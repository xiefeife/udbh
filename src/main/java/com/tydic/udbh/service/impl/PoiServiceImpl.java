package com.tydic.udbh.service.impl;

import com.last.demo.entity.BusinessInfo;
import com.last.demo.service.PoiService;
import com.tydic.udbh.repository.BusinessInfoDto;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: xiehh
 * @Date:2020/11/28 15:22
 * @ClassName:PoiServiceImpl
 */
@Service
@Transactional
public class PoiServiceImpl implements PoiService {

    @Resource
    private BusinessInfoDto businessInfoDto;
    Map<String,String> map = new HashMap<>();

    @Override
    public Map<String,String> export(HttpServletResponse response, HttpServletRequest req) {
        List<BusinessInfo> allBusinessInfo = businessInfoDto.findAllBusinessInfo();//商家表

        if (allBusinessInfo != null && allBusinessInfo.size() > 0) {
            String fileName = "所有用户_" + DateTimeFormatter.ofPattern("yyyyMMdd").format(ZonedDateTime.now()) + ".xlsx";
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("信息表");
            //创建styleHead
            CellStyle styleHead = workbook.createCellStyle();
            styleHead.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());//背景色
            styleHead.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            styleHead.setAlignment(HorizontalAlignment.CENTER);//水平居中
            XSSFFont font = workbook.createFont();
            font.setBold(true);//加粗
            font.setFontHeight((short) 240);//字体大小
            font.setFontName("宋体");
            styleHead.setFont(font);
            sheet.setColumnWidth(0, 2000);
            sheet.setColumnWidth(1, 3500);
            sheet.setColumnWidth(2, 2000);
            sheet.setColumnWidth(3, 3500);
            sheet.setColumnWidth(4, 3500);
            sheet.setColumnWidth(5, 5500);
            sheet.setColumnWidth(6, 3500);
            sheet.setColumnWidth(7, 3500);
            sheet.setColumnWidth(8, 2000);
            sheet.setColumnWidth(9, 3500);
            sheet.setColumnWidth(10, 3500);
            sheet.setColumnWidth(11, 3500);
            sheet.setColumnWidth(12, 3500);
            sheet.setColumnWidth(13, 3500);
            sheet.setColumnWidth(14, 3500);
            //新增数据行，并且设置单元格数据
            int rowNum = 1;
            String[] headers = {"编号", "用户名", "昵称", "头像", "密码", "加密盐", "邮箱", "手机号", "状态", "机构ID", "创建人", "创建时间", "更新人", "更新时间", "是否删除"};
            //headers表示excel表中第一行的表头
            XSSFRow row = sheet.createRow(0);
            //在excel表中添加表头
            for (int i = 0; i < headers.length; i++) {
                XSSFCell cell = row.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(styleHead);
            }
            //在表中存放查询到的数据放入对应的列
            for (BusinessInfo user : allBusinessInfo) {
                XSSFRow row1 = sheet.createRow(rowNum);
                row1.createCell(0).setCellValue(user.getBusinessName());
                row1.createCell(0).setCellValue(user.getBusinessAddress());
                rowNum++;
            }
            try {
                response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                response.flushBuffer();
                String rootFilePath = "excel.xlsx";
                FileOutputStream outputStream = new FileOutputStream(rootFilePath);
                workbook.write(outputStream);
                map.put("name",rootFilePath);
                return map;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        map.put("err","下載失敗");
        return map;

    }
}
