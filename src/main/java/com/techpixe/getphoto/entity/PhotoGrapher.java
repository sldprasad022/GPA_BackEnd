package com.techpixe.getphoto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="photoGrapher_Table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoGrapher 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long photoGrapherId;
	
	private String fullName;
	
	private String email;
	
	private Long mobileNumber;
	
	private String password;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="adminId")
	private Admin admin;
	
	private String role;
}
