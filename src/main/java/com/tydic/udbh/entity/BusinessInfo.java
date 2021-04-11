package com.last.demo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.string.StringImageConverter;
import lombok.*;

/**
 * @author: xiehh  excel导入实体类
 * @Date:2020/11/17 18:32
 * @ClassName:Bussiness
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessInfo {
    /**
     * 商家id
     */
    private String businessId;

    /**
     * 商家原id
     */
    private String businessOriginalId;

    /**
     * 商户地址
     */
    private String businessAddress;

    /**
     * 商户名称
     */
    private String businessName;

    /**
     * 商户邮编
     */
    private String businessCode;

    /**
     * 商户状态
     */
    private String businessStatus;

    /**
     * 商户邮箱
     */
    private String businessEmail;

    /**
     * 商户网址
     */
    private String businessUrl;

    /**
     * 电话是否加密
     */
    private String businessIssafety;

    /**
     * 增值业务标识
     */
    private String businessZzLogo;

    /**
     * 增值类型
     */
    private String businessZzType;

    /**
     * 单位名称编码
     */
    private String businessUnitCode;

    /**
     * 商家头像url
     */
    @ExcelProperty(converter = StringImageConverter.class)
    private String businessHeadUrl;

    /**
     * 商家背景url
     */
    @ExcelProperty(converter = StringImageConverter.class)
    private String businessBgUrl;

    /**
     * 商家经纬度
     */
    private String businessLngLat;

    /**
     * 商家类型id
     */
    private Long businessTypeId;

    /**
     * 省份地市id
     */
    private Long provinceCityId;

    /**
     * 商家电话
     */
    private String businessPhone;

    /**
     * 操作人
     */
    private String operationUser;

    /**
     * 操作日期
     */
    private String operationTime;

    private static final long serialVersionUID = 1L;


}
