package com.umc.post.controller;

import com.umc.post.data.dto.UserProfileDto;
import com.umc.post.data.entity.User;
import com.umc.post.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final AuthService authService;


    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable String userId) {

        User user = authService.getUserById(userId);

        // User 정보를 UserProfileDto로 변환
        UserProfileDto userProfileDto = convertToUserProfileDto(user);
        return ResponseEntity.ok(userProfileDto);
    }

    private UserProfileDto convertToUserProfileDto(User user) {
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setUser_id(user.getUserId());
        userProfileDto.setUsernickname(user.getUserNickname());
        userProfileDto.setUsername(user.getUserName());
        userProfileDto.setPosts(user.getPosts());
        return userProfileDto;
    }
}
