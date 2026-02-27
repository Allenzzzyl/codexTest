package com.myblog.search.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myblog.common.event.ArticleEvent;
import com.myblog.search.service.ArticleSearchService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ArticleEventConsumer {

    private final ObjectMapper objectMapper;
    private final ArticleSearchService articleSearchService;

    public ArticleEventConsumer(ObjectMapper objectMapper, ArticleSearchService articleSearchService) {
        this.objectMapper = objectMapper;
        this.articleSearchService = articleSearchService;
    }

    @KafkaListener(topics = "article-events", groupId = "blog-search-service")
    public void onMessage(String payload) throws JsonProcessingException {
        ArticleEvent event = objectMapper.readValue(payload, ArticleEvent.class);
        if ("DELETE".equalsIgnoreCase(event.getType())) {
            articleSearchService.delete(event.getArticleId());
            return;
        }
        articleSearchService.upsert(event);
    }
}
