package com.umc.post.data.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserJoinDto {
    private String userId;
    private String password;
    private String role;
    private String userName;
    private String userNickName;
}