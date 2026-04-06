package Transaction_Service.com.Service;

import org.springframework.stereotype.Service;

import Transaction_Service.com.Entity.DTO.DepositRequest;
import Transaction_Service.com.Entity.DTO.TransactionResponse;
import Transaction_Service.com.Entity.DTO.TransferRequest;
import Transaction_Service.com.Entity.DTO.WithdrawRequest;

@Service
public interface Transaction_Service {

	
	
	
	public TransactionResponse deposit(DepositRequest depositRequest);
	public TransactionResponse withdraw(WithdrawRequest request);
	public TransactionResponse transfer(TransferRequest Request);
	
}
