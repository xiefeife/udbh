package com.last.demo.util;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * @author: xiehh
 * @Date:2020/11/24 11:08
 * @ClassName:TestOSS
 */
public class TestOSS {

    private static String endpoint = "oss-cn-xixian-xxunicom-d01-a.ops.develop.xixian.unicom.local";
    private static String accessKeyId = "WlnY2ki8ubpp091E";
    private static String accessKeySecret = "Eif0vexSUgIi91IcMgPdDbCERaHFSB";


    public static void main(String[] args) throws Exception{
        // 创建OSSClient实例。
        // 创建ClientConfiguration实例，您可以按照实际情况修改默认参数。
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        // 设置是否支持CNAME。CNAME是指将自定义域名绑定到存储空间上。
        conf.setSupportCname(false);
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret,conf);
        System.out.println("连接创建成功!!");

        InputStream inputStream = new FileInputStream("D:\\aa\\1-8.jpg");
        System.out.println("文件流创建成功!!");
        ossClient.putObject("test-cszq", "test/IMG_2215.JPG", inputStream);
        System.out.println("文件上传成功!!");

        // 设置URL过期时间为100年 3600l* 1000*24*365*100
        Date expirations = new Date(new Date().getTime() + 3600l * 1000 * 24 *365 *100);// url超时时间
        URL url = ossClient.generatePresignedUrl("test-cszq", "test/IMG_2215.JPG", expirations);
        System.out.println("图片访问地址: " + url);
        // 关闭OSSClient。
        ossClient.shutdown();
        System.out.println("连接关闭成功!!");
    }
}
