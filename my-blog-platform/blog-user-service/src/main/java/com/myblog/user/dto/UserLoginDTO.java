package com.myblog.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDTO {

    @NotBlank(message = "must not be blank")
    private String username;

    @NotBlank(message = "must not be blank")
    private String password;
}
