package com.myblog.article.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myblog.article.entity.Article;
import com.myblog.common.event.ArticleEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArticleEventPublisher {

    private static final String TOPIC = "article-events";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public ArticleEventPublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishUpsert(Article article) {
        ArticleEvent event = buildEvent(article, "UPSERT");
        send(event);
    }

    public void publishDelete(Long articleId) {
        ArticleEvent event = new ArticleEvent();
        event.setType("DELETE");
        event.setArticleId(articleId);
        send(event);
    }

    private ArticleEvent buildEvent(Article article, String type) {
        ArticleEvent event = new ArticleEvent();
        event.setType(type);
        event.setArticleId(article.getId());
        event.setTitle(article.getTitle());
        event.setSummary(article.getSummary());
        event.setContent(article.getContent());
        event.setAuthorId(article.getAuthorId());
        event.setStatus(article.getStatus());
        event.setUpdateTime(article.getUpdateTime());
        return event;
    }

    private void send(ArticleEvent event) {
        try {
            kafkaTemplate.send(TOPIC, event.getArticleId() == null ? null : String.valueOf(event.getArticleId()),
                    objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException ignored) {
            // Keep CRUD success path stable even when event serialization fails.
        }
    }
}
