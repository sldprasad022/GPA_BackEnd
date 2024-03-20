
package com.techpixe.getphoto.dto;

import com.techpixe.getphoto.entity.Admin;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoGrapherDTO 
{
private Long photoGrapherId;
	
	private String fullName;
	
	private String email;
	
	private Long mobileNumber;
	
	private String password;
	
	private Admin admin;
	
	private String role;
}
