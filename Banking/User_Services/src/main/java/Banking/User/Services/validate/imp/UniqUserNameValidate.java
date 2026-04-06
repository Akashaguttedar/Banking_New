package Banking.User.Services.validate.imp;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import Banking.User.Services.repository.User_service_repository;
import Banking.User.Services.validate.UniqUserName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqUserNameValidate implements ConstraintValidator<UniqUserName, String>{

	@Autowired
	private User_service_repository repository;
	
	private final Duration ttl = Duration.ofMinutes(5); // adjust
	
	
	@Autowired
	@Qualifier("redisTemplate")
	private RedisTemplate<String, Object> redis;
	
	@Override
	public void initialize(UniqUserName constraintAnnotation) {
	       this.redis = BeanUtil.getBean("redisTemplate",RedisTemplate.class);
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(repository ==null) {
			System.out.println("repository null");
		}
			  if(repository==null) { return true; }
			  
			  return !repository.existsByUsername(value);
			 
	}

}
