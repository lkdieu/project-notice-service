package com.example.notificationmanagementservice.service;

import com.example.notificationmanagementservice.dto.request.NoticeRequest;
import com.example.notificationmanagementservice.dto.NoticeDto;
import com.example.notificationmanagementservice.entity.Notice;
import com.example.notificationmanagementservice.exception.NotificationServiceException;
import com.example.notificationmanagementservice.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface NoticeService {

    /**
     * Create new notice
     *
     * @param noticeRequest noticeRequest
     * @return Notice
     * @throws ServiceException If string's byte cannot be obtained
     */
    Notice createNotice(NoticeRequest noticeRequest) throws ServiceException, IOException;

    Page<Notice> getAllNotice(int offset, int limit);

    /**
     * Get Details
     *
     * @param id notice id
     * @return json NoticeDto
     */
    NoticeDto getDetails(Long id);

    /**
     * Delete notice
     *
     * @param id notice id
     * @throws NotificationServiceException If string's byte cannot be obtained
     */
    void deleteNotice(Long id) throws Exception;

    /**
     * Update Notice
     *
     * @param noticeRequest noticeRequest
     * @param id            id
     * @return json noticeEntity
     * @throws ServiceException If string's byte cannot be obtained
     */
    Notice updateNotice(NoticeRequest noticeRequest, Long id) throws Exception;

    /**
     * Get Details
     *
     * @param offset offset
     * @param limit limit
     * @param userName user name
     * @return json Page<Notice>
     */

    Page<Notice> getNoticesByUser(int offset, int limit, String userName) throws NotificationServiceException;
}
