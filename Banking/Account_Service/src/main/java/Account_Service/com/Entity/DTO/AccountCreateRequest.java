package Account_Service.com.Entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateRequest {

	
	
	 private String userId;
	 private String accountType; // SAVINGS, CURRENT
	 private String currency;
}
