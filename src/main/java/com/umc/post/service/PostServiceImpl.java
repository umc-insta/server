package com.umc.post.service;

import com.umc.post.data.dto.PostCreateDto;
import com.umc.post.data.entity.Post;
import com.umc.post.data.entity.User;
import com.umc.post.repository.PostRepository;
import com.umc.post.repository.UserRepository;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final S3Service s3Service;
    private final UserRepository userRepository;

    @Override
    public void uploadPost(PostCreateDto request) throws IOException {
        String imageURL = s3Service.upLoadFile(request.getFile());
        User user = userRepository.findByUserId(request.getUserid()).orElseThrow();
        Post post = Post.builder()
                .content(request.getContent())
                .imageUrl(imageURL)
                .build();
        user.addPost(post);
        postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts(){
        return postRepository.findAll();
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
