package com.example.demo.util;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AllowedIpService {

    private Set<String> allowedIps;

    public AllowedIpService() {
        allowedIps = new HashSet<>();
        // Adăugăm aici adresele IP permise manual sau le putem încărca dintr-un fișier de configurare
        allowedIps.add("192.168.0.1");
        allowedIps.add("192.168.0.2");
        allowedIps.add("0:0:0:0:0:0:0:1");
    }

    public boolean isIpAllowed(String ip) {
        return allowedIps.contains(ip);
    }
}
