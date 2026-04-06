package Banking.User.Services.Entity.geneator;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CredIdGenerator implements IdentifierGenerator{

	
	 public static long sequence = 1;
	
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object)  throws HibernateException{
		    String prefix = "CRED";
	        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	        
	        String seq = String.format("%04d", sequence++); // 3 digits like 001, 002, ...

	        return prefix + date + seq;
		
	}

}
