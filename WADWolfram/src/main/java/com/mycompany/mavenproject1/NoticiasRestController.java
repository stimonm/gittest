package com.mycompany.mavenproject1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class NoticiasRestController {

    @Autowired
    private NoticiasService service;

    private static final String FILES_FOLDER = "fileFolderNews";

    @RequestMapping(value = "/mostrarPorCategoria", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Noticia>> mostrarPorCategoria(@RequestParam String categoria) {
        ArrayList<Noticia> l = service.mostrarPorCategoria(categoria);
        if (!l.isEmpty())  
           return new ResponseEntity<>(l, HttpStatus.OK);
        else
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/blog", method = RequestMethod.GET)
    public ArrayList<Noticia> mostrarTodas(	) {
        ArrayList<Noticia> l = service.mostrarTodas();
        return l;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ResponseEntity<Noticia> mostrarUna(HttpSession sesion, @RequestParam long id) {
        Noticia n = service.mostrarUna(id);
        if (n!=null)
           	return new ResponseEntity<>(n, HttpStatus.OK);
        else
           	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/comment/upload/{id}", method = RequestMethod.POST) //put???
    public ResponseEntity<Noticia> Comentar(HttpSession sesion, @RequestParam String comentarios, @PathVariable long id) {
        User s = (User) sesion.getAttribute("User");
        if (s == null) {
        	//aq:
           	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 
        else {
        	   Noticia n=service.comentar(s, comentarios, id);
        	   if(n!=null)
           		   return new ResponseEntity<>(n, HttpStatus.OK);
        		   else
           			   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/admin/AddBlog/create", method = RequestMethod.POST)  //URL y method post necesarios.
    @ResponseStatus(HttpStatus.CREATED)
    public Noticia addNewBlog(HttpSession sesion, 		@RequestBody Noticia  noticia){
        Noticia n=service.addNewBlog(noticia);
        /*
        String fileName = n.getId() + ".jpg";
        if (!imagen.isEmpty()) {
            try {

                File filesFolder = new File(FILES_FOLDER);
                if (!filesFolder.exists()) {
                    filesFolder.mkdirs();
                }

                File uploadedFile = new File(filesFolder.getAbsolutePath(), fileName);
                imagen.transferTo(uploadedFile);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
  */
       return n;
    }
    
/*
    @RequestMapping("/image/{fileName}.jpg")
    public void handleFileDownload(@PathVariable String fileName,
            HttpServletResponse res) throws FileNotFoundException, IOException {

        File file = new File(FILES_FOLDER, fileName + ".jpg");

        if (file.exists()) {
            res.setContentType("image/jpeg");
            res.setContentLength(new Long(file.length()).intValue());
            FileCopyUtils
                    .copy(new FileInputStream(file), res.getOutputStream());
        } else {
            res.sendError(404, "File" + fileName + "(" + file.getAbsolutePath()
                    + ") does not exist");
        }
    }
*/
    
    @RequestMapping(value = "/borrarNoticia", method = RequestMethod.POST)
    public ResponseEntity<Noticia> deleteProject(@RequestParam long id, HttpSession sesion) {
        Noticia n=service.deleteNew(id);
        if(n!=null)
           	return new ResponseEntity<>(n, HttpStatus.OK);
        else
           	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
    }
}
