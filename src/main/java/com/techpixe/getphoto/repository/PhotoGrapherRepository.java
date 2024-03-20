package com.techpixe.getphoto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import com.techpixe.getphoto.entity.PhotoGrapher;

public interface PhotoGrapherRepository extends JpaRepository<PhotoGrapher, Long>
{
	PhotoGrapher findByMobileNumber(Long mobileNumber);
	
	PhotoGrapher findByEmail(String email);
}
