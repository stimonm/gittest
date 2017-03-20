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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NoticiasService {

    @Autowired
    private NoticiasRepository noticias;

    private static final String FILES_FOLDER = "fileFolderNews";

    @PostConstruct
    public void init() {
        Date releaseDate = new Date();
        ArrayList<CommentClass> coments = new ArrayList<>();

        ArrayList<CommentClass> coments2 = new ArrayList<>();/*
         MultipartFile a = null;
         CommentClass cc= null;
         CommentClass cc2= null;
         coments.add(cc);
         coments2.add(cc2);*/

        noticias.save(new Noticia("Noticia1", /*a,*/ "cuerpo", 0, "enfermedad", coments, releaseDate));
        MultipartFile b = null;
        noticias.save(new Noticia("Noticia2", /*b,*/ "cuerpo2", 0, "eventos", coments, releaseDate));
    }

    public ResponseEntity<ArrayList<Noticia>> mostrarPorCategoria(		@RequestParam String categoria) {
        ArrayList<Noticia> l = noticias.findByCategoria(categoria);
        if (!l.isEmpty())
        	   return new ResponseEntity<>(l, HttpStatus.OK);
        else
        	  return new ResponseEntity<>(HttpStatus.NOT_FOUND);        	
    }

    public List<Noticia> mostrarTodas(Model model) {
        ArrayList<Noticia> l = noticias.findAll();
        return l;
    }

    public ResponseEntity<Noticia> mostrarUna(@RequestParam long id) {
        Noticia n = noticias.findOne(id);
        if (n!=null)
           return new ResponseEntity<>(n, HttpStatus.OK);
        else
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Noticia> Comentar(HttpSession sesion, @RequestBody String comentario, @PathVariable long id) 
        Noticia n = noticias.findOne(id);    //pillamos la noticia de la bd
        if (n!=null){
           User s = (User) sesion.getAttribute("User");     
           String nombre = s.user.getName();
           nombre = nombre + " dice: \n" + comentario + "\n";
           n.getComentarios().add(nombre);
           n.setNumber_comments(n.getNumComentarios() + 1);
           noticias.save(n);
           return new ResponseEntity<>(n, HttpStatus.OK);
       }
       else
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);      
    }

    @ResponseStatus(HttpStatus.CREATED)
    public Noticia addNewBlog(@RequestBody noticia){
        noticias.save(noticia);                                                               //Añadimos la noticia a la bbdd
/*        String fileName = n.getId() + ".jpg";
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
//falta devolver noticia con id
      return noticia;
    }

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

    @RequestMapping(value = "/borrarNoticia", method = RequestMethod.POST)
    public String deleteProject(@RequestParam long id, Model m, HttpSession sesion) {
        Noticia n = noticias.findOne(id);
        noticias.delete(n);
        User u = (User) sesion.getAttribute("User");
        m.addAttribute("bienvenido",u.getUser().getUserName());
        return "Bootstrap-Admin-Theme/index";
    }
}
