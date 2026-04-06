package Banking.User.Services.Entity;


import java.time.Instant;
import Banking.User.Services.Entity.geneator.CredID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_credentials",
        indexes = {@Index(name="idx_credentials_userid",columnList = "user_id")})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Usercredential {

	
	
	@Id
	@CredID
	private String credentialId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id",nullable = false)
	private User user;
	
	@Column(name = "password_hash",nullable = false,length = 225)
	private String passwordHash;
	
	@Column(name = "last_password_change")
	private Instant lastPasswordChange;
	
	@Column(name = "failed_attempts")
	private Integer failedAttempts = 0;
	
	@Column(name = "locked_until")
	private Instant lockedUntil;
	
	@Column(name = "created_at",nullable = false)
	private Instant CreatedAt;
	
	
}
