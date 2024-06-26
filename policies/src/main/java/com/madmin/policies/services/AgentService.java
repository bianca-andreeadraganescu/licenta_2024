//package com.madmin.policies.services;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.madmin.policies.object.AgentPolicyAssignment;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class AgentService {
//    private final RedisTemplate<String, Object> redisTemplate;
//
//    @Autowired
//    public AgentService(RedisTemplate<String, Object> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    public void saveAssignment(AgentPolicyAssignment assignment) {
//        redisTemplate.opsForHash().put("AGENT_POLICY_ASSIGNMENTS", assignment.getAgentId(), assignment);
//    }
//
//    public AgentPolicyAssignment getAssignment(String agentId) {
//        Object assignment = redisTemplate.opsForHash().get("AGENT_POLICY_ASSIGNMENTS", agentId);
//        if (assignment instanceof LinkedHashMap) {
//            ObjectMapper mapper = new ObjectMapper();
//            return mapper.convertValue(assignment, AgentPolicyAssignment.class);
//        }
//        return (AgentPolicyAssignment) assignment;
//    }
//
//    public void deleteAssignment(String agentId) {
//        redisTemplate.opsForHash().delete("AGENT_POLICY_ASSIGNMENTS", agentId);
//    }
//
//    public List<AgentPolicyAssignment> getAllAssignments() {
//        return redisTemplate.opsForHash().values("AGENT_POLICY_ASSIGNMENTS").stream()
//                .map(assignment -> {
//                    if (assignment instanceof LinkedHashMap) {
//                        ObjectMapper mapper = new ObjectMapper();
//                        return mapper.convertValue(assignment, AgentPolicyAssignment.class);
//                    }
//                    return (AgentPolicyAssignment) assignment;
//                })
//                .collect(Collectors.toList());
//    }
//}
//
