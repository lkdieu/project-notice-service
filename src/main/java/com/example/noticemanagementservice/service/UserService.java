package com.example.noticemanagementservice.service;

import com.example.noticemanagementservice.dto.request.UserRequest;

public interface UserService {
    /**
     * Create new notice
     *
     * @param userRequest noticeRequest
     * @throws Exception If string's byte cannot be obtained
     */
    void createUser(UserRequest userRequest) throws Exception;
}
