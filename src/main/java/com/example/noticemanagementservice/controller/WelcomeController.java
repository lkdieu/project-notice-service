package com.example.noticemanagementservice.controller;

import com.example.noticemanagementservice.dto.request.AuthRequest;
import com.example.noticemanagementservice.config.JwtUtil;
import com.example.noticemanagementservice.util.MessageConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Authentication Manager
 *
 * @author FPT Software
 */
@RestController
@Slf4j
public class WelcomeController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Qualifier("customAccountServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/")
    public String welcome() {
        return MessageConstants.WELCOME;
    }


    /**
     * Create new user
     * @param authRequest userRequest
     * @return generateToken
     * @throws Exception If string's byte cannot be obtained
     */
    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            userDetailsService.loadUserByUsername(authRequest.getUserName());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        } catch (Exception ex) {
            log.error("Start generateToken result {}", ex.getMessage());
            throw new Exception(MessageConstants.INVALID_USERNAME_PASSWORD);
        }
        return jwtUtil.generateToken(authRequest.getUserName());
    }
}
