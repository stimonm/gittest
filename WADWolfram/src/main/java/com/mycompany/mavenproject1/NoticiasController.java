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

@Controller
public class NoticiasController {

    @Autowired
    public NoticiasRepository noticias;

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

    @RequestMapping(value = "/mostrarPorCategoria", method = RequestMethod.GET)
    public String mostrarPorCategoria(Model model, @RequestParam String categoria) {
        ArrayList<Noticia> l = noticias.findByCategoria(categoria);
        //  model.addAttribute("categoria", categoria);
        model.addAttribute("news", l);
        return "blog_template";
    }

    @RequestMapping(value = "/blog", method = RequestMethod.GET)
    public String mostrarTodas(Model model) {
        ArrayList<Noticia> l = noticias.findAll();
        model.addAttribute("news", l);
        return "blog_template";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String mostrarUna(Model model, HttpSession sesion, @RequestParam long id) {
        Noticia n = noticias.findOne(id);
        User s = (User) sesion.getAttribute("User");
        model.addAttribute("new", n);
        model.addAttribute("lcomentarios", n.getComentarios());
        model.addAttribute("id", n.getId());
        if(s==null){
            model.addAttribute("logeado", true);
        }else{
            model.addAttribute("logeado2", true);
        }
        return "new_template";
    }

    @RequestMapping(value = "/comment/upload/{id}", method = RequestMethod.POST) //put???
    public String Comentar(Model model, HttpSession sesion, @RequestParam String comentarios, @PathVariable long id) {//pillamos id y el comentario
        Noticia n = noticias.findOne(id);    //pillamos la noticia de la bd

        /*
         ArrayList<String> listaComentarios = new ArrayList<>();
         listaComentarios= n.getComentarios();
         System.out.println("comentarios antes: "+ listaComentarios);
         listaComentarios.add(comentarios);
         n.setComentarios(listaComentarios);
         System.out.println("comentarios despues: "+ n.getComentarios());
         /*noticias.findOne(id).setComentarios(listaComentarios);
         System.out.println("ListaComentarios: " + listaComentarios);
         System.out.println("getcomentarios: " + noticias.findOne(id).getComentarios());*/
        /*ArrayList<String> comment = new ArrayList<>();    //aux
         comment= n.getComentarios();      //pillamos los coments de la noticia donde comentamos
         comment.add(comentarios);       //añadimos el nuevo comentario a la lista
         n.setComentarios(comment);      //guardamos en n los comentarios con el añadido.
    	
         long aux= n.getId();
         noticias.delete(n);
    	
         Noticia n2= new Noticia(n.gettitle(),n.getCuerpo(),n.getCategoria(),n.getComentarios(),n.getdate());
         //CREAMOS UNA NOTICIA (POST) CON TODOS LOS DATOS DE LA ANTERIOR INCLUSO EL MISMO ID
         noticias.save(n2);*/
        ////CAMBIOS GABI
        User s = (User) sesion.getAttribute("User");
        if (s == null) {
            return "login";
        } else {
            String nombre = s.user.getName();
            nombre = nombre + " dice: \n" + comentarios + "\n";
            n.getComentarios().add(nombre);

            n.setNumber_comments(n.getNumComentarios() + 1);
            noticias.save(n);
            //n2.Comentar(comentarios, id);

            model.addAttribute("new", n);
            model.addAttribute("lcomentarios", n.getComentarios());
            model.addAttribute("id", n.getId());
            model.addAttribute("logeado2", true);
            
            return "new_template";
        }
    }

    @RequestMapping(value = "/admin/AddBlog/create", method = RequestMethod.POST)  //URL y method post necesarios.
    public String addNewBlog(Model model, HttpSession sesion,@RequestParam String title, @RequestParam String categoria,
            @RequestParam String fecha, @RequestParam("imagen") MultipartFile imagen, //@RP String hola, significa que en el form hay un input con name="hola"
            @RequestParam String cuerpo, @RequestParam Boolean confirm) { ///Se le pasa como parámetros todos los input del form

        Date date = new Date();  //Simulamos la hora actual
        ArrayList<String> x = new ArrayList<>();
        Noticia n = new Noticia(title, /*imagen,*/ cuerpo, categoria, x, date); //Creamos una noticia con todos los datos.

        noticias.save(n);                                                               //Añadimos la noticia a la bbdd

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
        
        User u = (User) sesion.getAttribute("User");
        model.addAttribute("bienvenido",u.getUser().getUserName());
        return "Bootstrap-Admin-Theme/index";           //WE ARE OUT!

    }

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

    @RequestMapping(value = "/borrarNoticia", method = RequestMethod.POST)
    public String deleteProject(@RequestParam long id, Model m, HttpSession sesion) {
        Noticia n = noticias.findOne(id);
        noticias.delete(n);
        User u = (User) sesion.getAttribute("User");
        m.addAttribute("bienvenido",u.getUser().getUserName());
        return "Bootstrap-Admin-Theme/index";
    }
}
