package com.tydic.udbh.async;

import com.ailk.ecs.open.esbclient.OpenEsbClient;
import com.ailk.ecs.open.esbclient.bean.EcAopResult;
import com.ailk.ecs.open.esbclient.bean.SysParamBean;
import com.ailk.ecs.open.esbclient.sign.SignAlgorithmType;
import com.alibaba.fastjson.JSONObject;

import java.util.UUID;


public class OpenPlatformController {

    public static void main(String[] args) {
        String url = "http://ecstest0517.10010.com/oppf";
        //创建应用时选择的签名加密方式
        SignAlgorithmType type = SignAlgorithmType.HmacSHA256;
        //创建应用后应用基本信息中的密钥

        //若签名加密方式为SHA，请使用"应用密钥",此为默认签名加密方式
        //若使用加密方式为RSA，请使用"私钥"
        String signSecurty = "d89a800186440b8b778153ae697ae780";

        OpenEsbClient client = new OpenEsbClient(url, type, signSecurty);
        String appid = "210126089";
        SysParamBean sysParamBean = new SysParamBean();
        //应用ID，创建应用后可在开发者视图查阅
        sysParamBean.setAppId(appid);
        //选择接入的能力编码，可位于开发者视图能力查询处查阅
        sysParamBean.setMethod("uac.oauth2.broadInfo");
        //查问题与追踪日志等的业务流水号，需要保持每笔唯一，uuid方式供参考
        sysParamBean.setBusiSerial(appid + UUID.randomUUID().toString().replace("-",""));
        //默认选择格式为json
        sysParamBean.setFormat("json");
        //接入版本，如未作额外通知默认填写1
        sysParamBean.setVersion("1");

        JSONObject busiParam = new JSONObject();

        //content业务参数，不同能力业务参数不同
        busiParam.put("user_id", "13269913465");
        busiParam.put("display","native");
        busiParam.put("redirect_uri","uop:oauth2.0:token");


        EcAopResult result = client.call(sysParamBean, busiParam);

        System.out.println(result.getResponse());

    }
}
