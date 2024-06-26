package com.project.policies.administration.controller;

import com.project.policies.administration.object.FirewallPolicy;
import com.project.policies.administration.services.FirewallPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/delivery")
public class PoliciesController {
    private final FirewallPolicyService policyService;

    @Autowired
    public PoliciesController(FirewallPolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FirewallPolicy> getPolicy(@PathVariable String id) {
        FirewallPolicy policy = policyService.getPolicy(id);
        return policy != null ? ResponseEntity.ok(policy) : ResponseEntity.notFound().build();
    }

    @PostMapping("/apply")
    public ResponseEntity<String> applyPolicy(@RequestBody String policyId) {
        FirewallPolicy policy = policyService.getPolicy(policyId);
        if (policy != null) {
            // Simularea trimiterii politicii către aplicația agent (Aplicatia 4)
            return ResponseEntity.ok("Policy applied to agent");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Policy not found");
        }
    }

    @GetMapping("/check")
    public ResponseEntity<FirewallPolicy> checkPolicy(@RequestParam String id, @RequestParam String checksum) {
        FirewallPolicy policy = policyService.getPolicy(id);
        if (policy != null) {
            String calculatedChecksum = policy.calculateChecksum();
            if (!calculatedChecksum.equals(checksum)) {
                return ResponseEntity.ok(policy);
            }
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user-policy")
    public ResponseEntity<FirewallPolicy> getUserPolicy(@RequestParam String username, @RequestParam String type) {
        Optional<FirewallPolicy> policy = policyService.getPolicyForUser(username, type);
        if (policy.isPresent()) {
            return ResponseEntity.ok(policy.get());
        } else {
            return policyService.getDefaultPolicy()
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
        }
    }

    @GetMapping("/user-active-policy")
    public ResponseEntity<FirewallPolicy> getUserActivePolicy(@RequestParam String username) {
        Optional<FirewallPolicy> policy = policyService.getActivePolicyForUser(username);
        if (policy.isPresent()) {
            return ResponseEntity.ok(policy.get());
        } else {
            return policyService.getDefaultPolicy()
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
        }
    }
}
