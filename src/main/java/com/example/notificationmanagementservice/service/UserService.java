package com.example.notificationmanagementservice.service;

import com.example.notificationmanagementservice.dto.request.UserRequest;
import com.example.notificationmanagementservice.exception.ServiceException;

public interface UserService {
    /**
     * Create new notice
     *
     * @param userRequest noticeRequest
     * @throws Exception If string's byte cannot be obtained
     */
    void createUser(UserRequest userRequest) throws Exception;
}
