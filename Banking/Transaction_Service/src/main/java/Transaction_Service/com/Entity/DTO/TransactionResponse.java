package Transaction_Service.com.Entity.DTO;

import java.math.BigDecimal;
import java.util.UUID;

import Transaction_Service.com.Entity.TransactionStatus;
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
public class TransactionResponse {

	
	private String transactionId;
    private TransactionStatus status;
    private BigDecimal balance;
	
}
