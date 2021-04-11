package com.last.demo.entity;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * @author: xiehh
 * @Date:2020/11/18 22:48
 * @ClassName:BusinessInfoExport
 */
@Data
@ContentRowHeight(10)
@HeadRowHeight(20)
@ColumnWidth(25)
public class BusinessInfoExport extends BaseRowModel {

    @ColumnWidth(10)
    @ExcelProperty(value = {"序号"}, index = 0)
    private String businessId;
    @ExcelProperty(value = {"商家地市"}, index = 1)
    private String division;
    @ColumnWidth(20)
    @ExcelProperty(value = {"商家类型"}, index = 2)
    private String businessType;
    @ExcelProperty(value = {"商家名称"}, index = 3)
    private String businessName;
    @ExcelProperty(value = {"地址"}, index = 4)
    @ColumnWidth(30)
    private String businessAddress;
    @ExcelProperty(value = {"位置"}, index = 5)
    private String businessLngLat;

    @ExcelProperty(value = {"电话"}, index = 6)
    private String businessPhone;


    @ExcelProperty(value = {"店铺展示"}, index = 7)
    private String businessDisplay;

    @ExcelProperty(value = {"广告位1"}, index = 8)
    private String advertising1;
    @ExcelProperty(value = {"广告位2"}, index = 9)
    private String advertising2;
    @ExcelProperty(value = {"广告位3"}, index = 10)
    private String advertising3;
    @ExcelProperty(value = {"广告位4"}, index = 11)
    private String advertising4;
    @ExcelProperty(value = {"操作时间"}, index = 12)
    private String operationTime;

    @ExcelProperty(value = {"操作人"}, index = 13)
    private String operationUser;


}
