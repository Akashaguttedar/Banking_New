package Account_Service.com;


import java.io.IOException;
import java.util.UUID;
import java.util.logging.LogRecord;

import org.slf4j.MDC;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

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
	            MDC.clear(); // very important
	        }
	}

	

}
