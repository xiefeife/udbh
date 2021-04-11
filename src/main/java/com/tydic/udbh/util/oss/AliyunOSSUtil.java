package com.tydic.udbh.util.oss;

import com.aliyun.oss.*;
import com.aliyun.oss.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.StandardSocketOptions;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * @author: xiehh
 * @Date:2020/11/20 16:37
 * @ClassName:AliyunOSSUtil
 */
public class AliyunOSSUtil {

    /**
     * oss 工具客户端
     */
    private static OSSClient ossClient = null;

    /**
     * 上传文件至阿里云 OSS
     * 文件上传成功,返回文件完整访问路径
     *
     * @param file 上传文件
     * @return
     */
    public static com.last.demo.util.oss.AliyunOssResult upload(MultipartFile file) {
        //原来文件名称
        String oldName = file.getOriginalFilename();
        //文件后缀
        String postfix = oldName.substring(oldName.lastIndexOf(".") + 1, oldName.length());
        //新文件名称
        String fileName = UUID.randomUUID().toString().toUpperCase()
                .replace("-", "")
                + "." + postfix;
        //获取文件类型
        String fileType = file.getContentType();

        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return new com.last.demo.util.oss.AliyunOssResult(400, null, e.getMessage());
        }
        //上传文件
        com.last.demo.util.oss.AliyunOssResult resoult = putFile(inputStream,  fileName);
        return resoult;
    }



    /**
     * 上传文件
     * @param input      文件输入流
     * @param fileName  文件的名字 生成新文件的名字
     * @return
     */
    public static com.last.demo.util.oss.AliyunOssResult putFile(InputStream input , String fileName) {
        com.last.demo.util.oss.AliyunOssResult resoult = null;
        //初始化客户端
        ossClient = com.last.demo.util.oss.AliyunOssConfig.getOssClient();
        try {
            // 创建上传Object的Metadata
            ObjectMetadata meta = new ObjectMetadata();
            // 设置上传内容类型
            // 创建上传文件的元信息，可以通过文件元信息设置HTTP header(设置了才能通过返回的链接直接访问)。
            meta.setContentType("image/jpg");

            //设置文件的ACL权限 去掉生成url链接后面的ak和其他字段
            meta.setHeader("x-oss-object-acl", "public-read");

            //被下载时网页的缓存行为
            meta.setCacheControl("no-cache");
            //创建上传请求
            PutObjectRequest request = new PutObjectRequest(com.last.demo.util.oss.AliyunOssConfig.JAVA_BUCKET_NAME, fileName, input, meta);
            //上传文件
             ossClient.putObject(request);

            resoult = new com.last.demo.util.oss.AliyunOssResult(200, getOssUrl(ossClient,fileName), null,fileName);

        } catch (OSSException | ClientException e) {
            e.printStackTrace();
            resoult = new com.last.demo.util.oss.AliyunOssResult(400, null, e.getMessage());
            return resoult;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return resoult;
    }

    /**
     * 根据文件名生成文件的访问地址
     *
     * @param ossClient
     * @param key   上传到oss后生成的新文件名 putFile方法设置文件类型为jpg后链接可以通 过http访问
     * @return
     */
    private static String getOssUrl(OSSClient ossClient, String key) {
        Date expiration = new Date(new Date().getTime() + com.last.demo.util.oss.AliyunOssConfig.FILE_EXPIRATION_TIME);// 生成URL
        GeneratePresignedUrlRequest generatePresignedUrlRequest;
        generatePresignedUrlRequest = new GeneratePresignedUrlRequest(com.last.demo.util.oss.AliyunOssConfig.JAVA_BUCKET_NAME, key);
        generatePresignedUrlRequest.setExpiration(expiration);
        URL url = ossClient.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    public static String downloadFileFromOss(String fileName){
        ossClient = com.last.demo.util.oss.AliyunOssConfig.getOssClient();
        ossClient.getObject(new GetObjectRequest(com.last.demo.util.oss.AliyunOssConfig.JAVA_BUCKET_NAME, fileName), new File("<yourLocalFile>"));

        return null;
    }

    /**
     * 通过图片完整url路径，获取图片url最后一段信息
     *
     * @param url 图片完整的url信息
     * @return
     */
    public static String getImgUrlPath(String url) {
        if (Objects.isNull(url) || "".equals(url.trim())) {
            return null;
        }
        String shortUrl = null;
        try {
            URI uri = new URI(url);
            shortUrl = uri.getPath() + "?" + uri.getQuery();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("url解析出错！");
        }
        return shortUrl;
    }
}
