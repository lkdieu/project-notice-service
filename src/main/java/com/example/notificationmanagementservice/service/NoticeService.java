package com.example.notificationmanagementservice.service;

import com.example.notificationmanagementservice.dto.request.NoticeRequest;
import com.example.notificationmanagementservice.dto.NoticeDto;
import com.example.notificationmanagementservice.entity.Notice;
import com.example.notificationmanagementservice.exception.ServiceException;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface NoticeService {

    Notice createNotice(NoticeRequest noticeRequest) throws ServiceException, IOException;

    Page<Notice> getAllNotice(Integer size, Integer page);

    NoticeDto getDetails(Long id);

    void deleteNotice(Long id) throws Exception;

    Notice updateNotice(NoticeRequest noticeRequest, Long id) throws Exception;

}
