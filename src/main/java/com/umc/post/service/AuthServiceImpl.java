package com.umc.post.service;

import com.umc.post.config.security.JwtTokenProvider;
import com.umc.post.config.security.Role;
import com.umc.post.config.security.SecurityUtil;
import com.umc.post.config.security.TokenInfo;
import com.umc.post.data.dto.UserInfoDto;
import com.umc.post.data.dto.UserJoinDto;
import com.umc.post.data.dto.UserLoginDto;
import com.umc.post.data.entity.User;
import com.umc.post.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public TokenInfo login(UserLoginDto userLoginDto) {
        User user = userRepository.findByUserId(userLoginDto.getUserId()).orElseThrow(() -> new UsernameNotFoundException("아이디 혹은 비밀번호를 확인하세요."));

        boolean matches = passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword());
        if (!matches) throw new BadCredentialsException("아이디 혹은 비밀번호를 확인하세요.");

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword(), user.getAuthorities());

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        tokenInfo.setEmail(user.getUserId());
        tokenInfo.setMemberRole(user.getRole().toString());
        return tokenInfo;
    }

    @Override
    public void join(UserJoinDto userJoinDto) {
        User user = new User();
        user.setUserId(userJoinDto.getUserId());
        user.setPassword(passwordEncoder.encode(userJoinDto.getPassword()));
        if(userJoinDto.getRole().equals("ROLE_ADMIN")) {
            user.setRole(Role.ADMIN);
        }
        else {
            user.setRole(Role.USER);
        }
        user.setUserName(userJoinDto.getUserName());
        userRepository.save(user);
    }

    @Override
    public UserInfoDto info() {
        UserInfoDto userInfoDto = SecurityUtil.getCurrentMemberId();
        return userInfoDto;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepository.findByUserId(userId)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(user.getRole().toString())
                .build();
    }
}
