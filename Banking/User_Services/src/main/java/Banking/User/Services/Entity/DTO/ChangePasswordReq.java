package Banking.User.Services.Entity.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordReq {

	
	@NotBlank(message = "Please Enter old passowrd")
    private String oldPassword;

    @NotBlank
    @Size(min = 8)
    private String newPassword;
}
