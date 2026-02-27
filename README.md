# My Blog Platform

Spring Cloud microservice blog platform with Vue2 frontend.

Spring Cloud 微服务博客平台（含 Vue2 前端）。

## Modules
- `my-blog-platform/blog-common`: shared response wrapper and event models
- `my-blog-platform/blog-user-service`: register/login + BCrypt + JWT
- `my-blog-platform/blog-article-service`: article CRUD + Kafka event producer
- `my-blog-platform/blog-search-service`: Kafka consumer + Elasticsearch sync + search API
- `blog-frontend`: Vue2 frontend pages and API integration

## 模块说明（中文）
- `my-blog-platform/blog-common`：公共响应结构与事件模型
- `my-blog-platform/blog-user-service`：用户注册/登录（BCrypt + JWT）
- `my-blog-platform/blog-article-service`：文章 CRUD（并发送 Kafka 事件）
- `my-blog-platform/blog-search-service`：消费 Kafka 同步 ES，并提供搜索 API
- `blog-frontend`：Vue2 前端页面与接口联调

## Backend APIs

### User service (`8081`)
- `POST /api/user/register`
- `POST /api/user/login`
- `GET /api/user/token/validate`

### Article service (`8082`)
- `POST /api/articles`
- `PUT /api/articles`
- `DELETE /api/articles/{id}`
- `GET /api/articles/{id}`
- `GET /api/articles?current=1&size=10&keyword=&status=`

### Search service (`8083`)
- `GET /api/search/articles?current=1&size=10&keyword=&status=&authorId=`

## 后端接口（中文）

### 用户服务（`8081`）
- `POST /api/user/register`
- `POST /api/user/login`
- `GET /api/user/token/validate`

### 文章服务（`8082`）
- `POST /api/articles`
- `PUT /api/articles`
- `DELETE /api/articles/{id}`
- `GET /api/articles/{id}`
- `GET /api/articles?current=1&size=10&keyword=&status=`

### 搜索服务（`8083`）
- `GET /api/search/articles?current=1&size=10&keyword=&status=&authorId=`

## Local config

### Databases
Create MySQL databases:
- `blog_user_db`
- `blog_article_db`

### 数据库（中文）
请提前创建以下 MySQL 数据库：
- `blog_user_db`
- `blog_article_db`

### Service config files
- `my-blog-platform/blog-user-service/src/main/resources/application.yml`
- `my-blog-platform/blog-article-service/src/main/resources/application.yml`
- `my-blog-platform/blog-search-service/src/main/resources/application.yml`

Adjust:
- MySQL username/password
- Nacos address
- Kafka bootstrap server
- Elasticsearch URI
- JWT secret and expiration

## Run locally

### 1. Start dependencies
- MySQL
- Redis
- Nacos
- Kafka
- Elasticsearch

### 2. Start backend
From `my-blog-platform/`:

```bash
mvn clean install
mvn -pl blog-user-service spring-boot:run
mvn -pl blog-article-service spring-boot:run
mvn -pl blog-search-service spring-boot:run
```

### 3. Start frontend
From `blog-frontend/`:

```bash
npm install
npm run dev
```

## 本地启动（中文）

### 1. 启动依赖服务
- MySQL
- Redis
- Nacos
- Kafka
- Elasticsearch

### 2. 启动后端
在 `my-blog-platform/` 目录执行：

```bash
mvn clean install
mvn -pl blog-user-service spring-boot:run
mvn -pl blog-article-service spring-boot:run
mvn -pl blog-search-service spring-boot:run
```

### 3. 启动前端
在 `blog-frontend/` 目录执行：

```bash
npm install
npm run dev
```

## Docker compose

One command for full stack:

```bash
docker compose up -d --build
```

Compose file: `docker-compose.yml`

## Notes
- Current environment may not have Maven/Node preinstalled.
- Article events are produced to Kafka topic `article-events`.
- Search service consumes `article-events` and upserts/deletes ES index `article`.

## 备注（中文）
- 当前环境可能未预装 Maven / Node。
- 文章变更事件会写入 Kafka 主题 `article-events`。
- 搜索服务消费 `article-events` 后，会对 ES `article` 索引做新增/更新/删除。
