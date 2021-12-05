package com.example.noticemanagementservice.exception;

public class NoticeServiceException extends ServiceException {
    public NoticeServiceException(String message, String errorCode) {
        super(message, errorCode);
    }
}
