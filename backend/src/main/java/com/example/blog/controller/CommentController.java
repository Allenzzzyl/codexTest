package com.example.blog.controller;

import com.example.blog.domain.Comment;
import com.example.blog.service.CommentService;
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
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentResponse> list(@PathVariable Long postId) {
        return commentService.listComments(postId).stream()
                .map(CommentResponse::from)
                .toList();
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse create(@PathVariable Long postId,
                                  @RequestBody CommentRequest request,
                                  Principal principal) {
        Comment comment = commentService.addComment(postId, principal.getName(), request.content());
        return CommentResponse.from(comment);
    }

    public record CommentRequest(String content) {
    }

    public record CommentResponse(Long id, String author, String content, String createdAt) {
        static CommentResponse from(Comment comment) {
            return new CommentResponse(
                    comment.getId(),
                    comment.getAuthor().getUsername(),
                    comment.getContent(),
                    comment.getCreatedAt().toString()
            );
        }
    }
}
