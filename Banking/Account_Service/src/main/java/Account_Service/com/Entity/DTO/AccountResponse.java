package Account_Service.com.Entity.DTO;

import java.math.BigDecimal;

import Account_Service.com.Entity.Status;
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
public class AccountResponse {

	
	    private String accountId;
	    private String accountNumber;
	    private String accountType;
	    private BigDecimal balance;
	    private Status status;
	    private String currency;
	
	
}
