package com.myblog.article;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.myblog.article.mapper")
public class BlogArticleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogArticleServiceApplication.class, args);
    }
}
