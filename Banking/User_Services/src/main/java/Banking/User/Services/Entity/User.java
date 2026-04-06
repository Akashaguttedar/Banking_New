package Banking.User.Services.Entity;

import java.sql.Types;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;

import Banking.User.Services.Entity.geneator.UserId;
import Banking.User.Services.validate.UniqUserName;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Users",indexes = {@Index(name = "idx_users_username",columnList = "username"),
		                          @Index(name="idx_users_email_hash",columnList = "emailHash"),
		                          @Index(name="idx_users_external_id",columnList = "externalId")})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "usercredentials")
public class User {

	
	@Id
	@UserId
	private String userId;
	
	
	@Column(length = 100,nullable = false)
	private String username;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "emailHash",length = 64,nullable = false)
	private String emailHash;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "full_name",length = 255)
	private String fullname;
	
	@Column(nullable = false)
	private String status;
	
	@Column(name = "externalId",length = 128)
	private String externalId;
	
	@Column(columnDefinition = "json")
	private String metadata;
	
	@CreationTimestamp
	@Column(nullable = false,updatable = false)
	private Instant createdAt;
	
	@UpdateTimestamp
	@Column
	private Instant updatedAt;
	
	@Column
	private Instant deletedAt;
	
	@Version
	private Long version;
	
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
	private List<Usercredential> usercredentials = new ArrayList<>(); 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
