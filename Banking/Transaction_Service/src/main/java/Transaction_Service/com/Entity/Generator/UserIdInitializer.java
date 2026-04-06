package Transaction_Service.com.Entity.Generator;

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
        String prefix = "TRAN" + today;
       
        
        try {
        	
         String maxSeq = jdbcTemplate.queryForObject(
            "SELECT id FROM transactions WHERE id LIKE ? ORDER BY id DESC LIMIT 1",
            String.class,
            prefix + "%"
          );
         
         TransactionalIdGenerator.sequence = squencevalue(maxSeq, prefix);
         
        }catch (Exception e) {
        	TransactionalIdGenerator.sequence = 0001;
            
		}
        System.out.println("Starting user sequence from: " + TransactionalIdGenerator.sequence);
    } 

   
    
    private Long squencevalue(String name,String prefix) {
       String numericTail = name.substring(prefix.length()); // "0003"
        long nextSeq = Long.parseLong(numericTail) + 1;
        return nextSeq;
    }
    
    
}
