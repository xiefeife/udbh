//package com.last.demo.config;
//
//import com.aliyun.oss.OSS;
//import com.aliyun.oss.common.utils.BinaryUtil;
//import com.aliyun.oss.model.MatchMode;
//import com.aliyun.oss.model.PolicyConditions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//@RestController
//public class OssController {
//
//
//
//
//    @RequestMapping("/oss")
//    public String policy() {
//
//        //https://gulimall-hello.oss-cn-beijing.aliyuncs.com/hahaha.jpg
//        String host = "https://" + "test-cszq" + "." + "oss-cn-xixian-xxunicom-d01-a.ops.develop.xixian.unicom.local/test-cszq"; // host的格式为 bucketname.endpoint
//        // callbackUrl为 上传回调服务器的URL，请将下面的IP和Port配置为您自己的真实信息。
////        String callbackUrl = "http://88.88.88.88:8888";
//        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        String dir = format + "/"; // 用户上传文件时指定的前缀。
//        Map<String, String> respMap = null;
//        try {
//            long expireTime = 30;
//            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
//            Date expiration = new Date(expireEndTime);
//            PolicyConditions policyConds = new PolicyConditions();
//            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
//            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
//
//            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
//            byte[] binaryData = postPolicy.getBytes("utf-8");
//            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
//            String postSignature = ossClient.calculatePostSignature(postPolicy);
//
//            respMap = new LinkedHashMap<String, String>();
//            respMap.put("accessid", "x2n9dVyiGlgl6I1e");
//            respMap.put("policy", encodedPolicy);
//            respMap.put("signature", postSignature);
//            respMap.put("dir", dir);
//            respMap.put("host", host);
//            respMap.put("expire", String.valueOf(expireEndTime / 1000));
//            //respMap.put("expire", formatISO8601Date(expiration));
//
//        } catch (Exception e) {
//            // Assert.fail(e.getMessage());
//            System.out.println(e.getMessage());
//        }
//        return "11111";
//    }
//}
