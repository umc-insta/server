package com.umc.post.service;

import com.umc.post.config.security.TokenInfo;
import com.umc.post.data.dto.UserInfoDto;
import com.umc.post.data.dto.UserJoinDto;
import com.umc.post.data.dto.UserLoginDto;
import com.umc.post.data.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthService {
    TokenInfo login(UserLoginDto userLoginDto);

    void join(UserJoinDto userJoinDto);

    void delete();

    List<User> getAllUser();
    User getUserById(String id);

    UserInfoDto info();
}
