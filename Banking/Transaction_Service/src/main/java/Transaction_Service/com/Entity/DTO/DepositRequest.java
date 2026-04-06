package Transaction_Service.com.Entity.DTO;

import java.math.BigDecimal;

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
public class DepositRequest {
	
	    private String accountId;
	    
	    private BigDecimal amount;
}
