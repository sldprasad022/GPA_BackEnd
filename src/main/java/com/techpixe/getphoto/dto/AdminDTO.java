package com.techpixe.getphoto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO 
{
     private Long adminId;
	
	private String fullName;
	
	private String email;
	
	private Long mobileNumber;
	
	private String password;
	
	private String role;
}
