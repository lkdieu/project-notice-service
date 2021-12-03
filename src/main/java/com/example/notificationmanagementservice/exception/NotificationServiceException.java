package com.example.notificationmanagementservice.exception;

public class NotificationServiceException extends ServiceException {
    public NotificationServiceException(String message, String errorCode) {
        super(message, errorCode);
    }
}
