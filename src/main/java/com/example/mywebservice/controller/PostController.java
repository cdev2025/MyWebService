package com.example.mywebservice.controller;

import com.example.mywebservice.controller.model.PostRequest;
import com.example.mywebservice.controller.model.PostResponse;
import com.example.mywebservice.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/posts")
public class PostController {
    
    private final PostService postService;
    
    // 게시글 작성
    // http://localhost:8080/posts/create
    @GetMapping("/create")
    public String createForm(Model model){
        model.addAttribute("post", PostRequest.builder().build());
        return "create";
    }

    // http://localhost:8080/posts/create
    @PostMapping("/create")
    public String createPost(@ModelAttribute("request") PostRequest request){
        postService.createPost(request);
        return "redirect:/posts"; // 게시글 리스트로이동
    }

    // 게시글 목록 보기
    // http://localhost:8080/posts
    @GetMapping
    public String list(Model model){
        List<PostResponse> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "list";
    }
    
    // 게시글 상세 보기
    
    // 게시글 수정하기
    
    // 게시글 삭제하기
}
