package Account_Service.com.Exception;

public class AccountAlreadyExistsException extends RuntimeException {

	
	
	public AccountAlreadyExistsException() {
		super("Resource not found on server");
	}
	
	public AccountAlreadyExistsException(String message) {
		super(message);
	}

}
