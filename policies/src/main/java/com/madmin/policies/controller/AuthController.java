//package com.madmin.policies.controller;
//
//import com.madmin.policies.utils.JwtTokenProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    private final AuthenticationManager authenticationManager;
//    private final JwtTokenProvider jwtTokenProvider;
//    private final UserDetailsService userDetailsService;
//
//    @Autowired
//    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
//        this.authenticationManager = authenticationManager;
//        this.jwtTokenProvider = jwtTokenProvider;
//        this.userDetailsService = userDetailsService;
//    }
//
////    @PostMapping("/login")
////    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
////        String username = loginRequest.get("username");
////        String password = loginRequest.get("password");
////
////        Authentication authentication = authenticationManager.authenticate(
////                new UsernamePasswordAuthenticationToken(username, password)
////        );
////
////        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
////        String token = jwtTokenProvider.createToken(username, userDetails.getAuthorities().stream()
////                .map(grantedAuthority -> grantedAuthority.getAuthority())
////                .collect(Collectors.toList()));
////
////        return ResponseEntity.ok(Collections.singletonMap("token", token));
////    }
//}
