package com.example.notificationmanagementservice.controller;

import com.example.notificationmanagementservice.dto.request.NoticeRequest;
import com.example.notificationmanagementservice.dto.NoticeDto;
import com.example.notificationmanagementservice.entity.Notice;
import com.example.notificationmanagementservice.exception.NotificationServiceException;
import com.example.notificationmanagementservice.exception.ServiceException;
import com.example.notificationmanagementservice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

/**
 * CREST API to manage notification information.
 *
 * @author FPT Software
 */
@RestController
@RequestMapping("/notices")
public class NotificationController {
    @Autowired
    private NoticeService noticeService;

    /**
     * Create new notice
     * @param noticeRequest noticeRequest
     * @return json noticeEntity
     * @throws ServiceException If string's byte cannot be obtained
     */
    @PostMapping
    public ResponseEntity<?> registerNotice(@ModelAttribute @Valid @RequestBody NoticeRequest noticeRequest) throws ServiceException, IOException {
        return ResponseEntity.ok(noticeService.createNotice(noticeRequest));
    }

    /**
     * Get all notice
     * @param  size size
     * @param page page
     * @return delete successful
     */
    @GetMapping
    public ResponseEntity<?> getAllNotice(@RequestParam(required = false, defaultValue = "20") int offset, @RequestParam(required = false, defaultValue = "20") int limit) {
        return ResponseEntity.ok(noticeService.getAllNotice(offset, limit));
    }

    /**
     * Get notice Detail
     * @param  id id
     * @return json noticeEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getNoticeDetail(@PathVariable Long id) {
        NoticeDto notice = noticeService.getDetails(id);
        return ResponseEntity.ok(notice);
    }

    /**
     * Update Notice
     * @param  id id
     * @param noticeRequest notice request
     * @return json noticeEntity
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNotice(@PathVariable Long id, @ModelAttribute @Valid @RequestBody NoticeRequest noticeRequest) throws Exception {
        Notice noticeUpdate = noticeService.updateNotice(noticeRequest, id);
        return ResponseEntity.ok(noticeUpdate);

    }

    /**
     * Update Notice
     * @param  id id
     * @return Success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotice(@PathVariable Long id) throws Exception {
        noticeService.deleteNotice(id);
        return ResponseEntity.ok("Success");
    }

    /**
     * Get all notice with user name and page info
     *
     * @param offset: paging offset
     * @param limit:  paging limit
     * @return list of notice with paging
     */
    @GetMapping("/user")
    public ResponseEntity<?> getNoticeByUser(@RequestParam(defaultValue = "0") int offset,
                                        @RequestParam(defaultValue = "10") int limit,  @PathVariable String userName) throws NotificationServiceException {
        return ResponseEntity.ok(noticeService.getNoticesByUser(offset, limit, userName));
    }


}
