package com.umc.post.data.dto;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter(AccessLevel.NONE)
public class CommentDto {

    @Setter
    private String commentId;

    @Setter
    private String username;

    @Setter
    private String text;

    @Setter
    private LocalDateTime createdAt;
}
