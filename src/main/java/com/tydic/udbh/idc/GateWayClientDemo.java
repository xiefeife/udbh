package com.last.demo.independent;

import com.ailk.ecs.ssp.eop.api.ApiException;
import com.ailk.ecs.ssp.eop.api.EopClient;
import com.ailk.ecs.ssp.eop.api.EopReq;
import com.ailk.ecs.ssp.eop.api.EopRsp;

import java.util.HashMap;
import java.util.Map;


public class GateWayClientDemo {
    // 服务端URL地址
    protected static String url = "http://10.142.194.62:6000/ssp-gateway-release";//6000测试环境-对省份测试环境联调用

    // 渠道接入唯一标识码
    protected static String appcode = "111000001";//渠道编码,111000001:网厅,详见基础数据,新增渠道,密钥,需要自助提供 1

    // Md5密钥
    protected static String signKey = "bhgjDQRazK4bNAof1F0jnyITv0TmGdvENrHC9+eIWG0k5KKpg1Ag9DMXMdOyuB5d9NWX/dLNxKZ5FBX/UCOBNQ==";// HMAC

    // Md5加密类型
    protected static String signType = "hmac";

    // RSA加密密钥
    protected static String rsaKey = "MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEAjSww8T8YtScn8zgjufcpPOFP5A2v8pnbKfVTGv225P2C1AIgtjYVbU+2RWr5IjfSZxm7lTkx6nDirj1Pg59uEwIDAQABAkEAi/1yWwhSm/DEMO9Oni51+iUDcAYSn+Pp7OWVD4LgRpmUEBt+2Pldo0bJzIsDF+86TUymXycFV1GDA8ZGUBo2YQIhAMe+AnEKxjDX+29Q0H1O0Gzujaj/4l3ebj6NkH6Kk+h/AiEAtO8k9H5LCxcdrP+1cec3fK2y5gYm7QMcvaoNmhEikG0CIQDB/0YXzMMZhWxrfS5Bxl6grkFgNscBLJwenRgOD0IAuQIga7Ig3ArEXkCPIGdAOCE5bNPzNWmaB9+fXuF2oSrr2O0CIQCnIZTaJ/4EUTv5DXdRyeuWZkvLgtsZOl8dc9foQhvPuw==";

    // AES加密密钥
    protected static String aesKey = "IRQ50Zz8HqByTo+waEICjw==";

    // 请求服务编码
    protected static String busiCode = "cu.query.mydetailinfo";//对外业务编码,表示某个服务,cu.query.calldetail:详单查询,详见自助服务平台接入规范

    public static void main(String[] args) throws ApiException {
        EopClient client = new EopClient(url, appcode, signKey);
        client.setSignAlgorithm(signType);
        client.setParamKey(aesKey);
        client.setPriKey(rsaKey);

        //密钥版本对象封装(根据自助平台提供的版本号及类型设置)
        Map<String, String> accessMap = new HashMap<String, String>();
        accessMap.put("AES", "0.0.1");
        accessMap.put("HMAC", "0.0.1");
        accessMap.put("RSA", "0.0.1");
        client.setAccessMap(accessMap);

        EopReq eopReq = new EopReq(busiCode);

        // 封装业务参数列表(接口报文体参数,具体接口不同,传值请参见接口规范)
        Map<String, String> bizMap = new HashMap<String, String>();
        bizMap.put("ssp_version", "ssp.201202VS00000000000000000000000001");//网关版本号
        bizMap.put("begindate", "20120202");
        bizMap.put("enddate", "20120220");
        bizMap.put("usernumber", "13263136367");//详单查询服务会收到短信,请用自己的测试号码

        eopReq.putSecuret4KeyValue("biz_params", bizMap);//业务参数,具体参见自助服务平台接入规范

        // 封装公共参数列表
        Map<String, String> pubMap = new HashMap<String, String>();
        pubMap.put("transid", "1110000011111200008820210305100012058326728");//唯一标识流水,由调用系统生成,格式:渠道编码+业务编码+yyyyMMddHHmmssSSS+6位自定
        pubMap.put("customid", "11111111111");//用户id,如果无法获取,默认11个1
        pubMap.put("channelcode", "111000001");//渠道编码,如111000001:网厅,详见基础数据,新接入渠道需要联系自助分配 1
        pubMap.put("businesscode", "11132000001");//业务编码,如11112000088:4G后付费通话详单查询,详见基础数据 1
        pubMap.put("provincecode", "011");//省份编码
        pubMap.put("citycode", "110");//地市编码
        pubMap.put("nettype", "11");//网别,如11:4G 具体参见接口规范 如果没有登录认证无法获取网别,默认99
        pubMap.put("paytype", "2");//付费类型,如2具体参见接口规范 如果没有登录认证无法获取网别,默认0

        eopReq.put("pub_params", pubMap);//公共参数
        try {
            EopRsp eopRsp = client.execute(eopReq);
            //rspcode返回为0000时表示成功
            if("0000".equals(eopRsp.getRspcode())){
                Map result = eopRsp.getResult();
                //result为应答报文封装对象,对应业务规范
                System.out.println(result);
            }else{
                //其他编码是错误编码,此时不取result
                System.out.println("错误编码:" + eopRsp.getRspcode() + ",错误描述" + eopRsp.getRspdesc());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
