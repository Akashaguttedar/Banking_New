package Banking.User.Services.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import Banking.User.Services.Entity.Usercredential;

public interface UserCredentialRepository extends JpaRepository<Usercredential, String>{

	
	
	public Optional<Usercredential> findByUser_userId(String userid);
	
	
}
