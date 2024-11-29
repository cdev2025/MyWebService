package com.example.mywebservice.controller;

import com.example.mywebservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/posts")
public class PostController {
    
    private final PostService postService;
    
    // 게시글 작성
    
    // 게시글 목록 보기
    
    // 게시글 상세 보기
    
    // 게시글 수정하기
    
    // 게시글 삭제하기
}
