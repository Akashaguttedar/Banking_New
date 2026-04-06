package Account_Service.com.Entity.DTO.mapper;

import Account_Service.com.Entity.Account;
import Account_Service.com.Entity.DTO.AccountResponse;

public class AccountMapper {

	
	public static AccountResponse accountResponse(Account account) {
		
		
		AccountResponse accountResponse = new AccountResponse(account.getAccount_id(),
				 account.getAccountNumber(),account.getAccountType()
				 ,account.getBalance(),account.getStatus(),account.getCurrency());
	
		return accountResponse;
		
	}
}
