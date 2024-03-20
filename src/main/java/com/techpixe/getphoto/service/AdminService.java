package com.techpixe.getphoto.service;

import org.springframework.http.ResponseEntity;

import com.techpixe.getphoto.entity.Admin;

public interface AdminService 
{
	Admin register(String fullName, String email, Long mobileNumber, String password);
	
	ResponseEntity<?> loginByMobileNumber(Long mobileNumber, String password);
	
	ResponseEntity<?> loginByEmail(String email, String password);
	
}
