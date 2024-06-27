//package com.project.policies.administration.utils;
//
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class AgentSimulator {
//    private final RestTemplate restTemplate = new RestTemplate();
//    private final String serverUrl = "http://localhost:9999/api/delivery/agent-connect";
//    private final String ipAddress;
//    private final String checksum;
//
//    public AgentSimulator(String ipAddress, String checksum) {
//        this.ipAddress = ipAddress;
//        this.checksum = checksum;
//        startAgent();
//    }
//
//    private void startAgent() {
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                checkPolicy();
//            }
//        }, 0, 60000); // Check policy every 60 seconds
//    }
//
//    private void checkPolicy() {
//        AgentRequest request = new AgentRequest(ipAddress, checksum);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<AgentRequest> entity = new HttpEntity<>(request, headers);
//
//        ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, entity, String.class);
//        if (response.getStatusCode().is2xxSuccessful()) {
//            System.out.println("Server response: " + response.getBody());
//        } else {
//            System.out.println("Failed to connect to the server");
//        }
//    }
//
//    public static void main(String[] args) {
//        new AgentSimulator("192.168.1.1", "123456");
//    }
//}
