package com.madmin.policies.services;

import com.madmin.policies.object.Role;
//import com.madmin.policies.object.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

//@Service
//public class UserService implements UserDetailsService {
//
//    // A temporary in-memory map to store user data
//    private Map<String, User> users;
//
//    public UserService() {
//        // In a real application, user data will be retrieved from a database or other data source
//        users = new HashMap<>();
//
//        Role role1 = new Role(1, "Administator");
//        Role role2 = new Role(2, "User");
//        // Sample user data (for demonstration purposes)
//        User adminUser = new User(1, "admin", "admin123", role1);
//        User regularUser = new User(2, "user", "user123", role2);
//
//        // Passwords should be hashed in a real application, but for simplicity, we are storing them as plain text here
//        users.put(adminUser.getUsername(), adminUser);
//        users.put(regularUser.getUsername(), regularUser);
//    }
//
//    public User authenticateUser(String username, String password) {
//        // In a real application, you will validate the user's credentials against the database or any other source
//        // For demonstration purposes, we are using a simple in-memory map
//        User user = users.get(username);
//        if (user != null && user.getPassword().equals(password)) {
//            return user;
//        }
//        return null; // User not found or invalid credentials
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = users.get(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        // Convert your custom User object to a Spring Security User object
//        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
//                .password(user.getPassword())
//                .authorities(user.getRole().getName())
//                .build();
//    }
//}

