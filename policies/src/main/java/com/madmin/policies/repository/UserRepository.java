package com.madmin.policies.repository;

import com.madmin.policies.object.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Repository
public class UserRepository {

    private static final String HASH_KEY = "user";
    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    @Autowired
    @Qualifier("template_user")
    private RedisTemplate<String, User> redisTemplate;
    private HashOperations<String, String, User> hashOperations;

    @PostConstruct
    public void init() {
        hashOperations = redisTemplate.opsForHash();
        log.info("UserRepository initialized successfully.");
    }

    public User save(User user) {
        hashOperations.put(HASH_KEY, user.getUsername(), user);
        return user;
    }

    public Optional<User> findByUsername(String username) {
        User user = hashOperations.get(HASH_KEY, username);
        return Optional.ofNullable(user);
    }

    public void deleteByUsername(String username) {
        hashOperations.delete(HASH_KEY, username);
    }
}
