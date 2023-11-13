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

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ArticleForTestRepository articleRepository;
    private final UserRepository userRepository;

    // get all posts
    // GET /posts
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    // get post by id
    // GET /posts/{postid}
    public Post getPostById(Long id){
        return postRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 id 없음: id="+id));
    }

    // update post
    // PUT /posts/{postid}
    public Post updatePost(Long postId, PostDto postDto) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            // 현재 로그인한 사용자 정보 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUser = authentication.getName();

            // 게시글의 작성자와 현재 로그인한 사용자 비교
            if (post.getUser().getUserName().equals(currentUser)) {
                post.setContent(postDto.getContent());
                // todo : entitiy에 setter로 접근하는거 지양하랬는데 걍 해버렸음 어케 개선하지
                return postRepository.save(post);
            } else {
                throw new IllegalArgumentException("본인꺼 아니에요");
            }
        } else {
            throw new IllegalArgumentException("not find post id: " + postId);
        }
    }
    // create post
    // POST /posts
    public Post createPost(PostDto postDto) { // todo : 고쳐야될거 투성이
//         Optional<User> user = userRepository.findByUserId(postDto.getUserId());

//        if (user.isPresent()) {
//            Post post = Post.builder()
//                    .postId(postDto.getPostId())
//                    .thumbnail(postDto.getThumbnail())
//                    .user(user.get())
//                    .title(postDto.getTitle())
//                    .content(postDto.getContent())
//                    .build();
//
//            return postRepository.save(post);
//        } else {
//            throw new IllegalArgumentException("not find user id: " + postDto.getUserId());
//        }


        Date now = new Date();
        String id = now.toString();
        Post post = Post.builder()
                    .postId(id)
                    //.username(user.getUsername())
                    .thumbnail(postDto.getThumbnail())
                    .content(postDto.getContent())
                    .build();

        return postRepository.save(post);
    }


    // delete post by id
    // DELETE /posts/{postid}
    public void deletePost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            // 현재 로그인한 사용자 정보 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUser = authentication.getName();

            // 게시글의 작성자와 현재 로그인한 사용자 비교
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
