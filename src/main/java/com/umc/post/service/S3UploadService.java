package com.umc.post.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.text.Normalizer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3UploadService {

    private final AmazonS3 amazonS3;

    //@Value("${cloud.aws.s3.bucket}")
    private String bucket = "test-bucket";

    public String saveFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String nfcFilename = getNFCFilename(originalFilename);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, nfcFilename, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, nfcFilename).toString();
    }

    public void deleteFile(String originalFilename) {
        String nfcFilename = getNFCFilename(originalFilename);
        amazonS3.deleteObject(bucket, nfcFilename);
    }

    // NFC 방식으로 변환하지 않는다면, OS마다 originalFilename을 다르게 처리
    private String getNFCFilename (String originalFilename) {
        return Normalizer.normalize(originalFilename, Normalizer.Form.NFC);
    }

}
