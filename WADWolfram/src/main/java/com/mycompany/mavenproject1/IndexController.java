/*
 * To change this license header, choose License Headers in UserProject Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author TOSHIBA
 */
@Controller
public class IndexController {
	
	 @Autowired private ProjectRepository projects;
	  
	 @Autowired private NoticiasRepository news;
	 
	

	@RequestMapping("/")
	public String ShowIndex(Model m) {
		
		List<Project> projectsList = projects.findAll();
		List<Noticia> newsList = news.findAll();
		List<ProjectProgress>percentages=new ArrayList<>(); 
		for(Project p: projectsList){
		ProjectProgress percentage=new ProjectProgress(p.getTitle(), p.calculateProgressPercentage());
		System.out.println(p.calculateProgressPercentage());
		percentages.add(percentage);
		} 
		 m.addAttribute("projects", projectsList);
		 m.addAttribute("news", newsList);		 
		 m.addAttribute("percentages",percentages);
		return "index_template";
	}
	
	@RequestMapping("/contact")
	public String ShowContact(Model m) {
		return "contact";
	}

	@RequestMapping("/about")
	public String ShowAbout(Model m) {
		return "about";
	}

	@RequestMapping("/register")
	public String ShowRegister(Model m) {
		return "register";
	}
}
