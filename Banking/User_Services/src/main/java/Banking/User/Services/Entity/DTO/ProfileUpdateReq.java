package Banking.User.Services.Entity.DTO;

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
public class ProfileUpdateReq {
   
	@Size(max = 255)
    private String fullName;

    @Size(max = 24)
    private String phone;

    private String metadata;

}
