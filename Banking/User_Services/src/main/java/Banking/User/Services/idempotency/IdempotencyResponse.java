package Banking.User.Services.idempotency;

import java.io.Serializable;

public class IdempotencyResponse implements Serializable{
	    
	    private int status;
	    private Object body;
	    
	    public IdempotencyResponse() {}

	    public IdempotencyResponse(int status, Object body) {
	        this.status = status;
	        this.body = body;
	    }

	    public int getStatus() { return status; }
	   

	    public Object getBody() { return body; }
	   
}
