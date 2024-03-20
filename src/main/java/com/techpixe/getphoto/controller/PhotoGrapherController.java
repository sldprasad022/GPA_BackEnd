package com.techpixe.getphoto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techpixe.getphoto.dto.ErrorDTO;
import com.techpixe.getphoto.entity.PhotoGrapher;
import com.techpixe.getphoto.service.PhotoGrapherService;

@RestController
@RequestMapping("/client")
public class PhotoGrapherController 
{
	@Autowired
	private PhotoGrapherService photoGrapherService;
	
	@PostMapping("/registration/{admin}")
	public ResponseEntity<PhotoGrapher> save(@RequestParam String fullName, @RequestParam String email,@RequestParam Long mobileNumber, @PathVariable Long admin)
	{
		PhotoGrapher saved = photoGrapherService.registration(fullName, email, mobileNumber, admin);
		return new ResponseEntity<PhotoGrapher>(saved, HttpStatus.CREATED);
	}
	
	
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String emailOrMobileNumber, String password)
	{
		if (emailOrMobileNumber!=null) 
		{
			if (isMail(emailOrMobileNumber)) 
			{
				return photoGrapherService.loginByEmail(emailOrMobileNumber, password);
			}
			else if(isMobileNumber(emailOrMobileNumber))
			{
				return photoGrapherService.loginByMobileNumber(Long.parseLong(emailOrMobileNumber), password);
			}
			else
			{
				ErrorDTO errorDTO = new ErrorDTO();
				errorDTO.setError("Invalid Mobile Number or Email");
				return ResponseEntity.internalServerError().body(errorDTO);
			}
		}
		else
		{
			ErrorDTO errorDTO = new ErrorDTO();
			errorDTO.setError("Mobile Number or Email must be Provided");
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
