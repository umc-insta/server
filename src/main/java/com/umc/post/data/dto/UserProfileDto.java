package com.umc.post.data.dto;

import com.umc.post.data.entity.Post;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Setter(AccessLevel.NONE)
public class UserProfileDto {

    @Setter
    private String user_id;

    @Setter
    private String usernickname;

    @Setter
    private String username;

    @Setter
    private List<Post> posts;

}


/*
    DTO만들 때 어노테이션 팁
    https://velog.io/@bwjhj1030/DTO-%EB%A7%8C%EB%93%A4-%EB%95%8C-Lombok-%EA%BF%80%ED%8C%81-%EB%8C%80%EB%B0%A9%EC%B6%9C
 */