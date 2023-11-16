package com.umc.post.data.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostCreateDto {
    private String userid; // user login id
    private String content;
    private MultipartFile file;
}
