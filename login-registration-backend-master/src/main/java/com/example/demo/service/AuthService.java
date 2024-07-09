package com.example.demo.service;

import com.example.demo.appuser.AppUser;
import com.example.demo.appuser.AppUserRepository;
import com.example.demo.userip.UserIp;
import com.example.demo.userip.UserIpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private UserIpRepository userIpRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean authenticateUser(String username, String password, HttpServletRequest request) {
        logger.debug("Authenticating user: {}", username);
        Optional<AppUser> authUser = appUserRepository.findByUsername(username);
        if (authUser.isPresent() && passwordEncoder.matches(password, authUser.get().getPassword())) {
            String clientIp = getClientIp(request);
            Optional<UserIp> existingUserIp = userIpRepository.findByUsername(username);
            UserIp userIp;
            if (existingUserIp.isPresent()) {
                userIp = existingUserIp.get();
                userIp.setIp(clientIp); // Update the IP address
            } else {
                userIp = new UserIp(username, clientIp);
            }
            userIpRepository.save(userIp);
            logger.debug("User authenticated: {}", username);
            return true;
        }
        logger.debug("Authentication failed for user: {}", username);
        return false;
    }

    public boolean registerUser(String username, String password) {
        logger.debug("Registering user: {}", username);
        if (!appUserRepository.findByUsername(username).isPresent()) {
            AppUser newUser = new AppUser();
            newUser.setUsername(username);
            newUser.setPassword(passwordEncoder.encode(password));
            appUserRepository.save(newUser);
            logger.debug("User registered: {}", username);
            return true;
        }
        logger.debug("User already exists or invalid data: {}", username);
        return false;
    }

    private String getClientIp(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-FORWARDED-FOR");
        if (remoteAddr == null || "".equals(remoteAddr)) {
            remoteAddr = request.getRemoteAddr();
        }

        // Ensure the IP address is in IPv4 format
        try {
            InetAddress inetAddress = InetAddress.getByName(remoteAddr);
            if (inetAddress instanceof java.net.Inet6Address) {
                // Convert IPv6 to IPv4 if possible
                byte[] ipv4 = new byte[4];
                System.arraycopy(inetAddress.getAddress(), 12, ipv4, 0, 4);
                inetAddress = InetAddress.getByAddress(ipv4);
                remoteAddr = inetAddress.getHostAddress();
            }
        } catch (UnknownHostException e) {
            logger.error("Unknown host exception: {}", e.getMessage());
        }

        return remoteAddr;
    }
}
