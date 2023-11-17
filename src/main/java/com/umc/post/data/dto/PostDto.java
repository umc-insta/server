package com.umc.post.data.dto;

import com.umc.post.config.security.Role;
import com.umc.post.data.entity.Post;
import java.util.List;
import lombok.Data;

@Data
public class PostDto {
    private Long id; // user unique id
    private String userLoginId;
    private String password;
    private String userName;
    private List<Post> posts;
    private Role role;
}
