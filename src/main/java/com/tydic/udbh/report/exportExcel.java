package com.last.demo.report;

import com.last.demo.entity.Photo;
import com.last.demo.service.PhotoService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author: xiehh
 * @Date:2020/12/8 17:49
 * @ClassName:exportExcel
 */
@RestController
@RequestMapping("/report")
public class exportExcel {
    @Autowired
    private PhotoService photoService;

    /**
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel(HttpServletResponse response) {
        // 默认查询用户所属单位及子集单位数据
        // 查询人员列表
        List<Photo> people = photoService.findAllPhoto();
        //设置标题
        String[] title = {"姓名", "身份证号", "注册照", "证件照", "性别", "民族", "出生日期", "政治面貌", "婚姻状态", "手机号", "人员类别", "是否入住", "常驻地址", "地址编码", "工号", "单位", "职位", "系统名称"};
        //设置文件名
        String filename = "staff.xls";
        //创建一个工作薄
        XSSFWorkbook wb = new XSSFWorkbook();
        //创建一个sheet
        XSSFSheet sheet = wb.createSheet("人员表");
        //设置列宽
        sheet.setDefaultColumnWidth(20);
        //创建标题行
        XSSFRow row = sheet.createRow(0);
        //设置行高，类型居中
        row.setHeightInPoints(80);
        XSSFCellStyle style = wb.createCellStyle();
        //创建标题
        XSSFCell cell = null;
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }
        //插入数据
        for (int i = 0; i < people.size(); i++) {
            //创建行
            row = sheet.createRow(i + 1);
            //设置行高
            row.setHeightInPoints(160);
            //将内容按顺序插入单元格
//            row.createCell(0).setCellValue(people.get(i).getBusinessId());
            try {
                //插入注册照
                BufferedImage bufferImg = null;
                ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                System.out.println("people.get(i).getBusinessImage()="+people.get(i).getBusinessImage());

                InputStream inputStream = new ByteArrayInputStream(people.get(i).getBusinessImage());
                //读取
                bufferImg = ImageIO.read(inputStream);
                ImageIO.write(bufferImg, "jpg", byteArrayOut);
                XSSFDrawing patriarch = sheet.createDrawingPatriarch();
                //设置大小位置
                XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 1002, 250, (short) 2, i + 1, (short) 2, i + 1);
                //插入图片
                patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));
            } catch (IOException io) {
                io.printStackTrace();

            }
            //继续插入其他数据
//            row.createCell(4).setCellValue(people.get(i).getGender());
//            row.createCell(5).setCellValue(people.get(i).getNation());
//            row.createCell(6).setCellValue(people.get(i).getBirthDate());
//            row.createCell(7).setCellValue(people.get(i).getPolitical());
//            row.createCell(8).setCellValue(people.get(i).getMaritalStatus());
//            row.createCell(9).setCellValue(people.get(i).getPhoneNo());
//            row.createCell(10).setCellValue(people.get(i).getPeopleCategoryDesc());
//            if(people.get(i).getPeopleCategories().get(0).getLiveFlag() != null){
//                row.createCell(11).setCellValue(String.valueOf(people.get(i).getPeopleCategories().get(0).getLiveFlag()));
//            }
//            row.createCell(12).setCellValue(people.get(i).getAddressDesc());
//            row.createCell(13).setCellValue(String.valueOf(people.get(i).getAddressId()));
//            row.createCell(14).setCellValue(people.get(i).getWorkNo());
//            row.createCell(15).setCellValue(people.get(i).getOrgName());
//            row.createCell(16).setCellValue(people.get(i).getPeopleCategories().get(0).getWorkTitle());
//            row.createCell(17).setCellValue("智能安防管理系统PROD");
        }
        try {
            // 响应到客户端
            this.setResponseHeader(response, filename);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向客户端发送响应流方法
     *
     * @param response
     * @param fileName
     */
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


}
