package com.example.mywebservice.service;

import com.example.mywebservice.entity.PostEntity;
import com.example.mywebservice.entity.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    // 작성하기
    public PostEntity createPost(PostEntity postEntity) {
        return postRepository.save(postEntity);
    }

    // 목록보기
    public List<PostEntity> getAllPosts() {
        return postRepository.findAll();
    }

    // 상세보기
    public Optional<PostEntity> getPostById(Long id) {
        return postRepository.findById(id);
    }

    // 수정하기
    public PostEntity updatePost(Long id, PostEntity postEntity) {
        return postRepository.findById(id)
                .map(post -> {
                    // 해당하는 post가 있으면
                    post.setTitle(postEntity.getTitle());
                    post.setContent(postEntity.getContent());
                    post.setAuthor(postEntity.getAuthor());
                    return postRepository.save(post);
                })
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    // 삭제하기
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
