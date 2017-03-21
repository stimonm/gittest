package com.mycompany.mavenproject1;

import java.util.Date;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public  ArrayList mostrarPorCategoria(String categoria) {
        ArrayList<Noticia> l = noticias.findByCategoria(categoria);
        return l;
    }
        
    public ArrayList<Noticia> mostrarTodas() {
        ArrayList<Noticia> l = noticias.findAll();
        return l;
    }

    public Noticia mostrarUna(long id) {
        Noticia n = noticias.findOne(id);
        return n;
    }

    public Noticia comentar(User u, String comentario, long id){ 
        Noticia n = noticias.findOne(id);    //pillamos la noticia de la bd
        if (n!=null){
           String nombre = u.user.getName();
           nombre = nombre + " dice: \n" + comentario + "\n";
           n.getComentarios().add(nombre);
           n.setNumber_comments(n.getNumComentarios() + 1);
           noticias.save(n);
           }
        return n;
   }

    public Noticia addNewBlog(Noticia noticia){
       
    	noticias.save(noticia);                                                               //Añadimos la noticia a la bbdd
     return noticia;
    }
/*
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
    
    public Noticia deleteNew(long id){
        Noticia n = noticias.findOne(id);
        if (n!=null)
           noticias.delete(n);
        return n;
    }
}
