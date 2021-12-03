package com.example.notificationmanagementservice.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateTimeUtil {

    public static Date convertStringToDate(String stringDate, String dateFormat) {
        if (StringUtils.isEmpty(stringDate)) {
            return null;
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
        try {
            return dateFormatter.parse(stringDate);
        } catch (ParseException e) {
            log.error("Error convert String To Local Date ", e);
        }
        return null;
    }

}
