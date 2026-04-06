package Transaction_Service.com.Entity.Generator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class TransactionalIdGenerator implements IdentifierGenerator {

	public static long sequence = 1;
	
	
	@Override
	public Object generate(SharedSessionContractImplementor session, Object object) {
		
		if (sequence == -1) {
            throw new IllegalStateException("TransactionalIdGenerator.sequence not initialized!");
        }
		  String prefix = "TRAN";
	        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	        
	        String seq = String.format("%04d", sequence++); // 3 digits like 001, 002, ...

	        return prefix + date + seq;
	}

}
