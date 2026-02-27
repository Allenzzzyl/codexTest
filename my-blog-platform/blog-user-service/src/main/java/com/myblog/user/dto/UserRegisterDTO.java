package com.myblog.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterDTO {

    @NotBlank(message = "must not be blank")
    @Size(min = 3, max = 32, message = "length must be between 3 and 32")
    private String username;

    @NotBlank(message = "must not be blank")
    @Size(min = 6, max = 64, message = "length must be between 6 and 64")
    private String password;

    @NotBlank(message = "must not be blank")
    @Email(message = "is not a valid email")
    private String email;
}
