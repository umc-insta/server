package com.umc.post.data.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Setter(AccessLevel.NONE)
public class PostDto {

    private String postId;
    private String userId;

    @Setter
    private String content;


    private String thumbnail;
    private LocalDateTime createdAt;

}
