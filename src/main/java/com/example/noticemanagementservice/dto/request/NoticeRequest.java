package com.example.noticemanagementservice.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

/**
 * Notice Request
 *
 * @author FPT Software
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeRequest {

    @NotBlank(message = "The title date is required.")
    private String title;

    @NotBlank(message = "The content date is required.")
    private String content;

    @NotBlank(message = "The startDate date is required.")
    private String startDate;

    @NotBlank(message = "The endDate date is required.")
    private String endDate;

    private MultipartFile[] multipartFile;
}
