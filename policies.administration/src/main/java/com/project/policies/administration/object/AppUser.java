package com.project.policies.administration.object;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    private String username;
    private String password;
    private String ipAddress;
    
    @ManyToOne
    @JoinColumn(name = "firewall_policy_id")
    private FirewallPolicy firewallPolicy;
}
