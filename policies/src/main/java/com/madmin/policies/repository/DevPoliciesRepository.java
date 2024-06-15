package com.madmin.policies.repository;
import com.madmin.policies.object.FirewallPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Repository
public class DevPoliciesRepository {

    public static final String HASH_KEY = "DevPolicy";
    private static final Logger log = LoggerFactory.getLogger(DevPoliciesRepository.class);

    @Autowired
    @Qualifier("template_dev")
    private RedisTemplate redisTemplateDev;

    @PostConstruct
    public void init() {
        RedisSerializer<?> serializer = new Jackson2JsonRedisSerializer<>(FirewallPolicy.class);
        redisTemplateDev.setKeySerializer(new StringRedisSerializer());
        redisTemplateDev.setHashKeySerializer(new StringRedisSerializer());
        redisTemplateDev.setValueSerializer(serializer);
        redisTemplateDev.setHashValueSerializer(serializer);
        log.info("RedisTemplate configured successfully: {}", redisTemplateDev);
    }

    public FirewallPolicy save(FirewallPolicy policy) throws IOException {
        String key = String.valueOf(policy.getId());
        redisTemplateDev.opsForHash().put("policies", key, policy);
        System.out.println("Saved policy with key: " + key + " -> " + policy);
        return policy;
    }

    public void deleteById(String id) {
        Long deleted = redisTemplateDev.opsForHash().delete(HASH_KEY, id);
        if (deleted == null || deleted == 0) {
            throw new IllegalArgumentException("Policy not found with ID: " + id);
        }
    }
    public Optional<FirewallPolicy> findById(String id) {
        FirewallPolicy policy = (FirewallPolicy) redisTemplateDev.opsForHash().get(HASH_KEY, id);
        return Optional.ofNullable(policy);
    }

}