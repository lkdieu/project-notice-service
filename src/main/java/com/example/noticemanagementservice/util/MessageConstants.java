package com.example.noticemanagementservice.util;

public class MessageConstants {
    //Format
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    //Field
    public static final String LAST_MODIFIED_DATE = "lastModifiedDate";
    public static final String FOLDER = "image-notice";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String SECRET = "javadoc";

    //Response message
    public static final String SUCCESS = "Success";
    public static final String WELCOME = "Welcome!!";
    public static final String ERROR = "errors";

    //Message error
    public static final String INVALID_NOTICE_ID = "Invalid notice Id: ";
    public static final String INVALID_USERNAME = "Invalid userName";
    public static final String INVALID_USERNAME_PASSWORD = "invalid username/password";

    //URL
    public static final String URL = "/login";
    public static final String URL_USER = "/user";
    public static final String URL_DEFAULT = "/";
    public static final String BEARER_TOKEN = "token";
    public static final String OFFSET = "0";
    public static final String LIMIT = "20";
}
