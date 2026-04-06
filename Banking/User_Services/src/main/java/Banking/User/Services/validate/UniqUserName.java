package Banking.User.Services.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import Banking.User.Services.validate.imp.UniqUserNameValidate;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqUserNameValidate.class)
@Documented
public @interface UniqUserName {

	
	String message() default "User name already exits";
	
	Class<?>[] groups() default{};
	
	Class<? extends Payload>[] payload() default {};
	
}
