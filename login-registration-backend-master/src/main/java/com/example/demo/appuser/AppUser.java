package com.example.demo.appuser;

import com.example.demo.util.UserRole;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "app_user", indexes = {
        @Index(name = "idx_username", columnList = "username")
})
public class AppUser {
    @Id
    private String username;
    private String password;
    private String ipAddress;
    @ElementCollection
    private List<String> allowedIpAddresses;
    @Enumerated(EnumType.STRING)
    private AppUserRole role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public List<String> getAllowedIpAddresses() {
        return allowedIpAddresses;
    }

    public void setAllowedIpAddresses(List<String> allowedIpAddresses) {
        this.allowedIpAddresses = allowedIpAddresses;
    }

    public AppUserRole getRole() {
        return role;
    }

    public void setRole(AppUserRole role) {
        this.role = role;
    }
}