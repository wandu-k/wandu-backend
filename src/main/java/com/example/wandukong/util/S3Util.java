package com.example.wandukong.util;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3Util {

    private final AmazonS3 amazonS3;

    @Value(value = "${cloud.aws.s3.bucket}")
    private String bucketName;

    public void itemfileUpload(MultipartFile itemfile, String objectKey)
            throws IOException {
        // 아이템 파일 업로드

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(itemfile.getContentType());

        amazonS3
                .putObject(new PutObjectRequest(bucketName, objectKey,
                        itemfile.getInputStream(), objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public void itemfileDelete(String filePath) {
        amazonS3
                .deleteObject(new DeleteObjectRequest(bucketName, filePath));
    }

    public String getUrl(String objectKey) {
        return amazonS3.getUrl(bucketName, objectKey).toString();
    }

}
