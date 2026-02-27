package com.myblog.common.event;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ArticleEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type;

    private Long articleId;

    private String title;

    private String summary;

    private String content;

    private Long authorId;

    private Integer status;

    private LocalDateTime updateTime;
}
