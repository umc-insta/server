package com.umc.post.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.text.Normalizer;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class
S3Service implements FileUploadService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String upLoadFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String nfcFilename = convertOriginalToNFC(originalFilename);
        String changedFilename = changeImageNameWithLocalDateTime(nfcFilename);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, changedFilename, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, changedFilename).toString();
    }

    @Override
    public void deleteFile(String originalFilename) {
        String nfcFilename = convertOriginalToNFC(originalFilename);
        amazonS3.deleteObject(bucket, nfcFilename);
    }

    // NFC 방식으로 변환하지 않는다면, OS마다 originalFilename을 다르게 처리
    private String convertOriginalToNFC(String originalFilename) {
        return Normalizer.normalize(originalFilename, Normalizer.Form.NFC);
    }

    private String changeImageNameWithLocalDateTime(String originalFilename) {
        return originalFilename + "_" + LocalDateTime.now();
    }
}
