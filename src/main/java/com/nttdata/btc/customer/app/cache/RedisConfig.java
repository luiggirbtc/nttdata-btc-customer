package com.nttdata.btc.customer.app.cache;

import com.nttdata.btc.customer.app.cache.RedisCustomer;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Class configuration redis.
 *
 * @author lrs
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {
    @Bean
    public RedisTemplate<String, RedisCustomer> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, RedisCustomer> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}