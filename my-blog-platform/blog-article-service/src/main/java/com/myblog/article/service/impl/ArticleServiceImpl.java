package com.myblog.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myblog.article.dto.ArticleCreateDTO;
import com.myblog.article.dto.ArticleUpdateDTO;
import com.myblog.article.entity.Article;
import com.myblog.article.exception.BizException;
import com.myblog.article.mapper.ArticleMapper;
import com.myblog.article.messaging.ArticleEventPublisher;
import com.myblog.article.service.ArticleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private final ArticleEventPublisher articleEventPublisher;

    public ArticleServiceImpl(ArticleEventPublisher articleEventPublisher) {
        this.articleEventPublisher = articleEventPublisher;
    }

    @Override
    public Long create(ArticleCreateDTO dto) {
        Article article = new Article();
        BeanUtils.copyProperties(dto, article);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        this.baseMapper.insert(article);
        articleEventPublisher.publishUpsert(article);
        return article.getId();
    }

    @Override
    public void update(ArticleUpdateDTO dto) {
        Article article = this.baseMapper.selectById(dto.getId());
        if (article == null) {
            throw new BizException("Article not found");
        }

        if (dto.getTitle() != null) {
            article.setTitle(dto.getTitle());
        }
        if (dto.getSummary() != null) {
            article.setSummary(dto.getSummary());
        }
        if (dto.getContent() != null) {
            article.setContent(dto.getContent());
        }
        if (dto.getStatus() != null) {
            article.setStatus(dto.getStatus());
        }

        article.setUpdateTime(LocalDateTime.now());
        this.baseMapper.updateById(article);
        articleEventPublisher.publishUpsert(article);
    }

    @Override
    public void delete(Long articleId) {
        int rows = this.baseMapper.deleteById(articleId);
        if (rows == 0) {
            throw new BizException("Article not found");
        }
        articleEventPublisher.publishDelete(articleId);
    }

    @Override
    public Article detail(Long articleId) {
        Article article = this.baseMapper.selectById(articleId);
        if (article == null) {
            throw new BizException("Article not found");
        }
        return article;
    }

    @Override
    public Page<Article> page(int current, int size, String keyword, Integer status) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isBlank()) {
            wrapper.and(w -> w.like(Article::getTitle, keyword)
                    .or()
                    .like(Article::getSummary, keyword));
        }
        if (status != null) {
            wrapper.eq(Article::getStatus, status);
        }
        wrapper.orderByDesc(Article::getUpdateTime);
        return this.baseMapper.selectPage(new Page<>(current, size), wrapper);
    }
}
