package com.myblog.article.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myblog.article.dto.ArticleCreateDTO;
import com.myblog.article.dto.ArticleUpdateDTO;
import com.myblog.article.entity.Article;
import com.myblog.article.service.ArticleService;
import com.myblog.common.domain.R;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public R<Long> create(@Valid @RequestBody ArticleCreateDTO dto) {
        return R.success(articleService.create(dto));
    }

    @PutMapping
    public R<Void> update(@Valid @RequestBody ArticleUpdateDTO dto) {
        articleService.update(dto);
        return R.success();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable("id") Long id) {
        articleService.delete(id);
        return R.success();
    }

    @GetMapping("/{id}")
    public R<Article> detail(@PathVariable("id") Long id) {
        return R.success(articleService.detail(id));
    }

    @GetMapping
    public R<Page<Article>> page(
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer status) {
        return R.success(articleService.page(current, size, keyword, status));
    }
}
