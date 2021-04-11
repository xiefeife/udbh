package com.tydic.udbh.report;

import com.last.demo.entity.Photo;
import com.last.demo.service.PhotoService;
import com.tydic.udbh.service.BusinessInfoService;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * @author: xiehh
 * @Date:2020/12/8 10:07
 * @ClassName:PhotoExportController
 */
@RestController
@RequestMapping("/api/photo")
public class PhotoExportController {
    @Autowired
    private BusinessInfoService businessInfoService;
    @Autowired
    private PhotoService photoService;

    /**
     * poi 写出带有图片的工具类
     * 只是07的03的就是改变下类就行。
     * @param fieldNames
     * @param values
     * @throws
     * @throws IOException
     */
    public void writePicture(List<String> fieldNames, LinkedList<LinkedHashMap<String, Object>> values,HttpServletResponse response) throws IOException {
        LinkedList<LinkedHashMap<String, Object>> linkedHashMaps = new LinkedList<>();
        LinkedHashMap<String, Object> rowValue = new LinkedHashMap<>();
        String[] headers = {"编号", "用户名", "昵称", "头像", "密码", "加密盐", "邮箱", "手机号", "状态", "机构ID", "创建人", "创建时间", "更新人", "更新时间", "是否删除"};
        LinkedList<String> fieldName = new LinkedList<>();
        List<Photo> allPhoto = photoService.findAllPhoto();
        fieldName.add(0,"名字");
        fieldName.add(1,"头像");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFDrawing xssfdrawing = sheet.createDrawingPatriarch();
        XSSFRow row = sheet.createRow(0);
        int lastCell = row.getPhysicalNumberOfCells();
        for (int j = 0; j < lastCell; j++) {
            for (Photo p: allPhoto){
            String name = fieldName.get(j);
            Cell cell = row.getCell(j);
            if (cell == null) continue;
            //进行拿到结果值
            rowValue.put(name, p.getBusinessImage());
            }
            linkedHashMaps.add(rowValue);
        }

        for (int i = 0; i < fieldNames.size(); i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(fieldNames.get(i));
        }
        for (int i = 0; i < values.size(); i++) {
            LinkedHashMap<String, Object> stringObjectLinkedHashMap = values.get(i);
            XSSFRow row1 = sheet.createRow((i + 1));
            Object[] keyArray = stringObjectLinkedHashMap.keySet().toArray();
            for (int j = 0; j < keyArray.length; j++) {
                Object cellValue = stringObjectLinkedHashMap.get(keyArray[j].toString());
                if (cellValue instanceof byte[]) {
                    //这块设置的时候一定要col2比col1大要不图片不会出来
                    //dx1 dy1 dx2 dy2 可以随意指定吧。。没弄清楚含义
//                    XSSFClientAnchor xssfClientAnchor = new XSSFClientAnchor(0, 0, 0, 0, j, i + 1, j + 1, i + 2);
                    //一开始无参构造不行 最后行了。。
                    XSSFClientAnchor xssfClientAnchor = new XSSFClientAnchor();
                    xssfClientAnchor.setCol1(j);
                    xssfClientAnchor.setCol2(j + 1);
                    xssfClientAnchor.setRow1(i + 1);
                    xssfClientAnchor.setRow2(i + 2);
                    //插入图片
                    xssfdrawing.createPicture(xssfClientAnchor, workbook.addPicture((byte[]) cellValue, XSSFWorkbook.PICTURE_TYPE_PNG));
                } else {
                    XSSFCell cell = row.createCell(j);
                    if (cellValue != null) {
                        cell.setCellValue(cellValue.toString());
                    }
                }
            }
        }
    }



    @GetMapping("/port")
    public  void  photoExport(HttpServletResponse response) throws IOException {
        FileOutputStream fileOut = null;
        BufferedImage bufferImg = null;
        //先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
        try {
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            bufferImg = ImageIO.read(new File("D:\\xunlei\\ph\\1.jpg"));
            ImageIO.write(bufferImg, "jpg", byteArrayOut);
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet1 = wb.createSheet("test picture");
            //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
            XSSFDrawing patriarch = sheet1.createDrawingPatriarch();
            //anchor主要用于设置图片的属性
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 255, 255,(short) 1, 1, (short) 5, 8);
            anchor.setAnchorType(ClientAnchor.AnchorType.byId(3));
            //插入图片
            patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
            fileOut = new FileOutputStream("D:/测试Excel.xls");

            // 写入excel文件
            wb.write(fileOut);
            System.out.println("----Excle文件已生成------");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(fileOut != null){
                try {
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
