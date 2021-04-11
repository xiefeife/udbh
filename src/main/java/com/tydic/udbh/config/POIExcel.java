package com.last.demo.config;


import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: xiehh
 * @Date:2020/11/22 20:56
 * @ClassName:Photo1
 */
public class POIExcel {



    public static void getDataFromExcel(String filePath) throws IOException {
        FileInputStream fis = null;
        Workbook wookbook = null;
        Sheet sheet = null;
        try {
            //获取一个绝对地址的流
            fis = new FileInputStream(filePath);
            /* 读取网络文件（比如七牛等云存储）
            URL url = new URL(filePath);
            BufferedInputStream fis = new BufferedInputStream(url.openStream());*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        fis = new FileInputStream(filePath);
        wookbook = new XSSFWorkbook(fis);//得到工作簿
        Map<String, PictureData> maplist = null;
        sheet = wookbook.getSheetAt(0);

        maplist = getPictures2((XSSFSheet) sheet);
        try {
            printImg(maplist);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放map
            if (maplist != null) {
                maplist = null;
            }
        }
        //得到一个工作表
        //获得表头
        Row rowHead = sheet.getRow(0);
        //获得数据的总行数
        int totalRowNum = sheet.getLastRowNum();
        for (int i = 1; i < totalRowNum; i++) {
            //获得第i行对象
            Row row = sheet.getRow(i);
        }
        //使用完成关闭
        wookbook.close();
        if (fis != null) {
            fis.close();
        }
    }

    /**
     * 获取图片和位置 (xlsx)
     *
     * @param sheet
     * @return
     * @throws IOException
     */
    public static Map<String, PictureData> getPictures2(XSSFSheet sheet) throws IOException {
        Map<String, PictureData> map = new HashMap<String, PictureData>();
        int pIndex = 0;//使用这个索引可以读取一个单元格里多个图片  保证map的key不重复
        List<POIXMLDocumentPart> list = sheet.getRelations();
        for (POIXMLDocumentPart part : list) {
            if (part instanceof XSSFDrawing) {
                XSSFDrawing drawing = (XSSFDrawing) part;
                List<XSSFShape> shapes = drawing.getShapes();
                for (XSSFShape shape : shapes) {
                    XSSFPicture picture = (XSSFPicture) shape;
                    XSSFClientAnchor anchor = picture.getPreferredSize();
                    CTMarker marker = anchor.getFrom();
                    String picIndex = marker.getRow() + "-" + marker.getCol() ;
                    map.put(picIndex, picture.getPictureData());
                    pIndex++;
                }
            }
        }
        for (Map.Entry<String, PictureData> entry : map.entrySet()) {
            //获取图片所在的单元格位置
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        return map;
    }

    //图片写出
    public static List<String>  printImg(Map<String, PictureData> sheetList) throws Exception {
        List<String> stringList = new ArrayList<>();
        //将map数组的键转换成 数组
        Object key[] = sheetList.keySet().toArray();
        String filePath = "";
        for (int i = 0; i < sheetList.size(); i++) {
            // 获取图片流
            PictureData pic = sheetList.get(key[i]);
            // 获取图片索引
            String picName = key[i].toString();
            // 获取图片格式
//            String ext = pic.suggestFileExtension();
            String ext = "jpg";
            byte[] data = pic.getData();
            //文件上传七牛
//            QiNiuUtils.uploadOneObject(data,"111_"+picName + "." + ext);
            //图片保存路径
            filePath = getJarPath() + File.separator + picName + "." + ext;
            stringList.add(picName);
            FileOutputStream out = new FileOutputStream(filePath);
            out.write(data);
            out.close();
        }
        return stringList;
    }



    public static String getJarPath() {
        return POIExcel.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    }

}
