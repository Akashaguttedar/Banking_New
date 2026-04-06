package Banking.User.Services.Entity.geneator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserIdInitializer implements ApplicationRunner {

   

    @Autowired
    private JdbcTemplate jdbcTemplate;

    

    @Override
    public void run(ApplicationArguments args) throws Exception {

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String prefix = "USER" + today;
        String prefix1 = "CRED" + today;
        
        try {
        	
         String maxSeq = jdbcTemplate.queryForObject(
            "SELECT user_id FROM users WHERE user_id LIKE ? ORDER BY user_id DESC LIMIT 1",
            String.class,
            prefix + "%"
          );
         
         
         String maxSeq1 = jdbcTemplate.queryForObject(
                 "SELECT credential_id FROM user_credentials WHERE credential_id LIKE ? ORDER BY credential_id DESC LIMIT 1",
                 String.class,
                 prefix1 + "%"
               );

         UserIdGenerator.sequence = squencevalue(maxSeq, prefix);
         CredIdGenerator.sequence =squencevalue(maxSeq1, prefix1);
        }catch (Exception e) {
        	 UserIdGenerator.sequence = 0001;
             CredIdGenerator.sequence = 0001;
		}
        System.out.println("Starting user sequence from: " + UserIdGenerator.sequence);
    } 

    
    
    
    
    
    
    private Long squencevalue(String name,String prefix) {
       String numericTail = name.substring(prefix.length()); // "0003"
        long nextSeq = Long.parseLong(numericTail) + 1;
        return nextSeq;
    }
    
    
}
