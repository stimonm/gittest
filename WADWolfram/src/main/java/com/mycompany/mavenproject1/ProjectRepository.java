package com.mycompany.mavenproject1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ProjectRepository extends JpaRepository<Project, Long>{
	@Query("SELECT MAX(id) FROM Project")
	Long maxID();
}
