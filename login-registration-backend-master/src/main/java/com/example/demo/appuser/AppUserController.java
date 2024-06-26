package com.example.demo.appuser;

import com.example.demo.util.JwtTokenProvider;
import com.example.demo.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AppUserController {
    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final List<String> allowedIps;

    @Autowired
    public AppUserController(@Value("${security.allowed-ips}") String allowedIps) {
        this.allowedIps = Arrays.asList(allowedIps.split(","));
    }

    private boolean isIpAllowed(String ipAddress) {
        return allowedIps.contains(ipAddress);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        if (!isIpAllowed(remoteAddr)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied from IP: " + remoteAddr);
        }

        Optional<AppUser> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
            AppUser user = userOptional.get();
            String token = jwtTokenProvider.createToken(username, user.getRole());
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String username, @RequestParam String password, @RequestParam AppUserRole role) {
        if (userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
