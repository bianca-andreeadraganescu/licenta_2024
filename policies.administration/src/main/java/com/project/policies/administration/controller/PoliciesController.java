package com.project.policies.administration.controller;

import com.project.policies.administration.object.AppUser;
import com.project.policies.administration.object.FirewallPolicy;
import com.project.policies.administration.services.FirewallPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delievery")
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

    @PostMapping("/policies/apply")
    public ResponseEntity<?> applyPolicy(@RequestBody String policyId) {
        FirewallPolicy policy = policyService.getPolicy(policyId);
        if (policy != null) {
            // Simularea trimiterii politicii către aplicația agent (Aplicatia 4)
            return ResponseEntity.ok("Policy applied to agent");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Policy not found");
        }
    }

//    @GetMapping("/users/{username}/policy")
//    public ResponseEntity<?> getUserPolicy(@PathVariable String username) {
//        AppUser user = userRepository.findByUsername(username);
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//        }
//
//        FirewallPolicy policy = user.getFirewallPolicy();
//        return policy != null ? ResponseEntity.ok(policy) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
//    @Autowired
//    private DevPoliciesRepository devRepo;
//    @Autowired
//    private LabPoliciesRepository labRepo;
//
//    @Autowired
//    private ExamPoliciesRepository examRepo;
//
//    @Autowired
//    private DefaultPoliciesRepository defaultRepo;
//    @GetMapping("/dev")
//    public List<FirewallPolicy> getAllDevPolicies() {
//        return devRepo.findAll();
//    }
//
//    @GetMapping("/dev/{id}")
//    public FirewallPolicy findDevPolicy(@PathVariable int id) {
//        Optional<FirewallPolicy> policyOptional = Optional.ofNullable(devRepo.findPolicyById(id));
//        if (policyOptional.isPresent()) {
//            System.out.println( "Registration with ID " + id + " exists in the database.");
//        } else {
//            System.out.println( "Registration with ID " + id + " does not exist in the database.");
//        }
//        return devRepo.findPolicyById(id);
//    }
//    @GetMapping("/lab")
//    public List<FirewallPolicy> getAllLabPolicies(){
//        return labRepo.findAll();
//    }
//    @GetMapping("/lab/{id}")
//    public FirewallPolicy findLabPolicy(@PathVariable int id){
//        return labRepo.findPolicyById(id);
//    }
//
//    @GetMapping("/exam")
//    public List<FirewallPolicy> getAllExamPolicies(){
//        return examRepo.findAll();
//    }
//
//    @GetMapping("/exam/{id}")
//    public FirewallPolicy findExamPolicy(@PathVariable int id){
//        return examRepo.findPolicyById(id);
//    }
//
//    @GetMapping("/default")
//    public List<FirewallPolicy> getAllDefaultPolicies(){
//        return defaultRepo.findAll();
//    }
//
//    @GetMapping("/default/{id}")
//    public FirewallPolicy findDefaultPolicy(@PathVariable int id){
//        Optional<FirewallPolicy> policyOptional = Optional.ofNullable(defaultRepo.findPolicyById(id));
//        if (policyOptional.isPresent()) {
//            System.out.println( "Registration with ID " + id + " exists in the database.");
//        } else {
//            System.out.println( "Registration with ID " + id + " does not exist in the database.");
//        }
//        return defaultRepo.findPolicyById(id);
//    }
}
