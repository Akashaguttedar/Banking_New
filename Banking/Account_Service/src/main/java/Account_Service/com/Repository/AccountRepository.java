package Account_Service.com.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Account_Service.com.Entity.Account;

public interface AccountRepository extends JpaRepository<Account, String> {

	
	
	@Query("""
	        SELECT CASE 
	            WHEN COUNT(a) > 0 THEN true 
	            ELSE false 
	        END
	        FROM Account a
	        WHERE a.userId = :userId
	          AND a.accountType = :accountType
	          AND a.currency = :currency
	    """)
	
	boolean accountexits(
			@Param("userId") String userId,  
			@Param ("accountType")  String accountType,
			@Param("currency")   String currency);
	//boolean existsByuserIdAndaccountTypeAndCurrency(String userid,String Accounttype,String currency);
	 List<Account> findByuserId(String userid);
	
}
