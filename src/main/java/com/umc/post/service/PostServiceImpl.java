package com.umc.post.service;

import com.umc.post.data.dto.PostCreateDto;
import com.umc.post.data.dto.PostResponseDto;
import com.umc.post.data.entity.Post;
import com.umc.post.data.entity.User;
import com.umc.post.repository.PostRepository;
import com.umc.post.repository.UserRepository;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final S3Service s3Service;
    @Autowired
    private final UserRepository userRepository;

    @Override
    public void uploadPost(PostCreateDto request) throws IOException {
        String imageURL = s3Service.upLoadFile(request.getFile());
        User user = userRepository.findByUserId(request.getUserId()).orElseThrow();
        Post post = Post.builder()
                .content(request.getContent())
                .user(user)
                .imageUrl(imageURL)
                .build();
        user.addPost(post);
        postRepository.save(post);
    }

    @Override
    public List<PostResponseDto> getAllPosts(){
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(Post::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

}
