package com.umc.post.service;

import com.umc.post.config.security.JwtTokenProvider;
import com.umc.post.config.security.Role;
import com.umc.post.config.security.SecurityUtil;
import com.umc.post.config.security.TokenInfo;
import com.umc.post.data.dto.PostResponseDto;
import com.umc.post.data.dto.UserDto;
import com.umc.post.data.dto.UserInfoDto;
import com.umc.post.data.dto.UserJoinDto;
import com.umc.post.data.dto.UserListDto;
import com.umc.post.data.dto.UserLoginDto;
import com.umc.post.data.dto.UserProfileDto;
import com.umc.post.data.entity.Post;
import com.umc.post.data.entity.User;
import com.umc.post.repository.PostRepository;
import com.umc.post.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private final PostRepository postRepository;

    @Override
    public TokenInfo login(UserLoginDto userLoginDto) {
        User user = userRepository.findByUserLoginId(userLoginDto.getUserLoginId())
                .orElseThrow(() -> new UsernameNotFoundException("아이디 혹은 비밀번호를 확인하세요."));

        boolean matches = passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword());
        if (!matches) {
            throw new BadCredentialsException("아이디 혹은 비밀번호를 확인하세요.");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserLoginId(), user.getPassword(),
                user.getAuthorities());

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        tokenInfo.setEmail(user.getUserLoginId());
        tokenInfo.setMemberRole(user.getRole().toString());
        return tokenInfo;
    }

    @Override
    public void join(UserJoinDto userJoinDto) {
        if (isDuplicate(userJoinDto.getUserLoginId())) {
            throw new IllegalArgumentException("User already exists with this userId");
        }
        User user = new User();
        user.setUserLoginId(userJoinDto.getUserLoginId());
        user.setPassword(passwordEncoder.encode(userJoinDto.getPassword()));
        user.setUserNickName(userJoinDto.getUserNickName());
        user.setUserName(userJoinDto.getUserName());
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    @Override
    public UserInfoDto info() {
        UserInfoDto userInfoDto = SecurityUtil.getCurrentMemberId();
        return userInfoDto;
    }

    @Override
    public UserListDto searchUsersByPartialLoginId(String userLoginId) {
        List<User> users = userRepository.findAll().stream().filter(e -> e.getUserLoginId().contains(userLoginId))
                .toList();

        List<UserDto> userDtos = users.stream().map(User::toDto).toList();

        UserListDto userListDto = new UserListDto(userDtos);
        return userListDto;
    }

    @Override
    public UserDetails loadUserByUsername(String userLoginId) throws UsernameNotFoundException {
        return userRepository.findByUserLoginId(userLoginId)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    @Override
    public void delete() {
        userRepository.deleteAll();
    }

    @Override
    public List<UserDto> getAllUser() {
        return userRepository.findAll().stream().map(e -> e.toDto()).toList();
    }

    @Override
    public UserProfileDto getUserByLoginId(String userLoginId) {
        User user = userRepository.findByUserLoginId(userLoginId).orElseThrow();

        List<PostResponseDto> postResponseDtos = postRepository.findAll().stream()
                .filter(e -> e.getUser().getUserLoginId().equals(userLoginId))
                .map(Post::toDto).toList();

        UserDto userDto = UserDto.builder()
                .userLoginId(user.getUserLoginId())
                .userNickname(user.getUserNickName())
                .profileImage(null)
                .userName(user.getUsername())
                .build();

        UserProfileDto userProfileDto = UserProfileDto.builder()
                .userDto(userDto)
                .postResponseDtos(postResponseDtos)
                .build();

        return userProfileDto;
    }

    private UserDetails createUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(user.getRole().toString())
                .build();
    }

    /*
        register 시 중복 user id 인지 체크
     */
    private boolean isDuplicate(String userLoginId) {
        return userRepository.findByUserLoginId(userLoginId).isPresent();
    }
}
