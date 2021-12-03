package com.example.notificationmanagementservice.service;

import com.example.notificationmanagementservice.dto.request.UserRequest;

public interface UserService {
    void createUser(UserRequest userRequest) throws Exception;
}
