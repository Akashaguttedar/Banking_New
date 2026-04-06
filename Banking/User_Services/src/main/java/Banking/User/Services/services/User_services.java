package Banking.User.Services.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import Banking.User.Services.Entity.User;
import Banking.User.Services.Entity.DTO.ProfileUpdateReq;
import Banking.User.Services.Entity.DTO.ProfileUpdateResp;
import Banking.User.Services.Entity.DTO.RegisterReq;
import Banking.User.Services.Entity.DTO.RegisterResp;
import Banking.User.Services.Entity.DTO.UserDto;

@Service
public interface User_services {

	
	
	public RegisterResp Save(RegisterReq RegisterReqser);
	
	
	public UserDto findbyid(String userid);
	
	public ProfileUpdateResp Profileupdate(String userid,ProfileUpdateReq profileUpdateReq);
	
	public void passwordupdate(String userid, String oldpassword,String newpassowrd);
	
}
