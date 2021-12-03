package com.example.notificationmanagementservice.service.impl;

import com.example.notificationmanagementservice.dto.request.UserRequest;
import com.example.notificationmanagementservice.entity.User;
import com.example.notificationmanagementservice.exception.NotificationServiceException;
import com.example.notificationmanagementservice.exception.ServiceException;
import com.example.notificationmanagementservice.repository.UserRepository;
import com.example.notificationmanagementservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl
 *
 * @author FPT Software
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Create new user
     * @param userRequest userRequest
     * @throws NotificationServiceException If string's byte cannot be obtained
     */

    @Override
    public void createUser(UserRequest userRequest) throws Exception {
        //check userName
        User userName = userRepository.findByUserName(userRequest.getUserName()).orElse(null);
        if (userName == null) {
            User user = new User();
            user.setUserName(userRequest.getUserName());
            user.setName(userRequest.getName());
            user.setGmail(userRequest.getEmail());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            user.setIsEnable(true);
            userRepository.save(user);
        }
        else {
            throw new NotificationServiceException(HttpStatus.FORBIDDEN.name(), HttpStatus.FORBIDDEN.getReasonPhrase());
        }

    }
}
