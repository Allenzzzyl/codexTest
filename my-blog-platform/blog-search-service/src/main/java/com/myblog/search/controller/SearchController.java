package com.myblog.search.controller;

import com.myblog.common.domain.R;
import com.myblog.search.dto.ArticleSearchQuery;
import com.myblog.search.dto.ArticleSearchVO;
import com.myblog.search.service.ArticleSearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final ArticleSearchService articleSearchService;

    @GetMapping("/articles")
    public R<Page<ArticleSearchVO>> searchArticles(@Valid @ModelAttribute ArticleSearchQuery query) {
        return R.success(articleSearchService.search(query));
    }
}
