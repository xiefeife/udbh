package com.last.demo.util.oss;


import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: xiehh
 * @Date:2020/11/20 16:39
 * @ClassName:AliyunOssConfig
 */
@Component
@Data
public class AliyunOssConfig  {


    private static String endpoint;

    private static String accessKeyId;

    private static String accessKeySecret;


    public static boolean isOssAttach;//是否存储到oss

    //文件最大上传大小
    public static long FILE_MAX_SIZE = 1024 * 1024 * 1;
    //图片过期时间 100年
    public static long FILE_EXPIRATION_TIME = 1000 * 3600 * 24 * 365 * 100;


    public static String JAVA_BUCKET_NAME ;

    @Value("${oss.endPoiot}")
    public  void setEndpoint(String endpoint) {
        AliyunOssConfig.endpoint = endpoint;
    }
    @Value("${oss.accessKeyId}")
    public  void setAccessKeyId(String accessKeyId) {
        AliyunOssConfig.accessKeyId = accessKeyId;
    }
    @Value("${oss.accessKeySecret}")
    public  void setAccessKeySecret(String accessKeySecret) {
        AliyunOssConfig.accessKeySecret = accessKeySecret;
    }
    @Value("${oss.bucketName}")
    public  void setJavaBucketName(String javaBucketName) {
        JAVA_BUCKET_NAME = javaBucketName;
    }

    @Value("${oss.isOssAttach}")
    public  void setIsOssAttach(boolean isOssAttach) {
        AliyunOssConfig.isOssAttach = isOssAttach;
    }

    public static OSSClient getOssClient() {
       // 创建ClientConfiguration实例，按照您的需要修改默认参数。
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
      // 关闭CNAME选项。
        conf.setSupportCname(false);
      // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, conf);

      // 关闭OSSClient。
     //        ossClient.shutdown();
        return (OSSClient) ossClient;
    }


}
