package com.umc.post.service;

import com.umc.post.data.dto.ArticleDto;
import com.umc.post.data.dto.PostDto;
import com.umc.post.data.dto.UserProfileDto;
import com.umc.post.data.entity.ArticleForTest;
import com.umc.post.data.entity.Post;
import com.umc.post.data.entity.User;
import com.umc.post.repository.ArticleForTestRepository;
import com.umc.post.repository.PostRepository;
import com.umc.post.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ArticleForTestRepository articleRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    @Transactional
    public Post getPostById(Long id){
        return postRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 id 없음: id="+id));
    }

    @Transactional
    public Post updatePost(Long postId, PostDto postDto) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUser = authentication.getName();

            if (post.getUser().getUserName().equals(currentUser)) {
                post.setContent(postDto.getContent());
                return postRepository.save(post);
            } else {
                throw new IllegalArgumentException("본인꺼 아니에요");
            }
        } else {
            throw new IllegalArgumentException("not find post id: " + postId);
        }
    }
    @Transactional
    public Post createPost(Post post) {
        return postRepository.save(post);
    }


    @Transactional
    public void deletePost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUser = authentication.getName();

            if (post.getUser().getUserName().equals(currentUser)) {
                postRepository.deleteById(postId);
            } else {
                throw new IllegalArgumentException("본인꺼 아니에요");
            }
        } else {
            throw new IllegalArgumentException("not find post id: " + postId);
        }
    }


    /*
        ARTICLE CREATE
        TEST

     */
    @Transactional
    public ArticleForTest createArticle(ArticleDto articleDto){
        Date now = new Date();
        String id = now.toString();
        ArticleForTest article = ArticleForTest.builder()
                .articleId(id)
                .title(articleDto.getTitle())
                .content(articleDto.getContent())
                .build();

        return articleRepository.save(article);
    }

}
