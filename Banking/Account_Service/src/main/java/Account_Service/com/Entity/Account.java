package Account_Service.com.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import Account_Service.com.Entity.generator.AccountId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accounts",indexes = {
        @Index(name = "idx_account_number", columnList = "accountNumber", unique = true)
    })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

	
	
	@Id
    @AccountId
    private String account_id;

    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "account_type", nullable = false)
    private String accountType;

    @Column(nullable = false)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status; // ACTIVE, BLOCKED, CLOSED

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
	
}
