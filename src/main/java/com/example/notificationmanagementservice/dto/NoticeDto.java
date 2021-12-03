package com.example.notificationmanagementservice.dto;

import com.example.notificationmanagementservice.dto.request.NoticeRequest;
import lombok.*;

import java.util.List;

/**
 * NoticeDto Request
 *
 * @author FPT Software
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDto extends NoticeRequest {
    private Long id;
    private List<String> image;
    private String user;
    private Integer viewNumber;
}
