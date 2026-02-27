package com.myblog.search.service.impl;

import com.myblog.common.event.ArticleEvent;
import com.myblog.search.document.ArticleDocument;
import com.myblog.search.dto.ArticleSearchQuery;
import com.myblog.search.dto.ArticleSearchVO;
import com.myblog.search.repository.ArticleSearchRepository;
import com.myblog.search.service.ArticleSearchService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleSearchServiceImpl implements ArticleSearchService {

    private final ArticleSearchRepository repository;
    private final ElasticsearchOperations operations;

    public ArticleSearchServiceImpl(ArticleSearchRepository repository, ElasticsearchOperations operations) {
        this.repository = repository;
        this.operations = operations;
    }

    @Override
    public void upsert(ArticleEvent event) {
        ArticleDocument document = new ArticleDocument();
        document.setId(event.getArticleId());
        document.setTitle(event.getTitle());
        document.setSummary(event.getSummary());
        document.setContent(event.getContent());
        document.setAuthorId(event.getAuthorId());
        document.setStatus(event.getStatus());
        document.setUpdateTime(event.getUpdateTime());
        repository.save(document);
    }

    @Override
    public void delete(Long articleId) {
        repository.deleteById(articleId);
    }

    @Override
    public Page<ArticleSearchVO> search(ArticleSearchQuery query) {
        Criteria criteria = new Criteria();
        if (query.getKeyword() != null && !query.getKeyword().isBlank()) {
            Criteria textCriteria = new Criteria("title").matches(query.getKeyword())
                    .or(new Criteria("summary").matches(query.getKeyword()))
                    .or(new Criteria("content").matches(query.getKeyword()));
            criteria = criteria.and(textCriteria);
        }
        if (query.getStatus() != null) {
            criteria = criteria.and(new Criteria("status").is(query.getStatus()));
        }
        if (query.getAuthorId() != null) {
            criteria = criteria.and(new Criteria("authorId").is(query.getAuthorId()));
        }

        PageRequest pageRequest = PageRequest.of(query.getCurrent() - 1, query.getSize());
        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria, pageRequest);
        SearchHits<ArticleDocument> hits = operations.search(criteriaQuery, ArticleDocument.class);

        List<ArticleSearchVO> items = hits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .map(doc -> {
                    ArticleSearchVO vo = new ArticleSearchVO();
                    BeanUtils.copyProperties(doc, vo);
                    return vo;
                })
                .toList();
        return new org.springframework.data.domain.PageImpl<>(items, pageRequest, hits.getTotalHits());
    }
}
