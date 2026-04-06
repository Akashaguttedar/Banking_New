package Account_Service.com.Controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Account_Service.com.Entity.DTO.AccountCreateRequest;
import Account_Service.com.Entity.DTO.AccountResponse;
import Account_Service.com.Service.AccountService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {


	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;

	}

	@PostMapping
	public ResponseEntity<AccountResponse> createAccount(
			@RequestBody AccountCreateRequest request) {

		log.info("Create account API called | userId={} | type={} | currency={}",
	            request.getUserId(),
	            request.getAccountType(),
	            request.getCurrency());
		
		AccountResponse response = accountService.createAccount(request);
		log.info("Account created | accountId={} | userId={}",
	            response.getAccountNumber(),
	            request.getUserId());
		return new ResponseEntity<>(
				response,
				HttpStatus.CREATED
				);
	}



	@PutMapping("/{accountId}/balance")
	public void applyBalance(
			@PathVariable String accountId,
			@RequestParam BigDecimal balance
			) {
		
		
		 log.info("Apply balance API called | accountId={} | balance={}",
		            accountId,
		            balance);
		accountService.applyblance(accountId, balance);
		
		log.info("Apply balance successful | accountId={} | balance={}",
	            accountId,
	            balance);
	}
	
	
	
	@GetMapping("/{accountId}")
	public ResponseEntity<AccountResponse> getAccount(
			@PathVariable String accountId) {

		log.debug("Get account API called | accountId={}", accountId);

	    AccountResponse response = accountService.getaccountbyid(accountId);

	    log.debug("Get account successful | accountId={}", accountId);

	    return ResponseEntity.ok(response);
	}


	@GetMapping("/user/{userId}")
	public ResponseEntity<List<AccountResponse>> getUserAccounts(
			@PathVariable String userId) {

		 log.debug("Get user accounts API called | userId={}", userId);

		    List<AccountResponse> accounts = accountService.getaccountsByuserId(userId);

		    log.debug("Get user accounts successful | userId={} | count={}",
		            userId,
		            accounts.size());

		    return ResponseEntity.ok(accounts);
	}



	@GetMapping("/{accountId}/balance")
	public ResponseEntity<BigDecimal> getBalance(
			@PathVariable String accountId) {

		log.debug("Get balance API called | accountId={}", accountId);

	    BigDecimal balance = accountService.getBalance(accountId);

	    log.debug("Get balance successful | accountId={} | balance={}",
	            accountId,
	            balance);

	    return ResponseEntity.ok(balance);
	}
}
