package Transaction_Service.com.Exception;

public class InsufficientBalanceException extends RuntimeException {
	
	public InsufficientBalanceException() {
		super("Resource not found on server");
	}
	
	public InsufficientBalanceException(String message) {
		super(message);
	}
}
