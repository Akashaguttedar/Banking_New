package Account_Service.com.Service;

import java.math.BigDecimal;
import java.util.List;


import org.springframework.stereotype.Service;

import Account_Service.com.Entity.DTO.AccountCreateRequest;
import Account_Service.com.Entity.DTO.AccountResponse;

@Service
public interface AccountService {

	
	
	public AccountResponse createAccount(AccountCreateRequest createRequest);
	
	public AccountResponse getaccountbyid(String accountd);
	
	public List<AccountResponse> getaccountsByuserId(String userid);

	public BigDecimal getBalance(String accountId);
	
	public void applyblance(String accounid,BigDecimal newbalance);
}
