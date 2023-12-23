package com.atguigu.tingshu.album.service.impl;

import com.atguigu.tingshu.album.config.MinioConstantProperties;
import com.atguigu.tingshu.album.service.FileUploadService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private MinioConstantProperties minioConstantProperties;

    @Override
    public String upload(MultipartFile file) {
        String url = "";
        try {
            // Create a minioClient with the MinIO server playground, its access key and secret key.
            //  ctrl+p
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(minioConstantProperties.getEndpointUrl())
                            .credentials(minioConstantProperties.getAccessKey(), minioConstantProperties.getSecreKey())
                            .build();

            // Make 'asiatrip' bucket if not exist.
            boolean found =
                    false;
            found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioConstantProperties.getBucketName()).build());
            if (!found) {
                // Make a new bucket called 'asiatrip'.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioConstantProperties.getBucketName()).build());
            } else {
                System.out.println("Bucket " + minioConstantProperties.getBucketName() + " already exists.");
            }

            //  文件：
            //  new File().length();  数组长度:length  集合长度:size()  字符串长度: length();  ()--> 方法 没有(); 属性
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            // Upload known sized input stream.
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(minioConstantProperties.getBucketName()).object(fileName).stream(
                                    file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());
            //  获取到上传之后的文件路径：
            //  http://192.168.10.130:9000/albums/atguigu.jpg
            url = minioConstantProperties.getEndpointUrl() + "/" + minioConstantProperties.getBucketName() + "/" + fileName;
            System.out.println("url:\t" + url);
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return url;
    }
}