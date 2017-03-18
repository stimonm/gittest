/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.admin;

import com.mycompany.mavenproject1.Donation;
import com.mycompany.mavenproject1.DonationsRepository;
import com.mycompany.mavenproject1.Noticia;
import com.mycompany.mavenproject1.NoticiasRepository;
import com.mycompany.mavenproject1.Project;
import com.mycompany.mavenproject1.ProjectRepository;
import com.mycompany.mavenproject1.Role;
import com.mycompany.mavenproject1.User;
import com.mycompany.mavenproject1.UserPersonalData;
import com.mycompany.mavenproject1.UserPersonalDataRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author JuanAntonio
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    public NoticiasRepository noticias;

    @Autowired
    public ProjectRepository projects;

    @Autowired
    public UserPersonalDataRepository adminuser;

    @Autowired
    public DonationsRepository movements;
//-------------------karen lo comentó*-------------------------
   /* @RequestMapping("/")
    public String index(Model m, HttpSession sesion) {
        User u = (User) sesion.getAttribute("User");
        if (u == null) {
            return "login";
        }
        if(u.getUser().getRoles().get(0).equals("USER")){
        	return "error";
        
        }
        m.addAttribute("bienvenido", u.getUser().getUserName());
        return "Bootstrap-Admin-Theme/index";
    }*/
    
    @RequestMapping("/")
    public String home(Model model, HttpServletRequest request) {
    	
    	model.addAttribute("admin", request.isUserInRole("ADMIN"));
    	
    	return "Bootstrap-Admin-Theme/index";
    }


    @RequestMapping("/AddBlog")
    public String addblog(Model m) {
        List<Noticia> not = noticias.findAll();
        m.addAttribute("noticias", not);
        return "Bootstrap-Admin-Theme/addblog";
    }

    @RequestMapping("/AddProject")
    public String addproject(Model m) {
        List<Project> proj = projects.findAll();
        m.addAttribute("projects", proj);
        return "Bootstrap-Admin-Theme/addproject";
    }

    @RequestMapping("/Donations")
    public String donations(Model m) {
        List<Donation> don = movements.findAll();
        m.addAttribute("donaciones", don);
        return "Bootstrap-Admin-Theme/donations";
    }

    @RequestMapping("/Profile")
    public String profile(Model m, HttpSession sesion) {
        User u = (User) sesion.getAttribute("User");
        m.addAttribute("User", u.getUser());
        return "Bootstrap-Admin-Theme/profile";
    }

    /*@RequestMapping(value = "/Profile/create", method = RequestMethod.POST)
    public String NewAdmin(Model m, @RequestParam String name, @RequestParam String email,
            @RequestParam String password, @RequestParam String repeat_password,
            @RequestParam Boolean confirm) {

        ArrayList<String> rol = new ArrayList<>();
        rol.add("ADMIN");
        UserPersonalData u = new UserPersonalData(name, "", email, name, password, repeat_password, "i.jpg", rol);
        
        adminuser.save(u);
        m.addAttribute("bienvenido", u.getUserName());
        return "Bootstrap-Admin-Theme/index";
    }*/

    @RequestMapping(value = "/Profile/update", method = RequestMethod.POST)
    public String UpdateAdmin(Model m, HttpSession sesion, @RequestParam String memail,
            @RequestParam String mpassword, @RequestParam String mnew_password,
            @RequestParam String mrepeat_password) {

        User u = (User) sesion.getAttribute("User");
        UserPersonalData upd = u.getUser();
        if (!memail.isEmpty()) {
            upd.setEmail(memail);
        }
       

        if (! upd.getOldPassword().equals(mpassword)) {
            return "error2";
        }
        
        if (!mnew_password.isEmpty() && mnew_password.isEmpty()){
            return "error2";
        }
        if (mnew_password.isEmpty() && !mnew_password.isEmpty()){
            return "error2";
        }
        

        if (!mnew_password.isEmpty() && !mrepeat_password.isEmpty()) {
            if (mnew_password.equals(mrepeat_password)) {
                upd.setOldPassword(mnew_password);
                upd.setNewPassword(mnew_password);
            } else {
                return "error2";
            }
        }

        adminuser.save(upd);

        u.setUser(upd);

        sesion.setAttribute("User", u);

        m.addAttribute("bienvenido", upd.getUserName());
        return "Bootstrap-Admin-Theme/index";
    }

}
