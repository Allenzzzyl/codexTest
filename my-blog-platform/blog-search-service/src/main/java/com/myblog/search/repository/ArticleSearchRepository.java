package com.myblog.search.repository;

import com.myblog.search.document.ArticleDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleSearchRepository extends ElasticsearchRepository<ArticleDocument, Long> {
}
