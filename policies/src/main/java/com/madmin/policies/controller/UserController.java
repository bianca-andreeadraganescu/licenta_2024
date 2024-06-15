package com.madmin.policies.controller;

import com.madmin.policies.object.FirewallPolicy;
//import com.madmin.policies.object.User;
//import com.madmin.policies.repository.UserRepository;
//import com.madmin.policies.services.FirewallPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("/api/users")
//public class UserController {
//    private final UserRepository userRepository;
//    private final FirewallPolicyService policyService;
//
//    @Autowired
//    public UserController(UserRepository userRepository, FirewallPolicyService policyService) {
//        this.userRepository = userRepository;
//        this.policyService = policyService;
//    }
//
//    @PostMapping("/{username}/applyPolicy")
//    public ResponseEntity<?> applyPolicyToUser(@PathVariable String username, @RequestBody String policyId) {
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//        }
//
//        FirewallPolicy policy = policyService.getPolicy(policyId);
//        if (policy == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Policy not found");
//        }
//
//        user.setFirewallPolicy(policy);
//        userRepository.save(user);
//        return ResponseEntity.ok("Policy applied to user");
//    }
//
//    @GetMapping("/{username}/policy")
//    public ResponseEntity<?> getUserPolicy(@PathVariable String username) {
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//        }
//
//        FirewallPolicy policy = user.getFirewallPolicy();
//        return policy != null ? ResponseEntity.ok(policy) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
//}
