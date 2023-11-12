package com.umc.post.service;

import com.umc.post.data.entity.User;
import com.umc.post.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserRepository userRepository;

    public User getUserById(String id){
        return userRepository.findByUserId(id)
                .orElseThrow(()->new IllegalArgumentException("해당 id 없음: id="+id));
    }
}
