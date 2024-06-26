package com.madmin.policies.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.madmin.policies.object.FirewallPolicy;
import com.madmin.policies.object.User;
import com.madmin.policies.repository.AppUserRepository;
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
    private final AppUserRepository appUserRepository;

    @Autowired
    public FirewallPolicyService(@Qualifier("template_lab") RedisTemplate<String, Object> redisTemplate, AppUserRepository appUserRepository) {
        this.redisTemplate = redisTemplate;
        this.appUserRepository = appUserRepository;
    }

    public void savePolicy(FirewallPolicy policy) {
        redisTemplate.opsForHash().put("FIREWALL_POLICIES", policy.getId(), policy);
    }

    public FirewallPolicy getPolicy(String id) {
        Object policy = redisTemplate.opsForHash().get("FIREWALL_POLICIES", id);
        if (policy instanceof LinkedHashMap) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.convertValue(policy, FirewallPolicy.class);
        }
        return (FirewallPolicy) policy;
    }

    public void deletePolicy(String id) {
        redisTemplate.opsForHash().delete("FIREWALL_POLICIES", id);
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

//    public void assignPolicyToUser(String username, String policyId) {
//        Optional<User> userOptional = appUserRepository.findByUsername(username);
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            user.setPolicyId(policyId);
//            appUserRepository.save(user);
//        }
//    }
//
//    public FirewallPolicy getPolicyForUser(String username) {
//        Optional<User> userOptional = appUserRepository.findByUsername(username);
//        if (userOptional.isPresent()) {
//            String policyId = userOptional.get().getPolicyId();
//            return getPolicy(policyId);
//        }
//        return null;
//    }
}
