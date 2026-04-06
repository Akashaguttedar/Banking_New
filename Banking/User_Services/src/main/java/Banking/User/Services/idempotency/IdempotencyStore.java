package Banking.User.Services.idempotency;

import java.time.Duration;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;



@Component
public class IdempotencyStore {

	 private final RedisTemplate<String, Object> redis;
	 private final ObjectMapper mapper = new ObjectMapper();
	 
	public IdempotencyStore(RedisTemplate<String, Object> redisTemplate) {
           this.redis=redisTemplate;
	}
	
	
	public IdempotencyResponse get(String key) {
		
		Object raw = redis.opsForValue().get(key);
		
		if(raw==null) return null;
		
		if (raw instanceof IdempotencyResponse resp) {
            return resp;
        }
		
		 return mapper.convertValue(raw, IdempotencyResponse.class);
	}
	
	
	public void save(String key,IdempotencyResponse response) {
		redis.opsForValue().set(key, response,Duration.ofSeconds(120));
	}
	
	
	
}
