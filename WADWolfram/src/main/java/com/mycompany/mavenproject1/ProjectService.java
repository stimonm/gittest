package com.mycompany.mavenproject1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
//import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projects;
	@Autowired
	private DonationsRepository movements;
	
	//private static final String FILES_FOLDER_PROJECTS = "files";

	@PostConstruct
	public void init() {
		Date releaseDate = new Date();
		projects.save(new Project("Titulo", "Breve Descripcion", "description", 500000.0, 0.0, 36, true, releaseDate, 2017,
				"image"));
		projects.save(new Project("Titulo2", "Breve Descripcion2", "description2", 600.0, 0.0, 36, true, releaseDate,
				2017, "image"));
	}

	public Project viewProject	(long id) {
		Project p = projects.findOne(id);
		return p;
	}
	
	public List<Project> viewAllProjects(	) {
		List<Project> l = projects.findAll();
	return l;
	}

		public Project deleteProject		(long 			id				) {
		Project p = projects.findOne(id);
if(p!=null){		
		for (Donation d: p.getDonations()){
			   movements.delete(d);
		}
		projects.delete(p);
}
return p;		
	}
	
public Project donate(		long projectId, User u		, Donation d){
				Project p  = projects.findOne(projectId);
				if(p!=null){
		  movements.save		(d);
		  p.setParcialBudget(p.getParcialBudget()+d.getMoney());
		  p.setRestBudget(p.getRestBudget()-d.getMoney());
		  projects.save(p);
				}
return p;
	}
	
	public Project addNewProject(Project p){
projects.save(p);		
return p;
		
/*
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
		*/
}

	/*
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
	*/
	
}

