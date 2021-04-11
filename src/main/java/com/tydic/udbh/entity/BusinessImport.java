package com.tydic.udbh.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.sun.rowset.internal.BaseRow;
import lombok.Data;

/**
 * @author: xiehh
 * @Date:2020/11/20 18:47
 * @ClassName:BusinessEntiry
 */
@Data
public class BusinessImport extends BaseRowModel {

    @ExcelIgnore
    private String  businessId;
    @ExcelProperty(index = 1)
    private String businessOriginalId;
    @ExcelProperty(index = 14)
    private String businessAddress;
    @ExcelProperty(index = 5)
    private String businessName;
    @ExcelProperty(index = 13)
    private  String businessCode;
    @ExcelProperty(index = 7)
    private  String businessPhone;
    @ExcelIgnore
    private  String businessStatus;
    @ExcelIgnore
    private  String businessEmail;
    @ExcelIgnore
    private  String businessUrl;
    @ExcelIgnore
    private  String businessIssafety;
    @ExcelProperty(index = 91)//获取市
    private  String businessZzLogo;
    @ExcelProperty(index = 92)//获取县
    private  String businessZzType;
    @ExcelIgnore
    private  String businessUnitCode;
    @ExcelIgnore
    private  String businessHeadUrl;
    @ExcelIgnore
    private  String businessBgUrl;
    @ExcelIgnore
    private  String businessLngLat;
    @ExcelIgnore
    private Long businessTypeId;
    @ExcelIgnore
    private Long provinceCityId;


}
