package com.umc.post.service;

import com.umc.post.config.security.TokenInfo;
import com.umc.post.data.dto.UserDto;
import com.umc.post.data.dto.UserInfoDto;
import com.umc.post.data.dto.UserJoinDto;
import com.umc.post.data.dto.UserListDto;
import com.umc.post.data.dto.UserLoginDto;
import com.umc.post.data.dto.UserProfileDto;
import com.umc.post.data.entity.User;

import java.util.List;

public interface AuthService {
    TokenInfo login(UserLoginDto userLoginDto);

    void join(UserJoinDto userJoinDto);

    void delete();

    List<UserDto> getAllUser();

    UserProfileDto getUserByLoginId(String userLoginId);

    UserInfoDto info();

    UserListDto searchUsersByPartialLoginId(String userLoginId);
}
