package Transaction_Service.com.config;

import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class Restclientconfig {

	private static final String CORRELATION_HEADER = "X-Correlation-ID";
	 
	@Bean
	public RestClient restclientbuilder(RestClient.Builder builder){
		
		return builder
                .requestInterceptor((request, body, execution) -> {

                    // Get correlationId from MDC
                    String correlationId = MDC.get("correlationId");

                    // Forward it if present
                    if (correlationId != null) {
                        request.getHeaders()
                               .add(CORRELATION_HEADER, correlationId);
                    }

                    return execution.execute(request, body);
                })
                .build();
	}
}
