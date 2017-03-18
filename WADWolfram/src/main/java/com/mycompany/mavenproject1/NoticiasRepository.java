/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticiasRepository extends JpaRepository<Noticia, Long> {

    public ArrayList<Noticia> findByCategoria(String categoria);

    public Noticia findByTitle(String title);

    public ArrayList<Noticia> findAll();
    
}
