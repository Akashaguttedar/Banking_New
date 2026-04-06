package Banking.User.Services.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Banking.User.Services.Entity.User;

@Repository
public interface User_service_repository extends JpaRepository<User, String> {

	
	
	boolean existsByUsername(String username);
	
	boolean existsByEmailHash(String email);
	
}
