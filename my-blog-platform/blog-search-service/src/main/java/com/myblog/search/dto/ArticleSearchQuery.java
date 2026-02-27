package com.myblog.search.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ArticleSearchQuery {

    private String keyword;

    private Integer status;

    private Long authorId;

    @Min(value = 1, message = "must be >= 1")
    private Integer current = 1;

    @Min(value = 1, message = "must be >= 1")
    private Integer size = 10;
}
