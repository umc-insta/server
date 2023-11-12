package com.umc.post.controller;

import com.umc.post.data.dto.PostDto;
import com.umc.post.data.entity.Post;
import com.umc.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    // get all posts
    // GET /posts
    @GetMapping("")
    public ResponseEntity<List<Post>> getAllPosts(){
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    // get post by id
    // GET /posts/{postid}
    @GetMapping("/{postid}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postid){
        Post post = postService.getPostById(postid);
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    // create post
    // POST /posts
    @PostMapping("")
    public ResponseEntity<Post> createPost(@RequestBody PostDto postDto){
        Post post = postService.createPost(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    // update post
    // PUT /posts/{postid} ???
    // todo : PATCH 어케쓰는지
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody PostDto postDto) {
        Post updatedPost = postService.updatePost(postId, postDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
    }

    // delete post by id
    // DELETE /posts/{postid}
    @DeleteMapping("/{postid}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
       postService.deletePost(id);
       return ResponseEntity.status(HttpStatus.OK).body(id+ ": post is deleted");
    }

}
