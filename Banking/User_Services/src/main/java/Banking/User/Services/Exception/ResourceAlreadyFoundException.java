package Banking.User.Services.Exception;

public class ResourceAlreadyFoundException extends RuntimeException {

	
	
	public ResourceAlreadyFoundException() {
		super("Resource not found on server");
	}
	
	public ResourceAlreadyFoundException(String message) {
		super(message);
	}

}
