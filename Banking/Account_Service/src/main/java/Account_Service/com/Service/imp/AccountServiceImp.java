package Account_Service.com.Service.imp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Account_Service.com.Entity.Account;
import Account_Service.com.Entity.Status;
import Account_Service.com.Entity.DTO.AccountCreateRequest;
import Account_Service.com.Entity.DTO.AccountResponse;
import Account_Service.com.Entity.DTO.mapper.AccountMapper;
import Account_Service.com.Exception.AccountAlreadyExistsException;
import Account_Service.com.Exception.ResourceNotFoundException;
import Account_Service.com.Repository.AccountRepository;
import Account_Service.com.Service.AccountService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountServiceImp implements AccountService{

	
	private final AccountRepository accountRepository;
	
	
   public AccountServiceImp(AccountRepository accountRepository) {
	this.accountRepository = accountRepository;
	// TODO Auto-generated constructor stub
  }	
	
	@Override
	public AccountResponse createAccount(AccountCreateRequest createRequest) {
		
		 log.info("Create account request | userId={} | type={} | currency={}",
		            createRequest.getUserId(),
		            createRequest.getAccountType(),
		            createRequest.getCurrency());
		
		boolean exists = accountRepository.accountexits(
				createRequest.getUserId(),
				createRequest.getAccountType(),
				createRequest.getCurrency());
		
		if(exists) {
			
			log.warn("Account already exists | userId={} | type={} | currency={}",
	                createRequest.getUserId(),
	                createRequest.getAccountType(),
	                createRequest.getCurrency());
			throw new AccountAlreadyExistsException("Account already exists for this user with same type and currency");
		}
		
		Account account=new Account();
		account.setAccountNumber(generateAccountNumber());
		account.setAccountType(createRequest.getAccountType());
		account.setBalance(BigDecimal.ZERO);
		account.setCreatedAt(LocalDateTime.now());
		account.setUpdatedAt(LocalDateTime.now());
		account.setCurrency(createRequest.getCurrency());
		account.setStatus(Status.ACTIVE);
		account.setUserId(createRequest.getUserId());
		
		Account saved = accountRepository.save(account);
		
		
		  log.info("Account created successfully | accountId={} | userId={}",
		            saved.getAccountNumber(),
		            saved.getUserId());
		
		return AccountMapper.accountResponse(saved);
	}
	
	private String generateAccountNumber() {
        return "AC" + System.currentTimeMillis();
    }

	@Override
	public AccountResponse getaccountbyid(String accountd) {
		log.debug("Fetching account by id | accountId={}", accountd);
		 
		Account account = accountRepository.findById(accountd).orElseThrow(()->{
			log.warn("Account not found | accountId={}", accountd);
		
		return new ResourceNotFoundException("Account not found");
	   });
		
		log.debug("Account fetched | accountId={} | balance={} | status={}",
		            account.getAccountNumber(),
		            account.getBalance(),
		            account.getStatus());
		return AccountMapper.accountResponse(account);
	}

	@Override
	public List<AccountResponse> getaccountsByuserId(String userid) {
		log.debug("Fetching accounts by userId={}", userid);
		List<Account> byuserId = accountRepository.findByuserId(userid);
		
		if(byuserId==null) {
			 log.warn("No accounts found for user | userId={}", userid);	
			throw   new ResourceNotFoundException("This user does not have any account");
		}
		
		List<AccountResponse> list = byuserId.stream().map(m->AccountMapper.accountResponse(m)).toList();
		
		/*
		 * List<AccountResponse> list2=new ArrayList<>();
		 * 
		 * for(Account a:byuserId) { list.add(AccountMapper.accountResponse(a)); }
		 */
		log.debug("Accounts found | userId={} | count={}", userid, list.size());
		return list;
	}

	@Override
	public BigDecimal getBalance(String accountId) {
	 log.info("Fetching balance | accountId={}", accountId);
	Account account = accountRepository.findById(accountId).orElseThrow(()->{
		
	  log.warn("Account not found while fetching balance | accountId={}", accountId);
		return new ResourceNotFoundException("Account not found");
		});
	   log.info("Balance fetched | accountId={} | balance={}",
		            account.getAccountNumber(),
		            account.getBalance());
		return account.getBalance();
	}

	@Transactional
	@Override
	public void applyblance(String accounid,BigDecimal newblance) {
		
	log.info("Apply balance request | accountId={} | newBalance={}",
			accounid,
			newblance);
		Account account = accountRepository.findById(accounid).orElseThrow(()->{
		log.warn("Account not found while updating balance | accountId={}", accounid);
		return new ResourceNotFoundException("Account not found");
		});
		
		if (newblance.compareTo(BigDecimal.ZERO) < 0) {
			 log.error("Negative balance attempt | accountId={} | newBalance={}",
		                accounid,
		                newblance);
            throw new IllegalArgumentException("Balance cannot be negative");
        }
		
		account.setBalance(newblance);
		
		 log.info("Balance updated successfully | accountId={} | balance={}",
		            accounid,
		            newblance);
		
		
	}

}
