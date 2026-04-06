package Account_Service.com.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import Account_Service.com.CorrelationIdFilter;



@Configuration
public class FilterConfig {

	
	   @Bean
	    public FilterRegistrationBean<CorrelationIdFilter> correlationFilter() {
	        FilterRegistrationBean<CorrelationIdFilter> registrationBean =
	                new FilterRegistrationBean<>();

	        registrationBean.setFilter(new CorrelationIdFilter());
	        registrationBean.addUrlPatterns("/*");
	        registrationBean.setOrder(1); // very important

	        return registrationBean;
	    }
	
}
