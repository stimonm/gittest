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
	private ProjectService service;
	
	private static final String FILES_FOLDER_PROJECTS = "files";

	@RequestMapping("/project")
	public String viewProject(Model model, @RequestParam long id) {
		Project p= service.viewProject(id);
		model.addAttribute("Project", p);
		return "oneProject";
	}
	
	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	public String viewAllProjects(Model model) {
		List<Project> l =service.viewAllProjects();
		model.addAttribute("projects", l);
		return "projects_template";
	}

	@RequestMapping(value = "/borrarProyecto", method = RequestMethod.POST)
	public String deleteProject(@RequestParam long id,Model m, HttpSession sesion) {
service.deleteProject(id);	
		User u= (User) sesion.getAttribute("User");
m.addAttribute("bienvenido",u.getUser().getUserName());
		return "Bootstrap-Admin-Theme/index";
	}
	
	@RequestMapping("/pay")
	public String donate(Model m, long projectId, HttpSession sesion) {
		// projectId es el id para reconocer al proyecto que se dona
		User s = (User) sesion.getAttribute("User");
		Project p=service.viewProject(projectId);
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
		User s = (User) sesion.getAttribute("User");
Project p=service.viewProject(projectId);
		Date date=new Date();
		Donation d=new Donation(s.getUser(), p, money, date);
service.donate(projectId, s, d);
		List<Project> l=service.viewAllProjects();
				 
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
service.addNewProject(p);
		
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

