package com.techpixe.getphoto.service;

import org.springframework.http.ResponseEntity;

import com.techpixe.getphoto.entity.PhotoGrapher;

public interface PhotoGrapherService 
{
	PhotoGrapher registration(String fullName,String email,Long mobileNumber, Long admin);
	
	ResponseEntity<?> loginByMobileNumber(Long mobileNumber, String password);
	
	ResponseEntity<?> loginByEmail(String email, String password);
	
	
}
