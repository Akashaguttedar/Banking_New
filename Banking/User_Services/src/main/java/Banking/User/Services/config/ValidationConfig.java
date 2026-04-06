package Banking.User.Services.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

@Configuration
public class ValidationConfig {

    @Bean
    public LocalValidatorFactoryBean validator(AutowireCapableBeanFactory beanFactory) {
        LocalValidatorFactoryBean lvfb = new LocalValidatorFactoryBean();
        lvfb.setConstraintValidatorFactory(new SpringConstraintValidatorFactory(beanFactory));
        lvfb.setProviderClass(HibernateValidator.class);
        return lvfb;
    }
}
