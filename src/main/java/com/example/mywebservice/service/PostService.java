package com.example.mywebservice.service;

import com.example.mywebservice.controller.model.PostRequest;
import com.example.mywebservice.controller.model.PostResponse;
import com.example.mywebservice.entity.PostEntity;
import com.example.mywebservice.entity.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    // 작성하기
    public PostResponse createPost(PostRequest postRequest) {
        var entity = createRequestToEntity(postRequest);
        var savedPost = postRepository.save(entity);
        var dto = entityToResponse(savedPost);
        return dto;
    }

    // 목록보기
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toList());
    }

    // 상세보기
    public Optional<PostResponse> getPostById(Long id) {
        return postRepository.findById(id)
                .map(this::entityToResponse);
    }

    // 수정하기
    public PostResponse updatePost(Long id, PostRequest postRequest) {
        return postRepository.findById(id)
                .map(post -> {
                    // 해당하는 post가 있으면
                    post.setTitle(postRequest.getTitle());
                    post.setContent(postRequest.getContent());
                    post.setAuthor(postRequest.getAuthor());
                    var savedEntity = postRepository.save(post);
                    return PostResponse.builder()
                            .id(savedEntity.getId())
                            .title(savedEntity.getTitle())
                            .content(savedEntity.getContent())
                            .author(savedEntity.getAuthor())
                            .build();
                })
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    // 삭제하기
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    private PostEntity createRequestToEntity(PostRequest postRequest) {
        PostEntity postEntity = new PostEntity(
                postRequest.getTitle(),
                postRequest.getContent(),
                postRequest.getAuthor()
        );
        return postEntity;
    }

    private PostResponse entityToResponse(PostEntity postEntity) {
        return PostResponse.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .author(postEntity.getAuthor())
                .build();
    }
}
