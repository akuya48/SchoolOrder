package com.sky.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;

/**
 * 用于文件上传
 */
@Data
@AllArgsConstructor
@Slf4j
public class AliOssUtil {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    /**
     * 用于文件上传
     * @param bytes
     * @param objectName
     * @return
     */
    public String upload(byte[] bytes,String objectName){
        //创建OssClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        try{
            ossClient.putObject(bucketName,objectName,new ByteArrayInputStream(bytes));
        }catch (OSSException e){
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + e.getErrorMessage());
            System.out.println("Error Code:" + e.getErrorMessage());
            System.out.println("Request Id:" + e.getRequestId());
            System.out.println("Host Id:" + e.getHostId());
        }catch (ClientException e){
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + e.getMessage());
        }finally {
            if(ossClient!=null){
                ossClient.shutdown();//关闭
            }
        }
        //文件访问路径规则 https://BucketName.Endpoint/ObjectName
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder.append(bucketName)
                .append(".")
                .append(endpoint)
                .append("/")
                .append(objectName);
        log.info("文件上传到：{}",stringBuilder.toString());
        return stringBuilder.toString();
    }
}
