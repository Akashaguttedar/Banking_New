package Banking.User.Services.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import Banking.User.Services.Entity.User;
import Banking.User.Services.Entity.DTO.RegisterReq;
import Banking.User.Services.Entity.DTO.RegisterResp;
import Banking.User.Services.idempotency.Idempotent;
import Banking.User.Services.services.User_services;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    @Autowired
	private User_services services;
	
	
	
	@PostMapping("/register")
	//@Idempotent
	public ResponseEntity<RegisterResp> register(@Valid @RequestBody RegisterReq user){
		
		
	   log.info("API CALL: Register user | username={}", user.getUsername());
		
		RegisterResp save = services.Save(user);
		
		URI uri=ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(save.getUserId())
				.toUri();
		  log.info("API RESPONSE: User registered | userId={}",
	                save.getUserId());

		return ResponseEntity.created(uri).body(save);
	}
	
	
	
	
	
}
