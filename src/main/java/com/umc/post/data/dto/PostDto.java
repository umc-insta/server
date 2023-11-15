package com.umc.post.data.dto;

import com.umc.post.data.entity.Post;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDto {

    private String postId;
    private String username;
    private String photoUrl;
//    private List<Like> likes;
//    private List<Comment> comments;
    private String content;
    private String thumbnail;
    private LocalDateTime createdAt;

    public Post toEntity(){
        return Post.builder()
                .postId(postId)
                .username(username)
                .photoUrl(photoUrl)
//                .likes(likes)
//                .comments(comments)
                .content(content)
                .thumbnail(thumbnail)
                .createdAt(createdAt)
                .build();
    }



}
