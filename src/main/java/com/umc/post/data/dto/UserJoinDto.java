package com.umc.post.data.dto;

import lombok.Data;

@Data
public class UserJoinDto {
    private String userLoginId;
    private String password;
    private String role;
    private String userName;
    private String userNickName;
}