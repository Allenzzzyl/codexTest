package com.example.blog.controller;

import com.example.blog.domain.Post;
import com.example.blog.service.PostService;
import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostSummary> list() {
        return postService.listPosts().stream()
                .map(PostSummary::from)
                .toList();
    }

    @GetMapping("/{id}")
    public PostDetail detail(@PathVariable Long id) {
        return PostDetail.from(postService.getPost(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('AUTHOR')")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDetail create(@RequestBody PostRequest request, Principal principal) {
        Post post = postService.createPost(request.title(), request.content(), principal.getName());
        return PostDetail.from(post);
    }

    @PostMapping("/{id}/view")
    public PostDetail view(@PathVariable Long id) {
        return PostDetail.from(postService.incrementViews(id));
    }

    @PostMapping("/{id}/like")
    public PostDetail like(@PathVariable Long id) {
        return PostDetail.from(postService.likePost(id));
    }

    public record PostRequest(String title, String content) {
    }

    public record PostSummary(Long id, String title, long viewCount, long likeCount) {
        static PostSummary from(Post post) {
            return new PostSummary(post.getId(), post.getTitle(), post.getViewCount(), post.getLikeCount());
        }
    }

    public record PostDetail(Long id, String title, String content, long viewCount, long likeCount, String author) {
        static PostDetail from(Post post) {
            return new PostDetail(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getViewCount(),
                    post.getLikeCount(),
                    post.getAuthor().getUsername()
            );
        }
    }
}
