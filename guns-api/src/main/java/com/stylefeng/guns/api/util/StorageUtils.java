package com.stylefeng.guns.api.util;


import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


@Service
public class StorageUtils {

    public static String uploadFile(File file) {
        //获取文件名
        String originalFilename = null;
        //存储文件夹中
        try {
            originalFilename = "QRCode/" + file.getName();
            //获取文件格式
            String contentType = "application/x-png";
            //获取文件大小
            long size = file.length();
            //获取文件信息
            InputStream inputStream = new FileInputStream(file);
            //创建objectMetaData，并设置文件格式和大小
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(size);
            objectMetadata.setContentType(contentType);
            //设置AccessKeyID
            String accessKeyId = "LTAII6EdJxcTawlQ";
            String secretAccessKey = "WPoDAvl5NMkxHvFToJ1M8YzAfmjnF3";
            String endPoint = "oss-cn-beijing.aliyuncs.com";
            String buckeyName = "molycloud";
            //创建节点信息对象
            PutObjectRequest putObjectRequest = new PutObjectRequest(buckeyName, originalFilename, inputStream, objectMetadata);
            //创建上传地址及秘钥信息对象
            OSSClient ossClient = new OSSClient(endPoint, accessKeyId, secretAccessKey);
            //执行上传操作
            ossClient.putObject(putObjectRequest);

        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回图片名
        return originalFilename;
    }
}
