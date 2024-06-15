package com.madmin.policies.controller;

//import com.madmin.policies.object.User;
//import com.madmin.policies.repository.UserRepository;
//import com.madmin.policies.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtUtil jwtUtil;
//    private static final String SECRET_KEY = "TbbxPCD+cas1tMNaEi8x7N/q7RbhFC3ckcS+ZLxKJtYpVJFwn5/dOtjTJ1s+NDL0";
//
//
//    @Autowired
//    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.jwtUtil = jwtUtil;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
//        Optional<User> userOptional = userRepository.findById(username);
//        if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
//            User user = userOptional.get();
//            String ipAddress = request.getRemoteAddr();
//
//            // Verifică dacă adresa IP este diferită de cea stocată
//            if (!ipAddress.equals(user.getIpAddress())) {
//                user.setIpAddress(ipAddress);
//                userRepository.save(user);
//            }
//
//            String token = jwtUtil.generateToken(user);
//            return ResponseEntity.ok(token);
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }
//    }
//}
