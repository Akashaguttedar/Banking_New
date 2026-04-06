package Transaction_Service.com.Exception;

public class AccountNotActiveException extends RuntimeException {

	
	
	public AccountNotActiveException() {
		super("Resource not found on server");
	}
	
	public AccountNotActiveException(String message) {
		super(message);
	}
	
	
}
