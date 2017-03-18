package com.mycompany.mavenproject1;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProjectController {

	@Autowired
	private ProjectRepository projects;
	@Autowired
	private DonationsRepository movements;
	
	private static final String FILES_FOLDER_PROJECTS = "files";

	@PostConstruct
	public void init() {
		Date releaseDate = new Date();
		projects.save(new Project("Titulo", "Breve Descripcion", "description", 500000.0, 0.0, 36, true, releaseDate, 2017,
				"image"));
		projects.save(new Project("Titulo2", "Breve Descripcion2", "description2", 600.0, 0.0, 36, true, releaseDate,
				2017, "image"));
	}

	@RequestMapping("/project")
	
	public String viewProject(Model model, @RequestParam long id) {
		Project p = projects.findOne(id);
		model.addAttribute("Project", p);
		return "oneProject";
	}
	

	/// ESTO SACARIA TODOS LOS PROYECTOS
	/*
	 * public String viewProject2(Model model){ model.addAttribute("Project",
	 * projects.findAll()); return "oneProject"; }
	 */

	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	public String viewAllProjects(Model model) {
		List<Project> l = projects.findAll();
		model.addAttribute("projects", l);
		return "projects_template";
	}

	

	@RequestMapping(value = "/borrarProyecto", method = RequestMethod.POST)
	public String deleteProject(@RequestParam long id,Model m, HttpSession sesion) {
		Project p = projects.findOne(id);
		
		for (Donation d: p.getDonations()){
			
			movements.delete(d);
		}
		projects.delete(p);
		
		User u= (User) sesion.getAttribute("User");
		
		m.addAttribute("bienvenido",u.getUser().getUserName());
		return "Bootstrap-Admin-Theme/index";
	}
	
	@RequestMapping("/pay")
	public String donate(Model m, long projectId, HttpSession sesion) {
		// projectId es el id para reconocer al proyecto que se dona
		User s = (User) sesion.getAttribute("User");
		Project p=projects.findOne(projectId);
		if (s != null) {
			m.addAttribute("projectId",projectId);
			m.addAttribute("RestBudget",p.getRestBudget());
			m.addAttribute("User", s.getUser());
			return "pay";
		} else {
			return "login";
		}

	}

	
	@RequestMapping(value="/pay/projects", method=RequestMethod.POST)
	public String donate(@RequestParam long projectId, HttpSession sesion, @RequestParam double money, Model model) {
		Date d = new Date();
		User s = (User) sesion.getAttribute("User");
		Project p  = projects.findOne(projectId);
		movements.save(new Donation(s.getUser(), p, money, d));
		p.setParcialBudget(p.getParcialBudget()+money);
		p.setRestBudget(p.getRestBudget()-money);
		projects.save(p);
		List<Project> l = projects.findAll();
		model.addAttribute("projects", l);
		return "projects_template";
	}
	
	
	@RequestMapping(value="/admin/AddProject/create", method=RequestMethod.POST)
	public String addNewProject(Model model, HttpSession sesion,@RequestParam String title,@RequestParam String shortDescription,
			@RequestParam String description,@RequestParam double totalBudget,@RequestParam double parcialBudget,
			@RequestParam double time,@RequestParam String releaseDate,@RequestParam boolean opened,
			@RequestParam int startYear,@RequestParam ("imagen") MultipartFile imagen){
		
		Date date= new Date();
		Project p= new Project(title, shortDescription, description, totalBudget, parcialBudget, time, true, date, startYear,"");
		projects.save(p);
		
		String fileName = p.getId() + ".jpg";
		if (!imagen.isEmpty()) {
			try {

				File filesFolder = new File(FILES_FOLDER_PROJECTS);
				if (!filesFolder.exists()) {
					filesFolder.mkdirs();
				}

				File uploadedFile = new File(filesFolder.getAbsolutePath(), fileName);
				imagen.transferTo(uploadedFile);
			}catch(IllegalStateException e){
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		User u = (User) sesion.getAttribute("User");
        model.addAttribute("bienvenido",u.getUser().getUserName());
                return "Bootstrap-Admin-Theme/index";           //WE ARE OUT!
		
	}

	  @RequestMapping("/imagep/{fileName}.jpg")
		public void handleFileDownload(@PathVariable String fileName,
				HttpServletResponse res) throws FileNotFoundException, IOException {

			File file = new File(FILES_FOLDER_PROJECTS, fileName+".jpg");

			if (file.exists()) {
				res.setContentType("imagep/jpeg");
				res.setContentLength(new Long(file.length()).intValue());
				FileCopyUtils
						.copy(new FileInputStream(file), res.getOutputStream());
			} else {
				res.sendError(404, "File" + fileName + "(" + file.getAbsolutePath()
						+ ") does not exist");
			}
		}
	
	
}

