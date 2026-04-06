package Transaction_Service.com.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import Transaction_Service.com.Entity.Generator.TransactionalId;
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
@Table(name = "transactions",indexes = {@Index(name = "idx_tx_account_id", columnList = "account_id"),
        @Index(name = "idx_tx_reference_id", columnList = "reference_id")})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
  
	
	@Id
	@TransactionalId
    private String id;

    @Column(name = "account_id", nullable = false)
    private String accountId;

    @Column(name = "reference_id")
    private UUID referenceId; // used for transfer

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private BigDecimal amount;

    private BigDecimal balanceAfter;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private LocalDateTime createdAt;
    
    
    
}
