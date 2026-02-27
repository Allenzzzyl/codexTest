package com.myblog.article.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ArticleCreateDTO {

    @NotBlank(message = "must not be blank")
    @Size(max = 200, message = "length must be <= 200")
    private String title;

    @Size(max = 500, message = "length must be <= 500")
    private String summary;

    @NotBlank(message = "must not be blank")
    private String content;

    @NotNull(message = "must not be null")
    private Long authorId;

    @NotNull(message = "must not be null")
    private Integer status;
}
