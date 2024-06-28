package com.project.policies.administration.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.project.policies.administration.controller.PoliciesController;
import com.project.policies.administration.object.FirewallPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class FirewallPolicyService {

    private final RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(PoliciesController.class);

    @Autowired
    public FirewallPolicyService(@Qualifier("template_lab") RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public FirewallPolicy getPolicy(String id, String category) {
        Object policyObject = redisTemplate.opsForHash().get(category, id);
        if (policyObject instanceof LinkedHashMap) {
            return objectMapper.convertValue(policyObject, FirewallPolicy.class);
        }
        return (FirewallPolicy) policyObject;
    }

    public FirewallPolicy getPolicyForUser(String ip, String category) {
        return (FirewallPolicy) redisTemplate.opsForHash().get(category, ip);
    }

    public FirewallPolicy getPolicyForIp(String ip, String category) {
        List<Object> policies = redisTemplate.opsForHash().values(category);
        if (policies == null) {
            logger.warn("No policies found for category: " + category);
            return null;
        }

        for (Object policyObject : policies) {
            FirewallPolicy policy = objectMapper.convertValue(policyObject, FirewallPolicy.class);
            if (policy != null && policy.getFw() != null) {
                for (String dest : policy.getFw().getDest()) {
                    if (dest.equals(ip)) {
                        logger.info("Policy found for IP: " + ip + " in category: " + category);
                        return policy;
                    }
                }
            }
        }

        logger.warn("No policy found for IP: " + ip + " in category: " + category);
        return null;
    }

    public FirewallPolicy getDefaultPolicyForStation(String ip, String category) {
        Object policyObject = redisTemplate.opsForHash().get(category, ip);
        if (policyObject instanceof LinkedHashMap) {
            return objectMapper.convertValue(policyObject, FirewallPolicy.class);
        }
        return null;
    }

    public FirewallPolicy getGeneralDefaultPolicy() {
        Object policyObject = redisTemplate.opsForHash().get("DEFAULT_POLICIES", "GENERAL_DEFAULT");
        if (policyObject instanceof LinkedHashMap) {
            return objectMapper.convertValue(policyObject, FirewallPolicy.class);
        }
        return (FirewallPolicy) policyObject;
    }

    public List<FirewallPolicy> getAllPolicies() {
        List<FirewallPolicy> allPolicies = new ArrayList<>();
        for (String category : getAllCategoryKeys()) {
            List<Object> policies = redisTemplate.opsForHash().values(category);
            for (Object policy : policies) {
                if (policy instanceof LinkedHashMap) {
                    allPolicies.add(objectMapper.convertValue(policy, FirewallPolicy.class));
                } else {
                    allPolicies.add((FirewallPolicy) policy);
                }
            }
        }
        return allPolicies;
    }
    private FirewallPolicy deserializePolicyFromChecksum(String checksum) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // Deserializăm JSON-ul din checksum
            String jsonPolicy = new String(MessageDigest.getInstance("MD5").digest(checksum.getBytes(StandardCharsets.UTF_8)));
            return objectMapper.readValue(jsonPolicy, FirewallPolicy.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize policy from checksum", e);
        }
    }

    public String determineCategoryFromChecksum(String checksum) {
        List<String> categories = getAllCategoryKeys();
        for (String category : categories) {
            List<Object> policies = redisTemplate.opsForHash().values(category);
            for (Object policyObject : policies) {
                FirewallPolicy policy = objectMapper.convertValue(policyObject, FirewallPolicy.class);
                String currentChecksum = calculateChecksum(policy);
                if (currentChecksum.equals(checksum)) {
                    return category;
                }
            }
        }
        return "DEFAULT_POLICIES"; // Default if not found
    }
    public List<String> getAllCategoryKeys() {
        return Arrays.asList("LAB_POLICIES", "EXAM_POLICIES", "DEV_POLICIES", "DEFAULT_POLICIES");
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
    public String calculateChecksum(FirewallPolicy policy) {
        try {
            // Convert policy to JSON string
            String jsonPolicy = objectMapper.writeValueAsString(policy);
            System.out.println("Java JSON Policy: " + jsonPolicy);
            // Calculate MD5 checksum
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(jsonPolicy.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate checksum", e);
        }
    }
}
