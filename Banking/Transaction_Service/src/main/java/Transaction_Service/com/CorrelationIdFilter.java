package Transaction_Service.com;


import java.io.IOException;
import java.util.UUID;


import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class CorrelationIdFilter implements Filter {

	 public static final String CORRELATION_ID = "correlationId";
	
	 @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		 HttpServletRequest httprequest=(HttpServletRequest)request;
		 String correlationId = httprequest.getHeader("X-Correlation-ID");
		 
		 
		 if (correlationId == null || correlationId.isBlank()) {
	            correlationId = UUID.randomUUID().toString();
	        }
		 
		 
		 MDC.put(CORRELATION_ID, correlationId);
		 try {
	            chain.doFilter(request, response);
	        } finally {
	            MDC.remove(CORRELATION_ID); // very important
	        }
	}

	

}
