package com.umc.post.service;

import com.umc.post.config.security.TokenInfo;
import com.umc.post.data.dto.UserInfoDto;
import com.umc.post.data.dto.UserJoinDto;
import com.umc.post.data.dto.UserLoginDto;

public interface AuthService {
    TokenInfo login(UserLoginDto userLoginDto);

    void join(UserJoinDto userJoinDto);

    UserInfoDto info();
}
