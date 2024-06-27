package com.project.policies.administration.services;

import com.project.policies.administration.object.AppUser;
import com.project.policies.administration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String getUserIdByIp(String ipAddress) {
        AppUser user = userRepository.findByIpAddress(ipAddress);
        return user != null ? user.getUsername() : null;
    }

    public AppUser getUserByIp(String ipAddress) {
        return userRepository.findByIpAddress(ipAddress);
    }
}
