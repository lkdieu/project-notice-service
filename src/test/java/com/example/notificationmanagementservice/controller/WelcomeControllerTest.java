package com.example.notificationmanagementservice.controller;

import com.example.notificationmanagementservice.config.JwtUtil;
import com.example.notificationmanagementservice.dto.request.AuthRequest;
import com.example.notificationmanagementservice.service.impl.CustomAccountServiceImpl;
import com.example.notificationmanagementservice.service.NoticeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = WelcomeController.class)
@AutoConfigureMockMvc(addFilters = false)
@WebAppConfiguration
class WelcomeControllerTest {

    public static final String BASE_URL ="/authenticate";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean(name = "customAccountService")
    private UserDetailsService userDetailsService;

    @MockBean
    private NoticeService noticeService;

    @MockBean
    private CustomAccountServiceImpl customAccountServiceImpl;


    @Test
    public void whenCallGenerateToken_Success() throws Exception{
        AuthRequest authRequest = new AuthRequest();
        authRequest.setPassword("diiiii");
        authRequest.setUserName("123456");
        UserDetails applicationUser = mock(UserDetails.class);
        Authentication authentication = mock(Authentication.class);
        when(userDetailsService.loadUserByUsername(any())).thenReturn(applicationUser);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtUtil.generateToken(any())).thenReturn("authentication");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(authRequest));
        mockMvc.perform(requestBuilder).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void whenCallGenerateToken_throwError() throws Exception{
        AuthRequest authRequest = new AuthRequest();
        authRequest.setPassword("diiiii");
        authRequest.setUserName("123456");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(authRequest.getUserName()));
        mockMvc.perform(requestBuilder).andExpect(status().is4xxClientError());
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}