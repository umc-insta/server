package com.umc.post.data.dto;

import com.umc.post.data.entity.Post;
import com.umc.post.data.entity.User;
import lombok.Data;
import java.util.List;

@Data
public class UserProfileDto {

    private String user_id;
    private String usernickname;
    private String username;
    private List<Post> posts;

    public User toEntity(){
        return User.builder()
                .userId(user_id)
                .userNickname(usernickname)
                .userName(username)
                .posts(posts)
                .build();
    }


}

