package com.umc.post.data.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostResponseDto {
    private Long postId;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;
    private Long userId;
    private String userNickName;
    private String userName;
}
