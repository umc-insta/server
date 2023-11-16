package com.umc.post.controller;

import com.umc.post.data.dto.PostCreateDto;
import com.umc.post.data.entity.Post;
import com.umc.post.service.PostServiceImpl;
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    PostServiceImpl postServiceImpl;

    @PostMapping("/upload")
    public void upload(@ModelAttribute PostCreateDto postCreateDto) throws IOException {
        postServiceImpl.uploadPost(postCreateDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<Post>> getAllPosts(){
        List<Post> posts = postServiceImpl.getAllPosts();
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

}
