package com.example.noticemanagementservice.exception;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceException extends Exception {
    private String errorCode;

    public ServiceException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
