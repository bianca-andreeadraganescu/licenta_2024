package com.madmin.policies.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.madmin.policies.object.FirewallPolicy;
import com.madmin.policies.repository.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FirewallPolicyService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final AppUserRepository appUserRepository;
    private final ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(FirewallPolicyService.class);


    @Autowired
    public FirewallPolicyService(@Qualifier("template_lab") RedisTemplate<String, Object> redisTemplate, AppUserRepository appUserRepository, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.appUserRepository = appUserRepository;
        this.objectMapper = objectMapper;
    }

    public void savePolicy(FirewallPolicy policy) {
        String categoryKey = getCategoryKey(policy.getType());
        redisTemplate.opsForHash().put(categoryKey, policy.getId(), policy);
    }

    public FirewallPolicy getPolicy(String id) {
        // It is necessary to retrieve the policy from all categories
        for (String categoryKey : getAllCategoryKeys()) {
            Object policyObject = redisTemplate.opsForHash().get(categoryKey, id);
            if (policyObject != null) {
                FirewallPolicy policy = objectMapper.convertValue(policyObject, FirewallPolicy.class);
                if (policy != null) {
                    logger.info("Policy found in category: " + categoryKey);
                    return policy;
                } else {
                    logger.warn("Failed to convert policy object to FirewallPolicy for id: " + id + " in category: " + categoryKey);
                }
            } else {
                logger.info("No policy found for id: " + id + " in category: " + categoryKey);
            }
        }
        logger.warn("Policy with id: " + id + " not found in any category");
        return null;
    }

    public void deletePolicy(String id) {
        for (String categoryKey : getAllCategoryKeys()) {
            if (redisTemplate.opsForHash().hasKey(categoryKey, id)) {
                redisTemplate.opsForHash().delete(categoryKey, id);
                return;
            }
        }
    }

    public List<FirewallPolicy> getAllPolicies() {
        return redisTemplate.opsForHash().values("FIREWALL_POLICIES").stream()
                .map(policy -> {
                    if (policy instanceof LinkedHashMap) {
                        ObjectMapper mapper = new ObjectMapper();
                        return mapper.convertValue(policy, FirewallPolicy.class);
                    }
                    return (FirewallPolicy) policy;
                })
                .collect(Collectors.toList());
    }

    private String getCategoryKey(String type) {
        switch (type) {
            case "LAB":
                return "LAB_POLICIES";
            case "EXAM":
                return "EXAM_POLICIES";
            case "DEV":
                return "DEV_POLICIES";
            default:
                return "DEFAULT_POLICIES";
        }
    }
    private List<String> getAllCategoryKeys() {
        return Arrays.asList("LAB_POLICIES", "EXAM_POLICIES", "DEV_POLICIES", "DEFAULT_POLICIES");
    }
}
