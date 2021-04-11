package com.tydic.udbh.poi;

import com.last.demo.entity.BusinessInfo;
import com.last.demo.entity.Photo;
import com.last.demo.service.PhotoService;
import com.tydic.udbh.service.BusinessInfoService;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * @author: xiehh
 * @Date:2020/11/26 11:46  纯原生poi导出
 * @ClassName:PoiExport
 */
@RestController
@RequestMapping("/excel")
public class PoiExport {
    @Autowired
    private BusinessInfoService businessInfoService;
    @Autowired
    private PhotoService photoService;

    @PostMapping("/poiExcel")
    public void export(HttpServletResponse response) {
        {
            List<BusinessInfo> allBusinessInfo = businessInfoService.findAllBusinessInfo();//商家表
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
                XSSFDrawing xssfdrawing = sheet.createDrawingPatriarch();
                //新增数据行，并且设置单元格数据
                int rowNum = 1;
                String[] headers = {"编号", "用户名", "昵称", "头像", "密码", "加密盐", "邮箱", "手机号", "状态", "机构ID", "创建人", "创建时间", "更新人", "更新时间", "是否删除"};
                List<Photo> allPhoto = photoService.findAllPhoto();
                System.out.println("photo" + allPhoto.size());
                //headers表示excel表中第一行的表头
                XSSFRow row = sheet.createRow(0);
                //在excel表中添加表头
                for (int i = 0; i < headers.length; i++) {
                    XSSFCell cell = row.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(styleHead);
                }
                //在表中存放查询到的数据放入对应的列
                for (Photo p : allPhoto) {
                    System.out.println("p=" + p);
                    XSSFRow row1 = sheet.createRow(rowNum);
//                    row1.createCell(0).setCellValue();
                    row1.createCell(1).setCellValue("222");
                    XSSFClientAnchor anchor = new XSSFClientAnchor(100, 100, 100, 100, 0, 0, 0, 3);
                    System.out.println("workbook="+workbook);
                    int picture = workbook.addPicture(p.getBusinessImage(), XSSFWorkbook.PICTURE_TYPE_PNG);
                    //插入图片
                    xssfdrawing.createPicture(anchor, picture);
//                    }
                    rowNum++;
                }
                try {
//                response.setCharacterEncoding("UTF-8");
//                response.setHeader("content-Type", "application/vnd.ms-excel");
//                response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
//                    避免文件名中文乱码，将UTF8打散重组成ISO-8859-1编码方式
//                //设置响应头的类型
//                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//                //让浏览器下载文件,name是上述默认文件下载名
//                response.addHeader("Content-Disposition","attachment;filename=\"" + new String (fileName.getBytes("UTF8"),"ISO-8859-1") + "\"");
//                response.flushBuffer();
//                workbook.write(response.getOutputStream());
                    response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    response.flushBuffer();
                    workbook.write(response.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
