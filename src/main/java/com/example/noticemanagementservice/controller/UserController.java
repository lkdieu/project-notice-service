package com.example.noticemanagementservice.controller;

import com.example.noticemanagementservice.dto.request.UserRequest;
import com.example.noticemanagementservice.exception.ServiceException;
import com.example.noticemanagementservice.service.UserService;
import com.example.noticemanagementservice.util.MessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * CREST API to manage user
 *
 * @author FPT Software
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Create new user
     * @param userRequest userRequest
     * @return json noticeEntity
     * @throws ServiceException If string's byte cannot be obtained
     */
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest) throws Exception {
        userService.createUser(userRequest);
        return ResponseEntity.ok().body(MessageConstants.SUCCESS);
    }
}
