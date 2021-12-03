package com.example.notificationmanagementservice.dto.request;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
/**
 * Auth Request
 *
 * @author FPT Software
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    @NotBlank(message = "The userName date is required.")
    private String userName;

    @NotBlank(message = "The userName date is required.")
    @Min(value = 8, message = "password minimum of 8 characters")
    private String password;
}
