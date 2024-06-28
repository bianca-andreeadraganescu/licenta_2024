package com.project.policies.administration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.policies.administration.object.FirewallPolicy;
import com.project.policies.administration.services.FirewallPolicyService;
import com.project.policies.administration.services.UserService;
import com.project.policies.administration.utils.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

@RestController
@RequestMapping("/api/policies")
public class PoliciesController {
    private final FirewallPolicyService policyService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private static final Logger logger = LoggerFactory.getLogger(PoliciesController.class);
    @Autowired
    public PoliciesController(FirewallPolicyService policyService, UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.policyService = policyService;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping
    public ResponseEntity<FirewallPolicy> deliverPolicy(@RequestParam String ip, @RequestParam String checksum) {
        String category = policyService.determineCategoryFromChecksum(checksum);
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

        String policyChecksum = policyService.calculateChecksum(policy);
        if (checksum.equals(policyChecksum)) {
            return ResponseEntity.ok().body(null); // Mesaj indicând că nu sunt necesare schimbări
        } else {
            return ResponseEntity.ok().body(policy);
        }
    }
    @GetMapping("/active")
    public ResponseEntity<FirewallPolicy> getActivePolicy(@RequestParam String ip, @RequestParam String checksum, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        logger.info("Token: " + token);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            String username = jwtTokenProvider.getUsername(token);
            logger.info("Username from token: " + username);

//            String category = policyService.determineCategoryFromChecksum(checksum);
//            logger.info("Determined category from checksum: " + category);

            FirewallPolicy policy = null;
            for (String category : policyService.getAllCategoryKeys()) {
                policy = policyService.getPolicyForIp(ip, category);
                if (policy != null && policy.isActive()) {
                    break; // Am găsit o politică activă, ieșim din buclă
                }
            }
            if (policy != null && policy.isActive()) {
                String currentChecksum = policyService.calculateChecksum(policy);
                logger.info("Current Checksum: " + currentChecksum);
                logger.info("Client Checksum: " + checksum);

                if (!currentChecksum.equals(checksum)) {
                    return ResponseEntity.ok(policy);
                }
                logger.info("Politica nu s-a schimbat. Returnez 204.");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // No new policy needed

            }
            logger.warn("No active policy found for IP: " + ip);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // No active policy found
        }
        logger.error("Invalid token for request from IP: " + ip);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Invalid token
    }

}
