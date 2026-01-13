# 个人博客（React + Spring Cloud）

该项目包含前后端分离的个人博客示例：

- 前端：React + Vite
- 后端：Spring Boot（Spring Cloud 架构可扩展）
- 中间件：MySQL + Redis + Kafka

## 目录结构

- `frontend/` React 前端
- `backend/` Spring Boot 后端

## 后端启动

1. 准备 MySQL、Redis、Kafka 服务。
2. 修改 `backend/src/main/resources/application.yml` 中的连接信息。
3. 启动服务：

```bash
cd backend
./mvnw spring-boot:run
```

## 前端启动

```bash
cd frontend
npm install
npm run dev
```

## 功能概览

- 登录/注册（JWT）
- 权限校验（ROLE_AUTHOR）
- 文章发布与列表/详情
- 浏览量与点赞量统计
- 评论列表与发布
