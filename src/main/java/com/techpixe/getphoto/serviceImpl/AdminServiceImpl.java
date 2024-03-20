package com.techpixe.getphoto.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.techpixe.getphoto.dto.AdminDTO;
import com.techpixe.getphoto.dto.ErrorDTO;
import com.techpixe.getphoto.entity.Admin;
import com.techpixe.getphoto.repository.AdminRepository;
import com.techpixe.getphoto.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService
{
	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public Admin register(String fullName, String email, Long mobileNumber, String password) 
	{
		
		Admin admin = new Admin();
		admin.setFullName(fullName);
		admin.setEmail(email);
		admin.setMobileNumber(mobileNumber);
		admin.setPassword(password);
		admin.setRole("ADMIN");
		return adminRepository.save(admin);
	}

	@Override
	public ResponseEntity<?> loginByMobileNumber(Long mobileNumber, String password)
	{
		Admin user =  adminRepository.findByMobileNumber(mobileNumber);
		if (user!=null && user.getPassword().equals(password)) 
		{
			AdminDTO adminDTO = new AdminDTO();
			adminDTO.setAdminId(user.getAdminId());
			adminDTO.setEmail(user.getEmail());
			adminDTO.setFullName(user.getFullName());
			adminDTO.setPassword(user.getPassword());
			adminDTO.setRole(user.getRole());
			adminDTO.setMobileNumber(user.getMobileNumber());
			return ResponseEntity.ok(adminDTO);
		}
		else
		{
			ErrorDTO errorDTO = new ErrorDTO();
			errorDTO.setError("Mobile number and Password is not matching");
			return ResponseEntity.internalServerError().body(errorDTO);
		}
	}

	@Override
	public ResponseEntity<?> loginByEmail(String email, String password) 
	{
		Admin user1 = adminRepository.findByEmail(email);
		if (user1!=null && user1.getPassword().equals(password)) 
		{
			AdminDTO adminDTO = new AdminDTO();
			adminDTO.setAdminId(user1.getAdminId());
			adminDTO.setEmail(user1.getEmail());
			adminDTO.setFullName(user1.getFullName());
			adminDTO.setMobileNumber(user1.getMobileNumber());
			adminDTO.setPassword(user1.getPassword());
			adminDTO.setRole(user1.getRole());
			
			return ResponseEntity.ok(adminDTO);
		} 
		else 
		{
			ErrorDTO errorDTO = new ErrorDTO();
			errorDTO.setError("Email and Password is not Matching");
			return ResponseEntity.internalServerError().body(errorDTO);
		}
	}

}
