package com.techpixe.getphoto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techpixe.getphoto.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>
{
	Admin findByMobileNumber(Long mobileNumber);
	
	Admin findByEmail(String email);
	
}
