package Banking.User.Services.Entity.DTO;

import Banking.User.Services.validate.UniqUserName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterReq {

	
	@UniqUserName(message = "User name is already exits")
	@NotBlank(message = "User name should not be blank")
	@Size(min = 3,max = 100,message = "min 3 char and max 100 char")
	@Pattern(regexp = "^[^\\s]+$", message = "Username must not contain spaces")
	private String username;
	
	@NotBlank(message = "Email should not be blank")
	@Email(message = "Email Should be like xxxxxx@gmail.com")
	private String email;
	
	@NotBlank(message = "password should not be blank")
	@Size(max = 8,message = "password should min 8")
	private String password;
	
	private String phone;
	
	@Size(max = 100,message = "max 100 char")
	private String fullname;
	
	
	
	
	
}
