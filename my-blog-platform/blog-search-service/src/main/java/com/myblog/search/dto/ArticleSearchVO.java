package com.myblog.search.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleSearchVO {

    private Long id;

    private String title;

    private String summary;

    private String content;

    private Long authorId;

    private Integer status;

    private LocalDateTime updateTime;
}
