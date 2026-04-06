package Banking.User.Services.Entity.DTO;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
	
    private String userId;
    private String username;
    private String email;
    private String fullName;
    private String phone;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
    

}
