package com.example.noticemanagementservice.dto.request;

import lombok.*;

import javax.validation.constraints.*;

/**
 * User Request
 *
 * @author FPT Software
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "The name is required.")
    private String name;

    @NotBlank(message = "The userName is required.")
    private String userName;

    @NotBlank(message = "The userName date is required.")
    @Min(value = 8, message = "password minimum of 8 characters")
    private String password;

    @NotEmpty(message = "The email address is required.")
    @Email(message = "The email address is invalid.", flags = { Pattern.Flag.CASE_INSENSITIVE })
    private String email;
}
