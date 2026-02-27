package com.myblog.article.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ArticleUpdateDTO {

    @NotNull(message = "must not be null")
    private Long id;

    @Size(max = 200, message = "length must be <= 200")
    private String title;

    @Size(max = 500, message = "length must be <= 500")
    private String summary;

    private String content;

    private Integer status;
}
