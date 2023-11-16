package com.umc.post.service;

import com.umc.post.data.dto.PostCreateDto;
import com.umc.post.data.entity.Post;
import java.io.IOException;
import java.util.List;

public interface PostService {
    void uploadPost(PostCreateDto request) throws IOException;

    List<Post> getAllPosts();

    Post getPostById(Long id);

    List<Post> getAllPost();
}
