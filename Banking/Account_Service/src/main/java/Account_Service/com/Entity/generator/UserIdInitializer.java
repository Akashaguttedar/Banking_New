package Account_Service.com.Entity.generator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserIdInitializer implements ApplicationRunner {

   

    @Autowired
    private JdbcTemplate jdbcTemplate;

    

    @Override
    public void run(ApplicationArguments args) throws Exception {

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String prefix = "ACCO" + today;
       
        
        try {
        	
         String maxSeq = jdbcTemplate.queryForObject(
            "SELECT account_id FROM accounts WHERE account_id LIKE ? ORDER BY account_id DESC LIMIT 1",
            String.class,
            prefix + "%"
          );
         
         AccountIdGenerator.sequence = squencevalue(maxSeq, prefix);
         
        }catch (Exception e) {
        	AccountIdGenerator.sequence = 0001;
            
		}
        System.out.println("Starting user sequence from: " + AccountIdGenerator.sequence);
    } 

   
    
    private Long squencevalue(String name,String prefix) {
       String numericTail = name.substring(prefix.length()); // "0003"
        long nextSeq = Long.parseLong(numericTail) + 1;
        return nextSeq;
    }
    
    
}
