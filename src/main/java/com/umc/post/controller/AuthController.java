package com.umc.post.controller;

import com.umc.post.config.security.TokenInfo;
import com.umc.post.data.dto.UserDto;
import com.umc.post.data.dto.UserInfoDto;
import com.umc.post.data.dto.UserJoinDto;
import com.umc.post.data.dto.UserListDto;
import com.umc.post.data.dto.UserLoginDto;
import com.umc.post.data.dto.UserProfileDto;
import com.umc.post.data.entity.User;
import com.umc.post.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public TokenInfo login(@RequestBody UserLoginDto userLoginDto) {
        return authService.login(userLoginDto);
    }

    @PostMapping("/register")
    public void register(@RequestBody UserJoinDto userJoinDto) {
        authService.join(userJoinDto);
    }

    @GetMapping("/info")
    public UserInfoDto info() {
        return authService.info();
    }

    @GetMapping("/users")
    public List<UserDto> getAllUser(){
        return authService.getAllUser();
    }

    @GetMapping("/users/{userLoginId}")
    public UserProfileDto getUserById(@PathVariable String userLoginId){
        return authService.getUserByLoginId(userLoginId);
    }

    @DeleteMapping("/users")
    public ResponseEntity<String> deleteAll(){
        authService.delete();
        return ResponseEntity.status(HttpStatus.OK).body("all users deleted");
    }

    @GetMapping("/users/search")
    public UserListDto searchUsersByPartialLoginId(@RequestParam String userLoginId) {
        return authService.searchUsersByPartialLoginId(userLoginId);
    }
}
