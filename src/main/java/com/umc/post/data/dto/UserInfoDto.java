package com.umc.post.data.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserInfoDto {
    private String userId;
    private String memberRole;
}