package com.umc.post.data.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
public class ArticleDto {
    private String title;
    private String content;

    @Setter
    private String articleId;
}
