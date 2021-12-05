package com.example.notificationmanagementservice.controller;

import com.example.notificationmanagementservice.config.JwtUtil;
import com.example.notificationmanagementservice.dto.NoticeDto;
import com.example.notificationmanagementservice.dto.request.NoticeRequest;
import com.example.notificationmanagementservice.entity.Notice;
import com.example.notificationmanagementservice.entity.User;
import com.example.notificationmanagementservice.service.impl.CustomAccountServiceImpl;
import com.example.notificationmanagementservice.service.NoticeService;
import com.example.notificationmanagementservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = NotificationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class NotificationControllerTest {
    private static final String BASE_URL = "/notices";

    @MockBean
    private NoticeService noticeService;

    @MockBean
    private CustomAccountServiceImpl customAccountServiceImpl;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void registerNotice() throws Exception {
        Notice notice = new Notice();
        NoticeRequest noticeDto = new NoticeRequest();
        noticeDto.setContent("Exec");
        noticeDto.setTitle("adfcsf");
        noticeDto.setEndDate("2021-03-12 10:20:30");
        noticeDto.setStartDate("2021-09-12 10:20:30");
        Mockito.when(noticeService.createNotice(noticeDto)).thenReturn(notice);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(noticeDto));
        mockMvc.perform(requestBuilder);
    }

    @Test
    public void testGetAllNotice() throws Exception {
        List<Notice> notice = new ArrayList<>();
        PageImpl<Notice> noticePage = new PageImpl<>(notice);
        when(noticeService.getAllNotice(Mockito.anyInt(), Mockito.anyInt())).thenReturn(noticePage);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder);
    }

    @Test
    public void testUpdateNotice() throws Exception {
        Notice noticeEntity = new Notice();
        noticeEntity.setContent("content");
        noticeEntity.setTitle("abc");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(noticeEntity));
        mockMvc.perform(requestBuilder);
    }

    @Test
    public void testDeleteNotice() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void testGetNotice() throws Exception {
        NoticeDto noticeDto = new NoticeDto();
        when(noticeService.getDetails(any())).thenReturn(noticeDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test(expected = Exception.class)
    public void testCreateNotice_throwException() throws Exception {
        Notice noticeEntity = new Notice();
        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setContent("Exec");
        noticeDto.setTitle("Exec");
        noticeDto.setEndDate("2021-06-12 10:20:30");
        noticeDto.setStartDate("2021-06-12 10:20:30");
        doThrow(Exception.class).when(noticeService).createNotice(any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(noticeDto));
        mockMvc.perform(requestBuilder).andExpect(status().is5xxServerError());
    }

    @Test
    public void getNoticeByUser() throws Exception {
        List<Notice> notice = new ArrayList<>();
        PageImpl<Notice> noticePage = new PageImpl<>(notice);
        when(noticeService.getNoticesByUser(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString())).thenReturn(noticePage);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL + "username")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder);
    }

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
