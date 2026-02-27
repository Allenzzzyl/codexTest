# my-blog-platform

## Modules
- `blog-common`: shared response model `R<T>`
- `blog-user-service`: user registration/login service
- `blog-article-service`: article CRUD service

## Current user-service features
- Register with BCrypt password hashing
- Login with real JWT token generation
- JWT token parsing and validation endpoint (`GET /api/user/token/validate`)
- Global exception handling via `@RestControllerAdvice`
- Backward compatibility for legacy MD5 password records (auto-upgrade to BCrypt after successful login)

## Current article-service features
- Article create/update/delete/detail/page APIs
- Global exception handling via `@RestControllerAdvice`
- Request parameter validation on create/update

## Key config
Update `my-blog-platform/blog-user-service/src/main/resources/application.yml`:
- `spring.datasource.*`: set your MySQL connection
- `jwt.secret`: base64 key (>= 32 bytes after decoding)
- `jwt.expiration-seconds`: token TTL in seconds

Update `my-blog-platform/blog-article-service/src/main/resources/application.yml`:
- `spring.datasource.*`: set your MySQL connection

## Local prerequisites
- Java 17+
- Maven 3.9+
- MySQL with database `blog_user_db`
- MySQL with database `blog_article_db`
- Optional: Nacos server at `127.0.0.1:8848`

## Build and test
From `my-blog-platform/`:

```bash
mvn clean test
mvn -pl blog-user-service spring-boot:run
mvn -pl blog-article-service spring-boot:run
```
