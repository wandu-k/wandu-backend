package com.example.wandukong.awstest;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;

public interface AwsS3Service {

    String uploadFileV1(MultipartFile multipartFile)
            throws AmazonServiceException, SdkClientException, IOException;

}
