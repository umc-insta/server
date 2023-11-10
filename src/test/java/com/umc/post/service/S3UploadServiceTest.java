package com.umc.post.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.amazonaws.services.s3.AmazonS3;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Normalizer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

class S3UploadServiceTest {

    private S3UploadService s3UploadService;

    @Mock
    private AmazonS3 mockAmazonS3;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        s3UploadService = new S3UploadService(mockAmazonS3);

        try {
            URL mockUrl = new URL("https://example.com/testfile.txt");
            when(mockAmazonS3.getUrl("test-bucket", "testfile.txt")).thenReturn(mockUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("S3 업로드 테스트")
    @Test
    void uploadTest() throws IOException {
        // given
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "testfile.txt",
                "text/plain",
                "Test file content".getBytes());

        // when

        String uploadedURL = s3UploadService.saveFile(mockMultipartFile);

        // then
        Assertions.assertThat(uploadedURL)
                .isEqualTo("https://example.com/testfile.txt");
    }

}
