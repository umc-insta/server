package com.umc.post.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    String upLoadFile(MultipartFile multipartFile) throws IOException;

    void deleteFile(String originalFilename);
}
