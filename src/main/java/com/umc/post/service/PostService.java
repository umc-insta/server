package com.umc.post.service;

import com.umc.post.data.dto.PostDto;
import com.umc.post.data.entity.Post;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface PostService {

//    void uploadForm(MultipartHttpServletRequest multipartHttpServletRequest);

    void upload(PostDto postDto);

    List<Post> getAllPost();

    Post getPostById(Long id);
}
