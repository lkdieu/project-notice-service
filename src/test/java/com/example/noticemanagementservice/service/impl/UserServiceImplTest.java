package com.example.noticemanagementservice.service.impl;

import com.example.noticemanagementservice.dto.request.UserRequest;
import com.example.noticemanagementservice.entity.User;
import com.example.noticemanagementservice.exception.NoticeServiceException;
import com.example.noticemanagementservice.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void whenGetCreateUser_success() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setPassword("12345678");
        userRequest.setUserName("sss");
        Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn("user");
        userService.createUser(userRequest);
    }

    @Test(expected = NoticeServiceException.class)
    public void whenGetCreateUser_throw() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setPassword("12345678");
        userRequest.setUserName("sss");
        User user = new User();
        user.setUserName("sss");
        Mockito.when(userRepository.findByUserName(Mockito.anyString())).thenReturn(Optional.of(user));
        userService.createUser(userRequest);
    }
}
