package com.madmin.policies.controller;

//import com.madmin.policies.object.Agent;
import com.madmin.policies.services.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("/api/agents")
//public class AgentController {
//    @Autowired
//    private AgentService agentService;
//
//    @PostMapping
//    public ResponseEntity<Agent> createAgent(@RequestBody Agent agent) {
//        return ResponseEntity.ok(agentService.saveAgent(agent));
//    }
//
//    @GetMapping("/{username}")
//    public ResponseEntity<Agent> getAgent(@PathVariable String username) {
//        return ResponseEntity.of(agentService.findByUsername(username));
//    }
//
//    @DeleteMapping("/{username}")
//    public ResponseEntity<Void> deleteAgent(@PathVariable String username) {
//        agentService.deleteAgent(username);
//        return ResponseEntity.ok().build();
//    }
//}
