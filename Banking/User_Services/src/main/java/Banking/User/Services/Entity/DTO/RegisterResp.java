package Banking.User.Services.Entity.DTO;

import java.time.Instant;
import java.util.UUID;


public class RegisterResp {
    private String userId;
    private String status;
    private Instant createdAt;

    public RegisterResp() {}
    public RegisterResp(String userId, String status, Instant createdAt) {
        this.userId = userId; this.status = status; this.createdAt = createdAt;
    }
    public String getUserId() { return userId; }
    public String getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
}
