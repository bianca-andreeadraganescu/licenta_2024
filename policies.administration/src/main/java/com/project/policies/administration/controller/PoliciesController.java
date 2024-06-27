package com.project.policies.administration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.policies.administration.object.FirewallPolicy;
import com.project.policies.administration.services.FirewallPolicyService;
import com.project.policies.administration.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/delivery")
public class PoliciesController {
    private final FirewallPolicyService policyService;
    private final UserService userService;

    @Autowired
    public PoliciesController(FirewallPolicyService policyService, UserService userService) {
        this.policyService = policyService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<FirewallPolicy> deliverPolicy(@RequestParam String ip, @RequestParam String checksum) {
        String category = determineCategoryFromChecksum(checksum);
        String userId = userService.getUserIdByIp(ip);
        FirewallPolicy policy = policyService.getPolicyForUser(userId, category);

        // Verificăm dacă politica este activă
        if (policy == null || !policy.isActive()) {
            policy = policyService.getDefaultPolicyForStation(ip, category);
        }

        // Verificăm din nou dacă politica implicită este activă
        if (policy == null || !policy.isActive()) {
            policy = policyService.getGeneralDefaultPolicy();
        }

        // Dacă nici politica generală implicită nu este activă, returnăm null
        if (policy == null || !policy.isActive()) {
            return ResponseEntity.ok().body(null);
        }

        String policyChecksum = calculateChecksum(policy);
        if (checksum.equals(policyChecksum)) {
            return ResponseEntity.ok().body(null); // Mesaj indicând că nu sunt necesare schimbări
        } else {
            return ResponseEntity.ok().body(policy);
        }
    }

    private String determineCategoryFromChecksum(String checksum) {
        // Exemplu de determinare a categoriei pe baza checksum-ului
        Map<String, String> checksumToCategory = new HashMap<>();
        checksumToCategory.put("d41d8cd98f00b204e9800998ecf8427e", "LAB_POLICIES");
        checksumToCategory.put("e99a18c428cb38d5f260853678922e03", "EXAM_POLICIES");
        checksumToCategory.put("ab56b4d92b40713acc5af89985d4b786", "DEV_POLICIES");
        checksumToCategory.put("4e07408562bedb8b60ce05c1decfe3ad", "DEFAULT_POLICIES");

        return checksumToCategory.getOrDefault(checksum, "DEFAULT_POLICIES");
    }


    private String calculateChecksum(FirewallPolicy policy) {
        return DigestUtils.md5DigestAsHex(policy.toString().getBytes());
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
}
