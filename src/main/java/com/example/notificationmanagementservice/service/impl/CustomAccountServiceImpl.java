package com.example.notificationmanagementservice.service.impl;

import com.example.notificationmanagementservice.entity.User;
import com.example.notificationmanagementservice.exception.ResourceNotFoundException;
import com.example.notificationmanagementservice.repository.UserRepository;
import com.example.notificationmanagementservice.util.MessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

/**
 * UserServiceImpl
 *
 * @author FPT Software
 */
@Service
public class CustomAccountServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Load UserByUsername
     *
     * @param username username
     * @return UserDetails
     * @throws ResourceNotFoundException If string's byte cannot be obtained
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Check user already exists
        User user = userRepository.findByUserName(username).orElse(null);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), new ArrayList<>());
        } else {
            throw new ResourceNotFoundException(MessageConstants.INVALID_USERNAME);
        }
    }

    /**
     * getUserName
     *
     * @return username
     */
    public String getUserName() {
        //Get current username information
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (StringUtils.isEmpty(username)) {
            username = null;
        }
        return username;
    }
}
