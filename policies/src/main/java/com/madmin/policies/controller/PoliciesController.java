package com.madmin.policies.controller;


import com.madmin.policies.object.FirewallPolicy;
import com.madmin.policies.services.FirewallPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
public class PoliciesController {
    private final FirewallPolicyService policyService;

    @Autowired
    public PoliciesController(FirewallPolicyService policyService) {
        this.policyService = policyService;
    }

    @PostMapping
    public ResponseEntity<?> createPolicy(@RequestBody FirewallPolicy policy) {
        policyService.savePolicy(policy);
        return ResponseEntity.ok("Policy created");
    }

    @GetMapping("/{id}")
    public ResponseEntity<FirewallPolicy> getPolicy(@PathVariable String id) {
        return ResponseEntity.ok(policyService.getPolicy(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePolicy(@PathVariable String id) {
        policyService.deletePolicy(id);
        return ResponseEntity.ok("Policy deleted");
    }

    @GetMapping
    public ResponseEntity<List<FirewallPolicy>> getAllPolicies() {
        return ResponseEntity.ok(policyService.getAllPolicies());
    }
//    @Autowired
//    private DevService devService;
//
//    @Autowired
//    private LabPoliciesRepository labRepo;
//
//    @Autowired
//    private ExamPoliciesRepository examRepo;
//
//    @Autowired
//    private DefaultPoliciesRepository defaultRepo;
//
//    @PostMapping("/dev")
//    public ResponseEntity<FirewallPolicy> saveDevPolicy(@RequestBody FirewallPolicy policy) throws IOException {
////        File file = new File("src/main/resources/policies/devpolicy.json");
////        String jsonContent = FileUtils.readFileToString(file, "UTF-8");
////        ObjectMapper objectMapper = new ObjectMapper();
////        FirewallPolicy policy = objectMapper.readValue(jsonContent, FirewallPolicy.class);
////        return devService.addPolicy(policy);
//        FirewallPolicy createdPolicy = devService.addPolicy(policy);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdPolicy);
//    }
//    @PostMapping("/default")
//    public FirewallPolicy saveDefaultPolicy() throws IOException{
//        File file = new File("src/main/resources/policies/defaultpolicy.json");
//        String jsonContent = FileUtils.readFileToString(file, "UTF-8");
//        ObjectMapper objectMapper = new ObjectMapper();
//        FirewallPolicy policy = objectMapper.readValue(jsonContent, FirewallPolicy.class);
//        return defaultRepo.save(policy);
//    }
//    @PostMapping("/lab")
//    public FirewallPolicy saveLabPolicy() throws IOException {
//        File file = new File("src/main/resources/policies/labpolicy.json");
//        String jsonContent = FileUtils.readFileToString(file, "UTF-8");
//        ObjectMapper objectMapper = new ObjectMapper();
//        FirewallPolicy policy = objectMapper.readValue(jsonContent, FirewallPolicy.class);
//        return labRepo.save(policy);
//
//    }
//
//    @PostMapping("/exam")
//    public FirewallPolicy saveExamPolicy() throws IOException{
//        File file = new File("src/main/resources/policies/exampolicy.json");
//        String jsonContent = FileUtils.readFileToString(file, "UTF-8");
//        ObjectMapper objectMapper = new ObjectMapper();
//        FirewallPolicy policy = objectMapper.readValue(jsonContent, FirewallPolicy.class);
//        return examRepo.save(policy);
//    }
//    @GetMapping("/check-registration/{id}")
//    public String checkRegistration(@PathVariable String id) {
//        Optional<FirewallPolicy> policyOptional = devRepo.findById(id);
//        if (policyOptional.isPresent()) {
//            return "Registration with ID " + id + " exists in the database.";
//        } else {
//            return "Registration with ID " + id + " does not exist in the database.";
//        }
//    }
}
