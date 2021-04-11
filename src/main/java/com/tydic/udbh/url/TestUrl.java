package com.tydic.udbh.url;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tools.ant.taskdefs.SendEmail;
import sun.management.Sensor;

import java.util.HashMap;

@Slf4j
public class TestUrl {

    private static String sendStaticUrl = "https://wap.10010.com/mobileService/clickCountLogRecord/pageClickCount.htm?flag=new&&title=\\u5BBD\\u5E26\\u6570\\u5B57\\u5316&openUrl=https://m.client.10010.com/mobileService/openPlatform/openPlatLineNew.htm?to_url=https://bbdigital.10010.com/udbh/engineer/view/0?orderId=";


    private static String sendUrl = "http://10.238.27.12:8090/api/rest/sendSMS";

    private static String creatOrderSmsUrl = "https://wap.10010.com/mobileService/clickCountLogRecord/pageClickCount.htm?flag=new&&title=\\u5BBD\\u5E26\\u6570\\u5B57\\u5316&openUrl=https://m.client.10010.com/mobileService/openPlatform/openPlatLineNew.htm?to_url=https://bbdigital.10010.com/udbh/engineer/view/1?account=";

    public static void main(String[] args) {

        sendSms(creatOrderSmsUrl);
    }


    public static void sendSms(String url) {

        try {
            HashMap<String, String> data = new HashMap<>();
            data.put("userPhone", "13263136367");
            data.put("userBroadcast", "01195876");
            String string = Base64.encode(JSONUtil.parse(data).toString());
            HashMap<String, Object> paramInfoMap = new HashMap<>();
            paramInfoMap.put("username", "谢辉辉");
            paramInfoMap.put("phoneNumber", "13263136367");
            paramInfoMap.put("url", url + string);
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("paramInfo", paramInfoMap);
            paramMap.put("sender", "10010");
            paramMap.put("templateId", "11");
            paramMap.put("recipient", "13263136367");
            HttpUtil.post(sendUrl, JSONUtil.parse(paramMap).toString());
            System.out.println(JSONUtil.parse(paramMap).toString());
        } catch (Exception e) {
            log.error("预约单生成成功,给用户 {} 发送短信失败 ");
        }
    }
}
