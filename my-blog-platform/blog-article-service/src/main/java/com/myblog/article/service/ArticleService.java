package com.myblog.article.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.myblog.article.dto.ArticleCreateDTO;
import com.myblog.article.dto.ArticleUpdateDTO;
import com.myblog.article.entity.Article;

public interface ArticleService extends IService<Article> {

    Long create(ArticleCreateDTO dto);

    void update(ArticleUpdateDTO dto);

    void delete(Long articleId);

    Article detail(Long articleId);

    Page<Article> page(int current, int size, String keyword, Integer status);
}
