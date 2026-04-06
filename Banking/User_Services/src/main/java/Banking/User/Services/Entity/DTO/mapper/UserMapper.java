package Banking.User.Services.Entity.DTO.mapper;

import Banking.User.Services.Entity.User;
import Banking.User.Services.Entity.DTO.ProfileUpdateResp;
import Banking.User.Services.Entity.DTO.UserDto;

public class UserMapper {

	
	
	 public static UserDto toDto(User u) {
	        return new UserDto(
	                u.getUserId(), u.getUsername(), u.getEmail(),
	                u.getFullname(), u.getPhone(), u.getStatus(),
	                u.getCreatedAt(), u.getUpdatedAt()
	        );
	    }
	 
	 
	 public static ProfileUpdateResp toProfileDto(User u) {
	        return new ProfileUpdateResp(
	                u.getUserId(), u.getUsername(), u.getEmail(),
	                u.getFullname(), u.getPhone(), u.getStatus(),
	                u.getCreatedAt(), u.getUpdatedAt()
	        );
	    }
}
