package Banking.User.Services.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;



@Configuration
public class RedisConfig {

    private final RedisConnectionFactory connectionFactory;


    RedisConfig(@Lazy RedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

	
	@Bean
	public RedisConnectionFactory connectionFactory() {
		return new LettuceConnectionFactory();
	}
	
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(){
		
		RedisTemplate<String, Object> template =new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory());
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		Jackson2JsonRedisSerializer<Object> jacksonJsonRedisSerializer=new Jackson2JsonRedisSerializer<>(objectMapper,Object.class);
		
		
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		
		template.setValueSerializer(jacksonJsonRedisSerializer);
		template.setHashValueSerializer(jacksonJsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}
	
	
}
