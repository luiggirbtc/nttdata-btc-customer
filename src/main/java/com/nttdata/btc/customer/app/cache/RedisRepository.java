package com.nttdata.btc.customer.app.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

/**
 * Class RedisRepository.
 *
 * @author lrs
 */
@Slf4j
@Repository
public class RedisRepository {
    @Autowired
    private RedisTemplate<String, RedisCustomer> redisTemplate;


    public RedisCustomer save(RedisCustomer customer) {
        log.info("Register in Redis :: " + customer);
        try {
            log.info("Before save");
            redisTemplate.opsForValue()
                    .set(customer.getId_customer(), customer);
            log.info("After save");
        } catch (Exception e) {
            log.error("Error while save redis object :: " + e);
        }
        return customer;
    }

    public RedisCustomer findById(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue()
                .get(key)).orElse(new RedisCustomer());
    }

    public boolean delete() {
        Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().flushAll();
        return true;
    }
}