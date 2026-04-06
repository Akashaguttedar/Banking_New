package Transaction_Service.com.Entity.DTO;

import java.math.BigDecimal;
import java.util.UUID;

import Transaction_Service.com.Entity.Status;
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
public class AccountDto {

	
	
	private String accountId;
    private BigDecimal balance;
    private Status status;
}
