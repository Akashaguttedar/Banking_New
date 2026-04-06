package Banking.User.Services.controller;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Banking.User.Services.Entity.DTO.ChangePasswordReq;
import Banking.User.Services.Entity.DTO.ProfileUpdateReq;
import Banking.User.Services.Entity.DTO.ProfileUpdateResp;
import Banking.User.Services.Entity.DTO.UserDto;
import Banking.User.Services.services.User_services;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserProfileController {

    

	
	private final User_services services;
	
	public UserProfileController(User_services services, RedisConnectionFactory connectionFactory) {
		this.services = services;
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getdetailsbyuserid(@PathVariable String id){
		  log.info("API CALL: Get user | userId={}", id);
		UserDto findbyid = services.findbyid(id);
		log.info("API RESPONSE: Get user success | userId={}", findbyid.getUserId());
		return ResponseEntity.status(HttpStatus.OK).body(findbyid);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<ProfileUpdateResp> profileupdate(@Valid @RequestBody ProfileUpdateReq req,@PathVariable String id){
		log.info("API CALL: User Profile update  | userId={}", id);
		
		ProfileUpdateResp profileupdate = services.Profileupdate(id, req);
		log.info("API RESPONSE: User Profile updated success | userId={}", profileupdate.getUserId());
		return ResponseEntity.status(HttpStatus.OK).body(profileupdate);
	}
	
	@PostMapping("/{id}/change-password")
	public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordReq passwordReq,@PathVariable String id){
		log.info("API CALL: changePassword  | userId={}", id);
		
		services.passwordupdate(id, passwordReq.getOldPassword(), passwordReq.getNewPassword());
		log.info("API RESPONSE: changePassword success  | userId={}", id);
		return ResponseEntity.ok("Password updated sucessfully");
		
	}
	

	
	
}
