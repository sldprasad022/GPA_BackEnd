package com.techpixe.getphoto.serviceImpl;

import java.util.NoSuchElementException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.techpixe.getphoto.dto.ErrorDTO;
import com.techpixe.getphoto.dto.PhotoGrapherDTO;
import com.techpixe.getphoto.entity.Admin;
import com.techpixe.getphoto.entity.PhotoGrapher;
import com.techpixe.getphoto.repository.AdminRepository;
import com.techpixe.getphoto.repository.PhotoGrapherRepository;
import com.techpixe.getphoto.service.PhotoGrapherService;

@Service
public class PhotoGrapherServiceImpl implements PhotoGrapherService
{
	@Autowired
	private PhotoGrapherRepository photoGrapherRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("$(spring.mail.username)")
	private String fromMail;
	
	@Override
	public PhotoGrapher registration(String fullName, String email, Long mobileNumber, Long admin) 
	{
		Admin adminId= adminRepository.findById(admin).orElseThrow(()-> new NoSuchElementException("Admin Id'"+admin+"' is not present"));
		if (adminId!=null) 
		{
			PhotoGrapher photoGrapher = new PhotoGrapher();
			photoGrapher.setFullName(fullName);
			photoGrapher.setEmail(email);
			photoGrapher.setMobileNumber(mobileNumber);
			photoGrapher.setRole("PhotoGrapher");
			
			String password = generateRandomPassword();
			photoGrapher.setPassword(password);
			photoGrapher.setAdmin(adminId);
			
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setFrom(fromMail);
			simpleMailMessage.setTo(email);
			simpleMailMessage.setSubject("Registration completed Successfully in GetPhoto application\n");
			simpleMailMessage.setText("Dear " + fullName
					+ ",\n\n Thank you for singing Up for GetPhoto! click bewlow to get  started on your web or mobile device .\n\nPlease check your registered email and generted passowrd\n UserEmail  :"
					+ email + "\n Registered MobileNumber :" + mobileNumber + "\n Temporary Password   :" + password
					+ "\n\n"
					+ "you will be required to reset the temporary password upon login\n\n\n if you have any question or if you would like to request a call-back,please email us at support info@techpixe.com");
			
			javaMailSender.send(simpleMailMessage);
			
			return photoGrapherRepository.save(photoGrapher);
		}
		else
		{
			System.out.println("Admin Id is not present");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin Id is not presnt"+admin); 
		}
	}
	
	
	public static String generateRandomPassword()
	{
        // Define the characters for digits and alphabets
        String digits = "0123456789";
        String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        
        // Initialize a random object
        Random random = new Random();
        
        // Initialize StringBuilder to construct the password
        StringBuilder password = new StringBuilder();
        
        // Add 4 random digits to the password
        for (int i = 0; i < 4; i++)
        {
            password.append(digits.charAt(random.nextInt(digits.length())));
        }
        
        // Add 4 random alphabets to the password
        for (int i = 0; i < 4; i++) 
        {
            password.append(alphabets.charAt(random.nextInt(alphabets.length())));
        }
        
        return password.toString();
    }
	
	

	@Override
	public ResponseEntity<?> loginByMobileNumber(Long mobileNumber, String password) 
	{
		PhotoGrapher photoGrapher = photoGrapherRepository.findByMobileNumber(mobileNumber);
		if (photoGrapher!=null && photoGrapher.getPassword().equals(password)) 
		{
			PhotoGrapherDTO photoGrapherDTO = new PhotoGrapherDTO();
			photoGrapherDTO.setFullName(photoGrapher.getFullName());
			photoGrapherDTO.setEmail(photoGrapher.getEmail());
			photoGrapherDTO.setMobileNumber(photoGrapher.getMobileNumber());
			photoGrapherDTO.setPassword(photoGrapher.getPassword());
			photoGrapherDTO.setAdmin(photoGrapher.getAdmin());
			photoGrapherDTO.setRole(photoGrapher.getRole());
			photoGrapherDTO.setPhotoGrapherId(photoGrapher.getPhotoGrapherId());
			return ResponseEntity.ok(photoGrapherDTO);	
		}
		else
		{
			ErrorDTO errorDTO = new ErrorDTO();
			errorDTO.setError("Mobile Number and password is not matching");
			return ResponseEntity.internalServerError().body(errorDTO);
		}
	}

	
	@Override
	public ResponseEntity<?> loginByEmail(String email, String password) 
	{
		PhotoGrapher  photoGrapher = photoGrapherRepository.findByEmail(email);
		if (photoGrapher!=null && photoGrapher.getPassword().equals(password)) 
		{
			PhotoGrapherDTO photoGrapherDTO = new PhotoGrapherDTO();
			photoGrapherDTO.setPhotoGrapherId(photoGrapher.getPhotoGrapherId());
			photoGrapherDTO.setFullName(photoGrapher.getFullName());
			photoGrapherDTO.setEmail(photoGrapher.getEmail());
			photoGrapherDTO.setMobileNumber(photoGrapher.getMobileNumber());
			photoGrapherDTO.setAdmin(photoGrapher.getAdmin());
			photoGrapherDTO.setRole(photoGrapher.getRole());
			
			photoGrapherDTO.setPassword(photoGrapher.getPassword());
			
			return ResponseEntity.ok(photoGrapherDTO);
		}
		else
		{
			ErrorDTO error = new ErrorDTO();
			error.setError("***********Email and password is not matching*************");
			return ResponseEntity.internalServerError().body(error);
		}
	}
}
