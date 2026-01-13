package com.example.blog.service;

import com.example.blog.domain.Post;
import com.example.blog.domain.User;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import java.util.List;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final StringRedisTemplate stringRedisTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public PostService(PostRepository postRepository,
                       UserRepository userRepository,
                       StringRedisTemplate stringRedisTemplate,
                       KafkaTemplate<String, String> kafkaTemplate) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.stringRedisTemplate = stringRedisTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<Post> listPosts() {
        return postRepository.findAll();
    }

    public Post getPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
    }

    @Transactional
    public Post createPost(String title, String content, String username) {
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Author not found"));
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(author);
        Post saved = postRepository.save(post);
        kafkaTemplate.send("post-events", "created:" + saved.getId());
        return saved;
    }

    @Transactional
    public Post incrementViews(Long id) {
        Post post = getPost(id);
        post.setViewCount(post.getViewCount() + 1);
        stringRedisTemplate.opsForValue().increment("post:view:" + id);
        kafkaTemplate.send("post-events", "view:" + id);
        return postRepository.save(post);
    }

    @Transactional
    public Post likePost(Long id) {
        Post post = getPost(id);
        post.setLikeCount(post.getLikeCount() + 1);
        stringRedisTemplate.opsForValue().increment("post:like:" + id);
        kafkaTemplate.send("post-events", "like:" + id);
        return postRepository.save(post);
    }
}
