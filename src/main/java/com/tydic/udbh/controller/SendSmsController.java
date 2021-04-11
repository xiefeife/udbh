package com.tydic.udbh.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.RateLimiter;
import com.tydic.udbh.annotation.SysLog;
import com.tydic.udbh.entity.image.GetImageCodeReqBO;
import com.tydic.udbh.entity.image.GetImageCodeRspBO;
import com.tydic.udbh.entity.image.ImageCodeCheckReqBO;
import com.tydic.udbh.entity.image.ImageCodeCheckRspBO;
import com.tydic.udbh.entity.sms.GetMessageCodeRspBO;
import com.tydic.udbh.entity.sms.MessageCheckReqBO;
import com.tydic.udbh.entity.sms.MessageCheckRspBO;
import com.tydic.udbh.eumes.Constants;
import com.tydic.udbh.eumes.VerifyCodeEnum;
import com.tydic.udbh.util.ValidBatchUtils;
import com.tydic.udbh.util.VerifyUtil;
import com.tydic.udbh.util.redis.RedisUtils;
import com.wf.captcha.SpecCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/sendSms")
public class SendSmsController {


    //设置每秒不超过三个请求被处理
    private RateLimiter rateLimiter = RateLimiter.create(3);

    //java8构造方法
    final RedisUtils redisUtils;

    public SendSmsController(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }


    @RequestMapping("/requestLimit")
    public void requestLimit() {
        rateLimiter.acquire();
        log.info("请求进来了");
    }


    /**
     * 获取短信验证码
     *
     * @return
     */
//    @SysLog(description="查看产品详情")
    @GetMapping("/getMessageCode")
    public Object getMessageCode(@RequestParam("phone") String phone) {
        String key = String.format(Constants.Code.MESSAGE_CODE_KEY, phone);
        GetMessageCodeRspBO res = GetMessageCodeRspBO.builder()
                .overtime("60")
                .success(true).build();
        String s = redisUtils.get(key);
        if (StringUtils.isEmpty(s)) {
            String messageCode = VerifyUtil.getNumberCode(VerifyCodeEnum.SIX);
            redisUtils.set(key, messageCode, 60);
            res.setOvertime("60");
            res.setCode(messageCode);
        } else {
            res.setCode(s);
            res.setSuccess(false);
            res.setOvertime(redisUtils.ttl(key, TimeUnit.SECONDS).toString());
        }
        System.out.println("返回的数据 " + JSON.toJSONString(res));
        return res;
    }


    /**
     * 校验短信验证码
     *
     * @param
     * @return
     */
    @SysLog(description = "查看产品详情")
    @PostMapping("/checkMessageCode")
    public Object checkMessageCode(MessageCheckReqBO messageCheckReqBO) {

        // 参数校验
        List<String> checkRes = ValidBatchUtils.checkParams(messageCheckReqBO);
        if (checkRes.size() != 0) {
            return "参数校验失败";
        }
        if (!ValidBatchUtils.isPhoneNumber(messageCheckReqBO.getMobile())) {
            return "手机号格式错误";
        }

        String key = String.format(Constants.Code.MESSAGE_CODE_KEY, messageCheckReqBO.getMobile());
        MessageCheckRspBO messageCheckRspBO = MessageCheckRspBO.builder()
                .res(false)
                .overtime("60").build();
        String s = redisUtils.get(key);
        if (StringUtils.isEmpty(s)) {
            messageCheckRspBO.setRes(false);
            messageCheckRspBO.setOvertime("0");
        } else {
            if (messageCheckReqBO.getCode().equals(s)) {
                messageCheckRspBO.setRes(true);
                messageCheckRspBO.setOvertime(redisUtils.ttl(key, TimeUnit.SECONDS).toString());
                String resKey = String.format(Constants.Code.MESSAGE_CHECK_RESULT, messageCheckReqBO.getMobile());
                redisUtils.set(resKey, s, 60 * 3);
            } else {
                messageCheckRspBO.setOvertime(redisUtils.ttl(key, TimeUnit.SECONDS).toString());
            }

        }
        return messageCheckRspBO;
    }


    /**
     * 获取图片验证码
     * @param getImageCodeReqBO
     * @return
     */
    @PostMapping("/getImageCode")
    public Object getImageCode(GetImageCodeReqBO getImageCodeReqBO)  {

        List<String> checkRes=ValidBatchUtils.checkParams(getImageCodeReqBO);
        if(checkRes.size()!=0){
            return "图片验证码校验失败";
        }
        if(!ValidBatchUtils.isPhoneNumber(getImageCodeReqBO.getMobile())){
            return "手机号格式错误";
        }

        SpecCaptcha specCaptcha=new SpecCaptcha(150,50);
        String codeValue=specCaptcha.text();
        String base64String=specCaptcha.toBase64();
        String key=String.format(Constants.Code.IMAGE_CODE_KEY,getImageCodeReqBO.getMobile());
        redisUtils.set(key,codeValue.toLowerCase(),60*10);
//        cacheClient.set(key,codeValue.toLowerCase(),60*10);
        GetImageCodeRspBO res= GetImageCodeRspBO.builder()
                .src(base64String).build();
        return res;
    }


    /**
     * 校验图片验证码
     * @param imageCodeCheckReqBO
     * @return
     */
    @PostMapping("/checkImageCode")
    public Object checkImageCode(ImageCodeCheckReqBO imageCodeCheckReqBO) {

        List<String> checkRes=ValidBatchUtils.checkParams(imageCodeCheckReqBO);
        if(checkRes.size()!=0){
            return "图片验证码校验失败";
        }
        if(!ValidBatchUtils.isPhoneNumber(imageCodeCheckReqBO.getMobile())){
            return "手机号格式错误";
        }

        String key=String.format(Constants.Code.IMAGE_CODE_KEY,imageCodeCheckReqBO.getMobile());
        String trueCode=redisUtils.get(key);

        ImageCodeCheckRspBO res= ImageCodeCheckRspBO.builder()
                .res(false).build();
        if (imageCodeCheckReqBO.getCode().toLowerCase().equals(trueCode)){
            res.setRes(true);
            redisUtils.delete(key);
        }
        return res;
    }


}
