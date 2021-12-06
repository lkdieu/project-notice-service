package com.example.noticemanagementservice.controller;

import com.example.noticemanagementservice.dto.request.NoticeRequest;
import com.example.noticemanagementservice.dto.NoticeDto;
import com.example.noticemanagementservice.entity.Notice;
import com.example.noticemanagementservice.exception.NoticeServiceException;
import com.example.noticemanagementservice.exception.ServiceException;
import com.example.noticemanagementservice.service.NoticeManageService;
import com.example.noticemanagementservice.util.MessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private NoticeManageService noticeManageService;

    /**
     * Create new notice
     * @param noticeRequest noticeRequest
     * @return json noticeEntity
     * @throws ServiceException If string's byte cannot be obtained
     */
    @PostMapping
    public ResponseEntity<?> registerNotice(@ModelAttribute @Valid @RequestBody NoticeRequest noticeRequest) throws ServiceException, IOException {
        return ResponseEntity.ok(noticeManageService.createNotice(noticeRequest));
    }

    /**
     * Get all notice
     * @param  offset offset
     * @param limit limit
     * @return delete successful
     */
    @GetMapping
    public ResponseEntity<?> getAllNotice(@RequestParam(required = false, defaultValue = MessageConstants.OFFSET) int offset, @RequestParam(required = false, defaultValue = MessageConstants.LIMIT) int limit) {
        return ResponseEntity.ok(noticeManageService.getAllNotice(offset, limit));
    }

    /**
     * Get notice Detail
     * @param  id id
     * @return json noticeEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getNoticeDetail(@PathVariable Long id) {
        NoticeDto notice = noticeManageService.getDetails(id);
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
        Notice noticeUpdate = noticeManageService.updateNotice(noticeRequest, id);
        return ResponseEntity.ok(noticeUpdate);

    }

    /**
     * Update Notice
     * @param  id id
     * @return Success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotice(@PathVariable Long id) throws Exception {
        noticeManageService.deleteNotice(id);
        return ResponseEntity.ok(MessageConstants.SUCCESS);
    }

    /**
     * Get all notice with user name and page info
     *
     * @param offset: paging offset
     * @param limit:  paging limit
     * @return list of notice with paging
     */
    @GetMapping("user/{userName}")
    public ResponseEntity<?> getNoticeByUser(@RequestParam(defaultValue = MessageConstants.OFFSET) int offset,
                                        @RequestParam(defaultValue = MessageConstants.LIMIT) int limit,  @PathVariable String userName) throws NoticeServiceException {
        return ResponseEntity.ok(noticeManageService.getNoticesByUser(offset, limit, userName));
    }


}
