package com.project.policies.administration.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.policies.administration.object.FirewallPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FirewallPolicyService {
    private final RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    public FirewallPolicyService(@Qualifier("template_lab")RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public FirewallPolicy getPolicy(String id) {
        Object policy = redisTemplate.opsForHash().get("FIREWALL_POLICIES", id);
        if (policy instanceof LinkedHashMap) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.convertValue(policy, FirewallPolicy.class);
        }
        return (FirewallPolicy) policy;    }

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
    public void savePolicy(FirewallPolicy policy) {
        redisTemplate.opsForHash().put("FIREWALL_POLICIES", policy.getId(), policy);
    }

    public Optional<FirewallPolicy> getDefaultPolicy() {
        return getAllPolicies().stream()
                .filter(policy -> "DEFAULT".equalsIgnoreCase(policy.getType()) && policy.isActive())
                .findFirst();
    }
    public FirewallPolicy getPolicyForUser(String userId, String category) {
        Object policyObject = redisTemplate.opsForHash().get(category, userId);
        return objectMapper.convertValue(policyObject, FirewallPolicy.class);
    }

    public FirewallPolicy getDefaultPolicyForStation(String ip, String category) {
        Object policyObject = redisTemplate.opsForHash().get(category, ip);
        return objectMapper.convertValue(policyObject, FirewallPolicy.class);
    }

    public FirewallPolicy getGeneralDefaultPolicy() {
        Object policyObject = redisTemplate.opsForHash().get("DEFAULT_POLICIES", "GENERAL_DEFAULT");
        return objectMapper.convertValue(policyObject, FirewallPolicy.class);
    }
}
