package com.mycompany.mavenproject1;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPersonalDataRepository extends JpaRepository<UserPersonalData, Long> {
	
	List<UserPersonalData> findByEmailAndOldPassword(String email, String oldPassword);
	UserPersonalData findByEmail(String email);
	
}