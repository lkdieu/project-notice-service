package com.example.notificationmanagementservice.service;

import com.example.notificationmanagementservice.entity.User;
import com.example.notificationmanagementservice.exception.ResourceNotFoundException;
import com.example.notificationmanagementservice.exception.ServiceException;
import com.example.notificationmanagementservice.repository.UserRepository;
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
public class CustomAccountService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Load UserByUsername
     * @param username username
     * @return UserDetails
     * @throws ResourceNotFoundException If string's byte cannot be obtained
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username).orElse(null);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), new ArrayList<>());
        } else {
            throw new ResourceNotFoundException("Invalid userName");
        }
    }

    /**
     * getUserName
     * @return username
     */
    public String getUserName() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (StringUtils.isEmpty(username)) {
            username = "";
        }
        return username;
    }
}
