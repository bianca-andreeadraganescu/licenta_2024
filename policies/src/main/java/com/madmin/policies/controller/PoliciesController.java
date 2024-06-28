package com.madmin.policies.controller;

import com.madmin.policies.object.FirewallPolicy;
import com.madmin.policies.services.FirewallPolicyService;
import com.madmin.policies.utils.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/policies")
public class PoliciesController {

    private final FirewallPolicyService policyService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public PoliciesController(FirewallPolicyService policyService, JwtTokenProvider jwtTokenProvider) {
        this.policyService = policyService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    public ResponseEntity<String> createPolicy(@RequestBody FirewallPolicy policy, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            String role = String.valueOf(jwtTokenProvider.getRoleFromToken(token));
            if ("ADMIN".equals(role)) {
                policyService.savePolicy(policy);
                return ResponseEntity.status(HttpStatus.CREATED).body("Policy created successfully");
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePolicy(@PathVariable String id, @RequestBody FirewallPolicy policy, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            String role = String.valueOf(jwtTokenProvider.getRoleFromToken(token));
            if ("ADMIN".equals(role)) {
                FirewallPolicy existingPolicy = policyService.getPolicy(id);
                if (existingPolicy != null) {
                    updateExistingPolicy(existingPolicy, policy);
                    policyService.savePolicy(existingPolicy);
                    return ResponseEntity.ok("Policy updated successfully");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Policy not found");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
    }
    private void updateExistingPolicy(FirewallPolicy existingPolicy, FirewallPolicy newPolicy) {
        existingPolicy.setType(newPolicy.getType());
        existingPolicy.setName(newPolicy.getName());
        existingPolicy.setT_start(newPolicy.getT_start());
        existingPolicy.setT_stop(newPolicy.getT_stop());
        existingPolicy.setUsb(newPolicy.getUsb());
        existingPolicy.setFw(newPolicy.getFw());
        existingPolicy.setApps(newPolicy.getApps());
        existingPolicy.setTarget_type(newPolicy.getTarget_type());
        existingPolicy.setTarget_name(newPolicy.getTarget_name());
        existingPolicy.setActive(newPolicy.isActive());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePolicy(@PathVariable String id, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            String role = String.valueOf(jwtTokenProvider.getRoleFromToken(token));
            if ("ADMIN".equals(role)) {
                FirewallPolicy existingPolicy = policyService.getPolicy(id);
                if (existingPolicy != null) {
                    policyService.deletePolicy(id);
                    return ResponseEntity.ok("Policy deleted successfully");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Policy not found");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
    }

    @GetMapping("/{id}")
    public ResponseEntity<FirewallPolicy> getPolicy(@PathVariable String id) {
        FirewallPolicy policy = policyService.getPolicy(id);
        if (policy != null) {
            return ResponseEntity.ok(policy);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
