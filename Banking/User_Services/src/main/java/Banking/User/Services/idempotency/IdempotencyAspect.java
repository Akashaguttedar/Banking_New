package Banking.User.Services.idempotency;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import Banking.User.Services.controller.AuthController;
import jakarta.servlet.http.HttpServletRequest;


@Component
@Aspect
public class IdempotencyAspect {

    private Logger log=LoggerFactory.getLogger(IdempotencyAspect.class);

	private final HttpServletRequest request;
	private final IdempotencyStore store;
	
	
	public IdempotencyAspect(HttpServletRequest request,IdempotencyStore store) {
           this.request=request;
           this.store=store;      
	}
	
	@Around("@annotation(Banking.User.Services.idempotency.Idempotent)")
	public Object handleIdempotency(ProceedingJoinPoint joinPoint) throws Throwable{
		
		log.info("Aspect executed");
		
		String key=request.getHeader("Idempotency-key");
	
		if(key==null || key.isBlank()) {
			return joinPoint.proceed();
		}
		
		IdempotencyResponse cached = store.get(key);
		
		 if(cached != null) {
			log.info("Returning cached idempotency response");
			 return ResponseEntity
	                    .status(cached.getStatus())
	                    .body(cached.getBody());
		}
		
		
		
		Object response = joinPoint.proceed();
		
		if(response instanceof ResponseEntity<?> resp) {
			
			IdempotencyResponse wrapper =
                    new IdempotencyResponse(resp.getStatusCode().value(), resp.getBody());
			
			store.save(key, wrapper);
		}
		
		return response;
	}
	
}
