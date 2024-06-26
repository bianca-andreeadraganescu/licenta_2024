//package com.madmin.policies.controller;
//
//import com.madmin.policies.object.AgentPolicyAssignment;
//import com.madmin.policies.object.FirewallPolicy;
//import com.madmin.policies.object.User;
//import com.madmin.policies.services.AgentService;
//import com.madmin.policies.services.FirewallPolicyService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/admin")
//public class AdminController {
//
//    @Autowired
//    private FirewallPolicyService firewallPolicyService;
//
//    @Autowired
//    private AgentService agentPolicyService;
//
//    @PostMapping("/policies")
//    public ResponseEntity<String> createPolicy(@RequestBody FirewallPolicy policy) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
//            firewallPolicyService.savePolicy(policy);
//            return ResponseEntity.status(HttpStatus.CREATED).body("Policy created successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
//        }
//    }
//
//    @PutMapping("/policies/{id}")
//    public ResponseEntity<String> updatePolicy(@PathVariable String id, @RequestBody FirewallPolicy policyDetails) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
//            FirewallPolicy policy = firewallPolicyService.getPolicy(id);
//            if (policy != null) {
//                policy.setType(policyDetails.getType());
//                policy.setActive(policyDetails.isActive());
//                policy.setT_start(policyDetails.getT_start());
//                policy.setT_stop(policyDetails.getT_stop());
//                policy.setRules(policyDetails.getRules());
//                firewallPolicyService.savePolicy(policy);
//                return ResponseEntity.ok("Policy updated successfully");
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Policy not found");
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
//        }
//    }
//
//    @DeleteMapping("/policies/{id}")
//    public ResponseEntity<String> deletePolicy(@PathVariable String id) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
//            FirewallPolicy policy = firewallPolicyService.getPolicy(id);
//            if (policy != null) {
//                firewallPolicyService.deletePolicy(id);
//                return ResponseEntity.ok("Policy deleted successfully");
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Policy not found");
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
//        }
//    }
//
//    @GetMapping("/policies")
//    public ResponseEntity<List<FirewallPolicy>> getAllPolicies() {
//        List<FirewallPolicy> policies = firewallPolicyService.getAllPolicies();
//        return ResponseEntity.ok(policies);
//    }
//
//    @PostMapping("/assignments")
//    public ResponseEntity<String> assignPoliciesToAgent(@RequestBody AgentPolicyAssignment assignment) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
//            agentPolicyService.saveAssignment(assignment);
//            return ResponseEntity.status(HttpStatus.CREATED).body("Policies assigned to agent successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
//        }
//    }
//
//    @GetMapping("/assignments")
//    public ResponseEntity<List<AgentPolicyAssignment>> getAllAssignments() {
//        List<AgentPolicyAssignment> assignments = agentPolicyService.getAllAssignments();
//        return ResponseEntity.ok(assignments);
//    }}
