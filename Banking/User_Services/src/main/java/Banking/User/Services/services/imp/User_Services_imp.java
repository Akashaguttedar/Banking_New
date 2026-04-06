package Banking.User.Services.services.imp;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.aspectj.apache.bcel.util.Repository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Banking.User.Services.Entity.User;
import Banking.User.Services.Entity.Usercredential;
import Banking.User.Services.Entity.DTO.ProfileUpdateReq;
import Banking.User.Services.Entity.DTO.ProfileUpdateResp;
import Banking.User.Services.Entity.DTO.RegisterReq;
import Banking.User.Services.Entity.DTO.RegisterResp;
import Banking.User.Services.Entity.DTO.UserDto;
import Banking.User.Services.Entity.DTO.mapper.UserMapper;
import Banking.User.Services.Exception.ResourceAlreadyFoundException;
import Banking.User.Services.Exception.ResourceNotFoundException;
import Banking.User.Services.repository.UserCredentialRepository;
import Banking.User.Services.repository.User_service_repository;
import Banking.User.Services.services.User_services;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class User_Services_imp implements User_services {

	
	private final User_service_repository repository;
	
	private final UserCredentialRepository cerdre;
	
	public User_Services_imp(User_service_repository repository, UserCredentialRepository cerdre) {
        this.repository=repository;
		this.cerdre = cerdre;
	
	}

	
	@Override
	@Transactional
	public RegisterResp Save(RegisterReq registerReq) {
		
	   log.info("User registration started | username={} email={}",
		            registerReq.getUsername(), registerReq.getEmail());
		  
		if (repository.existsByUsername(registerReq.getUsername())) {
			log.warn("Registration failed - username already exists | username={}",
	                registerReq.getUsername());
			throw new ResourceAlreadyFoundException(registerReq.getUsername()+" = User is alreday exists");
        }
        if (repository.existsByEmailHash(registerReq.getEmail())) {
        	 log.warn("Registration failed - email already exists | email={}",
                     registerReq.getEmail());
            throw new ResourceAlreadyFoundException(registerReq.getEmail()+" = Email is alreday exists");
        }
		
		User user=new User();
		user.setUsername(registerReq.getUsername());
		user.setEmail(registerReq.getEmail());
		user.setEmailHash(registerReq.getEmail());
		user.setPhone(registerReq.getPhone());
		user.setFullname(registerReq.getFullname());
		user.setStatus("Active");
		user.setCreatedAt(Instant.now());
		repository.save(user);
		
		Usercredential cred=new Usercredential();
		cred.setPasswordHash(registerReq.getPassword());
		cred.setUser(user);
		cred.setCreatedAt(Instant.now());
		cerdre.save(cred);
		log.info("User registration successful | userId={}", user.getUserId());
		
		return new RegisterResp(user.getUserId(),user.getStatus(),user.getCreatedAt());
	}


	@Override
	public UserDto findbyid(String userid) {
		log.info("Fetching user details | userId={}", userid);
		User user = repository.findById(userid).orElseThrow(()->{
			log.error("User not found | userId={}", userid);
			return new ResourceNotFoundException("user id is not existing");
		});
		log.info("User fetched successfully | userId={}", userid);
		return UserMapper.toDto(user);
		
	}


	@Override
	@Transactional
	public ProfileUpdateResp Profileupdate(String userid,ProfileUpdateReq profileUpdateReq) {
		log.info("Profile update started | userId={}", userid);
		User user = repository.findById(userid).orElseThrow(()->{
			 log.error("Profile update failed - user not found | userId={}", userid);
			
			return new ResourceNotFoundException("user id is not existing");
					});
		
		
		if(profileUpdateReq.getFullName()!=null) { 
			log.info("Updating full name | userId={}", userid);
			user.setFullname(profileUpdateReq.getFullName());
		}
		if(profileUpdateReq.getPhone()!=null) {
			 log.info("Updating phone | userId={}", userid);
			user.setPhone(profileUpdateReq.getPhone());
		}
		repository.save(user);
		log.info("Profile update completed | userId={}", userid);
		return UserMapper.toProfileDto(user);
	}


	@Override
	public void passwordupdate(String userid, String oldpassword, String newpassowrd) {
		 log.info("Password update initiated | userId={}", userid);
		Usercredential cred = cerdre.findByUser_userId(userid).orElseThrow(()->{
			log.error("Password update failed - credentials not found | userId={}", userid);
	         return	new ResourceNotFoundException("cred is not existing") ;
		});
		
		
		if(!oldpassword.equals(cred.getPasswordHash())) {
			log.warn("Password update failed - invalid old password | userId={}", userid);
			throw new ResourceNotFoundException("invalid.old.password");
		}
		
		cred.setPasswordHash(newpassowrd);
		cerdre.save(cred);
		log.info("Password updated successfully | userId={}", userid);
		
	}

	
	
	
	
}
