package com.example.wandukong.awstest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class AwsS3ServiceImpl implements AwsS3Service {

    @Autowired
    AmazonS3 amazonS3Client;

    @Value(value = "${cloud.aws.s3.bucket}")
    private String bucketName;

    @Override
    public String uploadFileV1(MultipartFile multipartFile)
            throws AmazonServiceException, SdkClientException, java.io.IOException {
        String fileName = CommonUtils.buildFileName(multipartFile.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());

        amazonS3Client
                .putObject(new PutObjectRequest(bucketName, fileName, multipartFile.getInputStream(), objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }

}
