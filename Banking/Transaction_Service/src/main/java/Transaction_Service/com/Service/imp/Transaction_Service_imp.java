package Transaction_Service.com.Service.imp;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import Transaction_Service.com.Entity.Status;
import Transaction_Service.com.Entity.Transaction;
import Transaction_Service.com.Entity.TransactionStatus;
import Transaction_Service.com.Entity.TransactionType;
import Transaction_Service.com.Entity.DTO.AccountDto;
import Transaction_Service.com.Entity.DTO.DepositRequest;
import Transaction_Service.com.Entity.DTO.TransactionResponse;
import Transaction_Service.com.Entity.DTO.TransferRequest;
import Transaction_Service.com.Entity.DTO.WithdrawRequest;
import Transaction_Service.com.Exception.AccountNotActiveException;
import Transaction_Service.com.Exception.ClientException;
import Transaction_Service.com.Exception.InsufficientBalanceException;
import Transaction_Service.com.Service.Transaction_Service;
import Transaction_Service.com.repository.Transaction_Repository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Transaction_Service_imp implements Transaction_Service{



	@Autowired
	private RestClient client;

	private final Transaction_Repository repository;


	public Transaction_Service_imp(Transaction_Repository repository) {
		this.repository = repository;

	}



    @Transactional
	@Override
	public TransactionResponse deposit(DepositRequest depositRequest) {
 
    	 log.info("Starting deposit | accountId={} | amount={}",
    	            depositRequest.getAccountId(),
    	            depositRequest.getAmount());
    	
    	
		AccountDto account = getaccount(depositRequest.getAccountId());

		validateAccount(account);

		BigDecimal bigDecimal = account.getBalance().add(depositRequest.getAmount());

		log.debug("Calculated new balance | accountId={} | newBalance={}",
	            account.getAccountId(),
	            bigDecimal);
		/*
		 * client.put().uri(uribuilder->uribuilder
		 * .path("http://localhost:8083/api/v1/accounts/{id}/balance")
		 * .queryParam("balance", bigDecimal)
		 * .build(account.getAccountId())).retrieve().toBodilessEntity();
		 */  
		updateAmount(account.getAccountId(), bigDecimal);
		
		Transaction tx = createTransaction(account.getAccountId(),
				UUID.randomUUID(), 
				TransactionType.DEPOSIT, 
				depositRequest.getAmount(), 
				bigDecimal);
		
		repository.save(tx);
		
		 log.info("Deposit transaction saved | transactionId={} | accountId={}",
		            tx.getId(),
		            account.getAccountId());
		
		return new TransactionResponse(tx.getId(), tx.getStatus(), bigDecimal);
         

	}

	private Transaction createTransaction(String accId, UUID refId,
			TransactionType type,
			BigDecimal amount,
			BigDecimal balance) {
		Transaction tx = new Transaction();
		tx.setAccountId(accId);
		tx.setReferenceId(refId);
		tx.setType(type);
		tx.setAmount(amount);
		tx.setBalanceAfter(balance);
		tx.setStatus(TransactionStatus.SUCCESS);
		return tx;
	}



	private void validateAccount(AccountDto account) {
		if (!Status.ACTIVE.equals(account.getStatus())) {
			throw new AccountNotActiveException("Account is not acctive");
		}
	}


    @Transactional
	@Override
	public TransactionResponse withdraw(WithdrawRequest request) {
    	
    	 log.info("Starting withdraw | accountId={} | amount={}",
    	            request.getAccountId(),
    	            request.getAmount());

		AccountDto account = getaccount(request.getAccountId());

		validateAccount(account);
		
		 if (account.getBalance().compareTo(request.getAmount()) < 0) {
			 log.warn("Insufficient balance | accountId={} | balance={} | requested={}",
		                request.getAccountId(),
		                account.getBalance(),
		                request.getAmount());
	            throw new InsufficientBalanceException("Insufficient balance");
	        }

		
		 BigDecimal newamount = account.getBalance().subtract(request.getAmount());
		 
		 updateAmount(account.getAccountId(), newamount);
		
		 Transaction tx = createTransaction(account.getAccountId(),
					UUID.randomUUID(), 
					TransactionType.WITHDRAW, 
					request.getAmount(), 
					newamount);
			
			repository.save(tx);
			log.info("Withdraw successful | transactionId={} | accountId={}",
		            tx.getId(),
		            account.getAccountId());

			return new TransactionResponse(tx.getId(), tx.getStatus(), newamount);
	}

	@Transactional
    @Override
	public TransactionResponse transfer(TransferRequest request) {
		 log.info("Starting transfer | fromAccount={} | toAccount={} | amount={}",
		            request.getFromAccountId(),
		            request.getToAccountId(),
		            request.getAmount());
    		AccountDto from = getaccount(request.getFromAccountId());
    	    AccountDto to = getaccount(request.getToAccountId());
    	
    	    validateAccount(from);
            validateAccount(to);
        
            if(from.getBalance().compareTo(request.getAmount())<0) {
            	 log.warn("Insufficient balance for transfer | fromAccount={} | balance={} | requested={}",
                         from.getAccountId(),
                         from.getBalance(),
                         request.getAmount());
            	 throw new InsufficientBalanceException("Insufficient balance");
            }
        
            BigDecimal newbalancefrom = from.getBalance().subtract(request.getAmount());
            BigDecimal newbalanceto = to.getBalance().add(request.getAmount());
            
            
            log.debug("Calculated balances | fromAccountNewBalance={} | toAccountNewBalance={}",
            		newbalancefrom,
            		newbalanceto);
            
            updateAmount(to.getAccountId(), newbalanceto);
            updateAmount(from.getAccountId(), newbalancefrom);
            
            
            UUID randomUUID = UUID.randomUUID();
            
            Transaction fromaccount = createTransaction(from.getAccountId(),
            		randomUUID, 
					TransactionType.TRANSFER, 
					request.getAmount(), 
					newbalancefrom);
			
			repository.save(fromaccount);

			
			Transaction toaccount = createTransaction(to.getAccountId(),
            		randomUUID, 
					TransactionType.TRANSFER, 
					request.getAmount(), 
					newbalanceto);
			
			repository.save(toaccount);
			
			log.info("Transfer successful | referenceId={} | fromAccount={} | toAccount={}",
					randomUUID,
		            from.getAccountId(),
		            to.getAccountId());
			
			return new TransactionResponse(fromaccount.getId(), fromaccount.getStatus(), newbalancefrom);
	}
	
	
	private AccountDto getaccount(String accountid) {
		
		log.debug("Calling Account Service | accountId={}", accountid);
		
		AccountDto account = client.get()
				.uri("http://localhost:8083/api/v1/accounts/{id}",accountid)
				.retrieve()
				.onStatus(HttpStatusCode::is4xxClientError,(req,res)->{
					 log.error("Account service 4xx error | accountId={}", accountid);
			    	throw new ClientException("Account service client error");
			    })
			    .onStatus(HttpStatusCode::is5xxServerError,(req,res)->{
			    	 log.error("Account service 5xx error | accountId={}", accountid);
			    	throw new AccountNotActiveException("Account service unavailable");
			    })
				.body(AccountDto.class);
		 log.debug("Account fetched | accountId={} | balance={} | status={}",
		            account.getAccountId(),
		            account.getBalance(),
		            account.getStatus());
		
		return account;
	}
	
	
	private void updateAmount(String accountid,BigDecimal newbalance) {
		
		 log.debug("Updating account balance | accountId={} | newBalance={}",
		            accountid,
		            newbalance);
		
		
		client.put()
	    .uri(uriBuilder -> uriBuilder
	            .scheme("http")                 // ✅ REQUIRED
	            .host("localhost")              // ✅ REQUIRED
	            .port(8083)                     // ✅ REQUIRED
	            .path("/api/v1/accounts/{id}/balance") // ✅ ONLY PATH
	            .queryParam("balance", newbalance)
	            .build(accountid))
	    .retrieve()
	    .onStatus(HttpStatusCode::is4xxClientError,(req,res)->{
	    	  log.error("Failed to update balance (4xx) | accountId={}", accountid);
	    	throw new ClientException("Account service client error");
	    })
	    .onStatus(HttpStatusCode::is5xxServerError,(req,res)->{
	    	log.error("Failed to update balance (5xx) | accountId={}", accountid);
	    	throw new AccountNotActiveException("Account service unavailable");
	    })
	    .toBodilessEntity();
		log.debug("Balance updated successfully | accountId={}", accountid);
	}



	
	
}
