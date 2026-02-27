package com.myblog.search.service;

import com.myblog.common.event.ArticleEvent;
import com.myblog.search.dto.ArticleSearchQuery;
import com.myblog.search.dto.ArticleSearchVO;
import org.springframework.data.domain.Page;

public interface ArticleSearchService {

    void upsert(ArticleEvent event);

    void delete(Long articleId);

    Page<ArticleSearchVO> search(ArticleSearchQuery query);
}
