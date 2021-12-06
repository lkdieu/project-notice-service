package com.example.noticemanagementservice.dto;


import com.example.noticemanagementservice.entity.AttachedFile;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * NoticeDto Request
 *
 * @author FPT Software
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDto {
    private Long id;
    private List<AttachedFile> image;
    private String user;
    private Integer viewNumber;
    private String title;
    private String content;
    private Date startDate;
    private Date endDate;
}
