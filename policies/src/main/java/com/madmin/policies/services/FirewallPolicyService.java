package com.madmin.policies.services;

import com.madmin.policies.object.FirewallPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FirewallPolicyService {
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public FirewallPolicyService(@Qualifier("template_lab")RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void savePolicy(FirewallPolicy policy) {
        redisTemplate.opsForHash().put("FIREWALL_POLICIES", policy.getId(), policy);
    }

    public FirewallPolicy getPolicy(String id) {
        return (FirewallPolicy) redisTemplate.opsForHash().get("FIREWALL_POLICIES", id);
    }

    public void deletePolicy(String id) {
        redisTemplate.opsForHash().delete("FIREWALL_POLICIES", id);
    }

    public List<FirewallPolicy> getAllPolicies() {
        return redisTemplate.opsForHash().values("FIREWALL_POLICIES").stream()
                .map(policy -> (FirewallPolicy) policy)
                .collect(Collectors.toList());
    }
}
