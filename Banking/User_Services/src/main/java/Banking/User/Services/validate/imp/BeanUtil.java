package Banking.User.Services.validate.imp;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanUtil implements ApplicationContextAware{

	
	
	private static ApplicationContext ctx;
	
    @Override 
    public void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }
    public static <T> T getBean(String name,Class<T> cls) { 
    	
    	 if (ctx == null) {
             throw new IllegalStateException("ApplicationContext not initialized yet.");
         }	
    	
    	return ctx.getBean(name,cls); 
    	
    }
	
    public static <T> T getBean(Class<T> cls) { 
    	
   	 if (ctx == null) {
            throw new IllegalStateException("ApplicationContext not initialized yet.");
        }	
   	
   	  return ctx.getBean(cls); 
   	
   }
	
	
}
