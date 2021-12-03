package com.example.notificationmanagementservice.service.impl;

import com.example.notificationmanagementservice.dto.request.NoticeRequest;
import com.example.notificationmanagementservice.entity.Notice;
import com.example.notificationmanagementservice.entity.User;
import com.example.notificationmanagementservice.exception.ServiceException;
import com.example.notificationmanagementservice.repository.NoticeRepository;
import com.example.notificationmanagementservice.repository.UserRepository;
import com.example.notificationmanagementservice.service.CustomAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class NoticeServiceImplTest {
    @Mock
    private NoticeRepository noticeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NoticeServiceImpl noticeServiceImpl;

    @Mock
    private CustomAccountService customAccountService;

    @Test
    public void whenCallCreateNotice_Success() throws  ServiceException, IOException {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        MultipartFile[] multipartFile = new MultipartFile[0];
        NoticeRequest noticeDto = new NoticeRequest();
        noticeDto.setContent("abc");
        noticeDto.setTitle("abc123");
        noticeDto.setMultipartFile(multipartFile);
        noticeDto.setEndDate("2021-11-11 00:00:00");
        noticeDto.setStartDate("2021-10-10 00:00:00");
        noticeServiceImpl.createNotice(noticeDto);
    }

    @Test
    public void whenCallUpdateNotice_successfull() throws Exception {
        NoticeRequest noticeRequest = new NoticeRequest();
        noticeRequest.setContent("123");
        noticeRequest.setTitle("abc");
        Notice notice = setDataNotice();
        Mockito.when(customAccountService.getUserName()).thenReturn("asas");
        Mockito.when(noticeRepository.findById(any())).thenReturn(Optional.of(notice));
        noticeServiceImpl.updateNotice(noticeRequest, 1L);
    }

    @Test(expected = NullPointerException.class)
    public void whenCallUpdateNotice_throwError() throws Exception {
        NoticeRequest notice = new NoticeRequest();
        notice.setContent("123");
        notice.setTitle("abc");
        Mockito.when(noticeRepository.findById(any())).thenReturn(null);
        noticeServiceImpl.updateNotice(notice,1L);
    }

    @Test
    public void whenCallDeleteNotice_successfull() throws Exception {
        Notice notice = setDataNotice();
        Mockito.when(customAccountService.getUserName()).thenReturn("asas");
        Mockito.when(noticeRepository.findById(any())).thenReturn(Optional.of(notice));
        noticeServiceImpl.deleteNotice(1L);
    }

    @Test(expected = NullPointerException.class)
    public void deleteNotice_throwError() throws Exception {
        Mockito.when(noticeRepository.findById(any())).thenReturn(null);
        noticeServiceImpl.deleteNotice(any());
    }

    @Test
    public void getById_success() throws Exception {
        User user = new User();
        Notice notice = setDataNotice();
        Mockito.when(noticeRepository.findByIdAndEndDateGreaterThanEqualAndIsEnableIsTrue(any(),any())).thenReturn(Optional.of(notice));
        noticeServiceImpl.getDetails(1L);
    }

    @Test(expected = Exception.class)
    public void whenCallGetById_throwError() throws Exception {
        Notice notice = setDataNotice();
        noticeServiceImpl.getDetails(any());
    }

    private Notice setDataNotice() {
        Notice notice = new Notice();
        User user = new User();
        user.setUserName("asas");
        user.setName("1111");
        notice.setTitle("abc");
        notice.setContent("mnbm");
        notice.setUser(user);
        notice.setEndDate(new Date());
        notice.setViewNumber(1);
        return notice;
    }

}
