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
public class TransferRequest {

	
	
	private String fromAccountId;
    private String toAccountId;
    private BigDecimal amount;
}
