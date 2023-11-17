package com.umc.post.data.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileDto {
    private UserDto userDto;
    private List<PostResponseDto> postResponseDtos;
}
