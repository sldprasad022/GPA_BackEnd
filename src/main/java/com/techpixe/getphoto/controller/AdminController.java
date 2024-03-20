package com.techpixe.getphoto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techpixe.getphoto.dto.ErrorDTO;
import com.techpixe.getphoto.entity.Admin;
import com.techpixe.getphoto.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController
{
	@Autowired
	private AdminService adminService;
	
	@PostMapping("/save")
	public ResponseEntity<Admin> saveAdmin(@RequestParam String fullName, @RequestParam String email, @RequestParam Long mobileNumber, @RequestParam String password)
	{
		Admin savedAdmin = adminService.register(fullName, email, mobileNumber, password);
		return new ResponseEntity<Admin>(savedAdmin, HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String emailOrMobileNumber, String password)
	{
		
		if (emailOrMobileNumber!=null)
		{
			if (isMail(emailOrMobileNumber)) 
			{
				return adminService.loginByEmail(emailOrMobileNumber, password);
			}
			else if(isMobileNumber(emailOrMobileNumber))
			{
				return adminService.loginByMobileNumber(Long.parseLong(emailOrMobileNumber), password);
			}
			else
			{
				ErrorDTO errorDTO = new ErrorDTO();
				errorDTO.setError("Invalid Email or MobileNumber and provide valid format");
				return ResponseEntity.internalServerError().body(errorDTO);
			}
		}
		else
		{
			ErrorDTO errorDTO = new ErrorDTO();
			errorDTO.setError("*************** Email or MobileNumber and must be provide************");
			return ResponseEntity.internalServerError().body(errorDTO);
		}
		
	}
	
	private boolean isMail(String emailOrMobileNumber)
	{
		return emailOrMobileNumber.contains("@");
	}
	
	private boolean isMobileNumber(String emailOrMobileNumber)
	{
		return emailOrMobileNumber.matches("\\d+");
	}
}
