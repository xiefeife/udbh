package com.tydic.udbh.controller;

import com.last.demo.entity.BusinessInfo;
import com.last.demo.entity.Photo;
import com.last.demo.service.BusinessTypeConfService;
import com.last.demo.service.PhotoService;
import com.last.demo.util.Result;
import com.last.demo.util.oss.AliyunOssConfig;
import com.last.demo.util.oss.AliyunOssResult;
import com.tydic.udbh.service.BusinessInfoService;
import com.tydic.udbh.util.oss.AliyunOSSUtil;
import com.tydic.udbh.util.UuidUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: xiehh   使用byte[]数组对poi读取的照片进行接收 上传到oss或存储到mysql
 * @Date:2020/12/7 12:17
 * @ClassName:ExcelController1
 * TODO  使用byte[]数组对poi读取的照片进行接收 上传到oss或存储到mysql
 *
 */
@RestController
@RequestMapping("/port")
public class ExcelController1 {

    @Resource
    private BusinessInfoService businessInfoService;
    @Resource
    private BusinessTypeConfService businessTypeConfService;
    @Resource
    private PhotoService photoService;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/test1")
    public Result test(MultipartFile file) throws IOException {
        //请求返回map
        Map<String, String> resultMap = new HashMap<>();
        //得到工作簿
        Workbook wb = new XSSFWorkbook(file.getInputStream());
        //循环遍历所有的sheet页
        for (int s = 0; s < wb.getNumberOfSheets(); s++) {
            XSSFSheet sheet = (XSSFSheet) wb.getSheetAt(s);
            //存放头像名称的map  k是当前头像的行数  v 是头像的名字
            LinkedHashMap<Integer, String> headMap = new LinkedHashMap<>();
            //存放背景名称的map  k 是当前头像的行数  v 是多个头像拼接后的字符串
            LinkedHashMap<Integer, String> bgMap = new LinkedHashMap<>();
            //获取当前sheet页的有效行数
            Integer lastRowNum = UuidUtil.getLastRowNum(wb);
            /*
            读取的是指定sheet页的照片
            读取图片并包装成Map -->key  是rowIndex  -->value 是map
              map-->key -->cellIndex  value->byte[]图片二进制数组
            */
            Map<Integer, Map<Integer, byte[]>> picture = picture(sheet);
            LinkedList<byte[]> bgList = new LinkedList<>();
            List<String> headList = new ArrayList<>();
            List<String> backgList = new ArrayList<>();

            for (Map.Entry<Integer, Map<Integer, byte[]>> entry : picture.entrySet()) {
                Map<Integer, byte[]> valueMap = entry.getValue();
                for (Map.Entry<Integer, byte[]> ent : valueMap.entrySet()) {
                    if (8 == ent.getKey()) {
                        if (AliyunOssConfig.isOssAttach) {
                            String hdString = UuidUtil.get8UUID() + ".jpg";
                            //存储到oss 图片存储到oss返回文件的名字  将文件名字存储到商家对象里
                            AliyunOssResult result = AliyunOSSUtil.putFile(new ByteArrayInputStream(ent.getValue()), hdString);
                            if (200 == result.getCode()) {
                                System.out.println("头像上传成功返回文件名="+result.getFileName());
                                headMap.put(entry.getKey(), hdString);
                                headList.add(result.getFileName());
                                resultMap.put("message", "数据导入成功");
                            } else {
                                log.error("数据导入失败", result.getErrMsg());
                                resultMap.put("message", "数据导入异常:" + result.getErrMsg());
                            }
                        } else {
                            try {
                                Photo photoHead = new Photo();
                                String hdString = UuidUtil.get8UUID() + ".jpg";
                                photoHead.setBusinessId(hdString);
                                photoHead.setBusinessImage(ent.getValue());
                                photoService.savePhoto(photoHead);
                                //k 这个头像所在的行数  v 头像存储数据库时的名字
                                headMap.put(entry.getKey(), hdString);
                                resultMap.put("message", "数据导入成功");
                            } catch (Exception e) {
                                log.error("数据导入失败", e.getMessage());
                                resultMap.put("message", "数据导入异常:" + e.getMessage());
                            }
                        }
                    } else {
                        //对8列以后的头像进行添加
                        bgList.add(ent.getValue());
                    }
                }
                resultMap.put("head",headList.toString());
                StringBuffer buffer = new StringBuffer();
                System.out.println("bgList="+bgList.size());
                //背景头像byte的集合
                if (null != bgList && 0< bgList.size() ){
                for (int j = 0; j < bgList.size(); j++) {
                    //是否存储到oss
                    if (AliyunOssConfig.isOssAttach) {
                        System.out.println("isOssAttach==" + AliyunOssConfig.isOssAttach);
                        String bgString = UuidUtil.get8UUID() + ".jpg";
                        AliyunOssResult aliyunOssResult = AliyunOSSUtil.putFile(new ByteArrayInputStream(bgList.get(j)), bgString);
                        buffer.append(",").append(aliyunOssResult.getFileName());
                        if (200 == aliyunOssResult.getCode()){
                            System.out.println("背景上传成功返回文件名="+aliyunOssResult.getFileName());
                            backgList.add(aliyunOssResult.getFileName());
                            resultMap.put("message", "数据导入成功");
                        }
                    } else {
                        try {
                            Photo photo1 = new Photo();
                            String bgString = UuidUtil.get8UUID() + ".jpg";
                            buffer.append(",").append(bgString);
                            photo1.setBusinessId(bgString);
                            photo1.setBusinessImage(bgList.get(j));
                            photoService.savePhoto(photo1);
                            resultMap.put("message", "数据导入成功");
                        }catch (Exception e){
                            log.error("数据导入失败", e.getMessage());
                            resultMap.put("message", "数据导入异常:" + e.getMessage());
                        }
                    }
                }
                resultMap.put("backgList",backgList.toString());
                String s1 = buffer.toString().substring(1, buffer.length());
                bgMap.put(entry.getKey(), s1);
                System.out.println("s1=" + s1);
                bgList.stream().forEach(p -> {
                    System.out.println("s=" + p + " ");
                });
                bgList.clear();
                System.out.println("--------------------------------");
                }
            }
            System.out.println("lastRowNum=" + lastRowNum);
            for (int i = 1; i < lastRowNum; i++) {//遍历所有行
                BusinessInfo b = new BusinessInfo();
                String head = null;
                //存放头像的map
                for (Map.Entry<Integer, String> map : headMap.entrySet()) {
                    if (i == map.getKey()) {
                        head = map.getValue();
                    }
                }
                String bg = null;
                //存放背景的map
                for (Map.Entry<Integer, String> entry : bgMap.entrySet()) {
                    if (i == entry.getKey()) {
                        bg = entry.getValue();
                    }
                }
                Row row = sheet.getRow(i);//得到每一行
                System.out.println("row1.getPhysicalNumberOfCells()"+row.getPhysicalNumberOfCells());
                b.setBusinessHeadUrl(head);
                b.setBusinessBgUrl(bg);
                b.setBusinessId(UuidUtil.get8UUID());
                if (null != row.getCell(1)) {
                    row.getCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
                    b.setBusinessOriginalId(row.getCell(1).toString());
                }
                //商家名称
                if (null != row.getCell(5)) {
                    row.getCell(5).setCellType(HSSFCell.CELL_TYPE_STRING);
                    b.setBusinessName(row.getCell(5).getStringCellValue());
                }
                //商家电话
                if (null != row.getCell(7)) {
                    row.getCell(7).setCellType(HSSFCell.CELL_TYPE_STRING);
                    b.setBusinessPhone(row.getCell(7).toString());
                }
                //商家地址
                if (null != row.getCell(14)) {
                    b.setBusinessAddress(row.getCell(14).getStringCellValue());
                }
                //市区
                if (null != row.getCell(91)) {
                    b.setBusinessZzLogo(row.getCell(91).toString());
                }
                //县区
                if (null != row.getCell(92)) {
                    b.setBusinessZzType(row.getCell(92).toString());
                }
                businessInfoService.save(b);

            }
        }
        return Result.succuess(resultMap);
    }


    /**
     * 读取图片并包装Map -->key  是rowIndex  -->value 是map
     * map-->key -->cellIndex  value->byte[]图片二进制数组
     *
     * @param sheet
     * @return
     */
    private Map<Integer, Map<Integer, byte[]>> picture(Sheet sheet) {
        //map 返回值
        Map<Integer, Map<Integer, byte[]>> map = new LinkedHashMap();
        //sheet.getDrawingPatriarch() 返回工作表现有的图形，如果还没有，则返回null。
        System.out.println("sheet.getDrawingPatriarch()=" + sheet.getDrawingPatriarch());
        if (sheet.getDrawingPatriarch() != null) {
            //甄别07
            if (sheet instanceof XSSFSheet) {
                XSSFSheet sheetTemp = (XSSFSheet) sheet;
                //此形状组中的形状列表
                for (XSSFShape shape : sheetTemp.getDrawingPatriarch().getShapes()) {
//                    如果形状组是图片 还有自定义图形的情况。
                    if (shape instanceof XSSFPicture) {
                        XSSFPicture picture = (XSSFPicture) shape;
//                      获取图片的锚点
                        XSSFAnchor anchor = picture.getAnchor();
                        if (anchor instanceof XSSFClientAnchor) {
                            int row1 = ((XSSFClientAnchor) anchor).getRow1();
                            int cell1 = ((XSSFClientAnchor) anchor).getCol1();
                            setByte(map, row1, cell1, picture.getPictureData().getData());
                            map.put(row1, map.get(row1));
                        }
                    }
                }
            }
        }
        return map;
    }

    /**
     * 判断cell的枚举 并对应处理成一个Object对象
     *
     * @param cell
     * @return
     */
    private Object getObject(Cell cell) {
        Object cellValue = null;
        switch (cell.getCellTypeEnum()) {
            case _NONE:
                cellValue = null;
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    if (cell.getNumericCellValue() < 1) {
                    } else {
                        //判断字段类型是时间还是日期
                        SimpleDateFormat sdf = null;
                        String dateValue = DateUtil.getJavaDate(cell.getNumericCellValue()).toString();
                        //判断时间类型还是日期类型。
                        boolean matches = dateValue.matches(".*00:00:00.*");
                        if (matches) {
                            sdf = new SimpleDateFormat("yyyy-MM-dd");
                        } else {
                            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        }
                        cellValue = sdf.format(DateUtil.getJavaDate(cell.getNumericCellValue()));
                    }
                } else {
                    //数字转化
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    cellValue = new BigDecimal(cellValue.toString());
                    BigDecimal bigDecimal = ((BigDecimal) cellValue).stripTrailingZeros();
                    cellValue = bigDecimal.toPlainString();
                }
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case FORMULA:
                break;
            case BLANK:
                break;
            case BOOLEAN:
                break;
            case ERROR:
                break;
        }
        return cellValue;
    }


    /**
     * 不管是03 还是07的通用方法，进行判断是否存在那一行的那一列，进行添加数据
     *
     * @param map
     * @param row1
     * @param cell1
     * @param data
     */
    private void setByte(Map<Integer, Map<Integer, byte[]>> map, int row1, int cell1, byte[] data) {
        if (!map.containsKey(row1)) {
            Map<Integer, byte[]> map1 = new LinkedHashMap();
            map1.put(cell1, data);
            map.put(row1, map1);
        } else {
            Map<Integer, byte[]> integerMap = map.get(row1);
            if (!integerMap.containsKey(cell1)) {
                integerMap.put(cell1, data);
            }
        }
    }






}
