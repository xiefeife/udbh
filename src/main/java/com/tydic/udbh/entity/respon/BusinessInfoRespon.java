package com.last.demo.entity.respon;

import com.last.demo.entity.BusinessInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiehh   商家管理查询相应类
 * @Date:2020/11/18 17:13
 * @ClassName:BusinessInfoRespon
 */
@Data
public class BusinessInfoRespon  {
    /**
     *商家id
     */
    private String businessId;
    /**
     *商家原id
     */
    private String businessOriginalId;
    /**
     *商户地址
     */
    private String businessAddress;
    /**
     *商户名称
     */
    private String businessName;
    /**
     *商户邮编
     */
    private String businessCode;
    /**
     *商户状态
     */
    private String businessStatus;
    /**
     *商户邮箱
     */
    private String businessEmail;
    /**
     *商户网址
     */
    private String businessUrl;
    /**
     *电话是否加密
     */
    private String businessIssafety;
    /**
     *增值业务标识
     */
    private String businessZzLogo;
    /**
     *增值类型
     */
    private String businessZzType;
    /**
     *单位名称编码
     */
    private String businessUnitCode;
    /**
     *商家头像
     */
    private String businessHeadUrl;
    /**
     *商家背景
     */
    private String businessBgUrl;
    /**
     *商家经纬度
     */
    private String businessLngLat;
    /**
     *商家类型
     */
    private Long businessTypeId;

    /**
     * 商家电话
     */
    private  String businessPhone;

    /**
     *省份地市id
     */
    private Long provinceCityId;


    /**
     * 商户所属省份
     */
    private  String   province;


    /**
     * 商户的类型
     */
    private String  businessType;


}
