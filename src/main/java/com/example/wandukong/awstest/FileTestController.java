package com.example.wandukong.awstest;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

@RestController
public class FileTestController {

    @Autowired
    AwsS3Service awsS3Service;

    @PostMapping("/api/test/file")
    public String uploadFile(
            @RequestPart(value = "file") MultipartFile multipartFile)
            throws AmazonServiceException, SdkClientException, IOException {
        // TODO: process POST request

        return awsS3Service.uploadFileV1(multipartFile);
    }

}
