package com.last.demo.poi;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: xiehh
 * @Date:2020/12/7 9:49
 * @ClassName:PhotoImport
 */
@RestController
@RequestMapping("/photo")
public class PhotoImport {

    @GetMapping("/import")
    public void mergerData() throws Exception {
//        File file = new File("E:\\elk\\商家数据分类数据--hb.xlsx");
        File file = new File("C:\\Users\\XIE\\Desktop\\商家模板1128.xlsx");
        //最终返回数据
        LinkedList<LinkedHashMap<String, Object>> linkedHashMaps = new LinkedList<>();
        //为了给03版本赋值
        Workbook workbook = null;
        //获取工作簿
        workbook = new XSSFWorkbook(file);

        //这里可以写成fori形式，因为我是测试的demo 所以下标就为0了
        Sheet sheetAt = workbook.getSheetAt(0);
        //一行可能有多组图片。picture方法获取指定行 列 和图片二进制数组。
        Map<Integer, Map<Integer, byte[]>> picture = picture(sheetAt);
        //获取文本行。我觉得就只是CellTypeEnum 枚举里的这些才会读到
        int physicalNumberOfRows = sheetAt.getLastRowNum();
        //记录第一行为表头
        LinkedList<String> fieldName = new LinkedList<>();
        //获取第一行信息 getPhysicalNumberOfCells 获取不为空的列个数。 getLastCellNum 是获取最后一个不为空的列是第几个。
        Row row1 = sheetAt.getRow(0);
        //getPhysicalNumberOfCells 获取不为空的列个数
        int lastCellNum = row1.getPhysicalNumberOfCells();
        if (null != row1.getCell(1)) {
            row1.getCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
            fieldName.add(0, row1.getCell(1).getStringCellValue());
        }
        //商家名称
        if (null != row1.getCell(5)) {
            row1.getCell(5).setCellType(HSSFCell.CELL_TYPE_STRING);
            fieldName.add(1, row1.getCell(5).getStringCellValue());
        }
        //商家电话
        if (null != row1.getCell(7)) {
            row1.getCell(7).setCellType(HSSFCell.CELL_TYPE_STRING);
            fieldName.add(2, row1.getCell(7).getStringCellValue());
        }
        if (null != row1.getCell(13)) {
            row1.getCell(13).setCellType(HSSFCell.CELL_TYPE_STRING);
            fieldName.add(3, row1.getCell(13).getStringCellValue());
        }
        //商家地址
        if (null != row1.getCell(14)) {
            fieldName.add(4, row1.getCell(14).getStringCellValue());
        }
        //市区
        if (null != row1.getCell(91)) {
            fieldName.add(5, row1.getCell(91).getStringCellValue());
        }
        //县区
        if (null != row1.getCell(92)) {
            fieldName.add(6, row1.getCell(92).getStringCellValue());
        }
//        for (int i = 0; i < lastCellNum; i++) {
//            fieldName.add(i, row1.getCell(i).getStringCellValue());
//        }
        //从第二行开始,获取信息
        for (int i = 1; i <= fieldName.size(); i++) {
            Row row = sheetAt.getRow(i);
            //只能获取CellTypeEnum 枚举里的这些才会读到
//            int lastCell = row.getPhysicalNumberOfCells();
            //记录一行的value值
            LinkedHashMap<String, Object> rowValue = new LinkedHashMap<>();
            for (int j = 0; j < fieldName.size(); j++) {
                //获取对应列的表头
                String name = fieldName.get(j);
                System.out.println();
                Cell cell = row.getCell(j);
                if (cell == null) continue;
                //进行拿到结果值
                Object cellValue = getObject(cell);
                rowValue.put(name, cellValue);
            }
            linkedHashMaps.add(rowValue);
        }
        //为了防止文本行没有数据 光有图片的情况
        if (!picture.isEmpty()) {
            picture.forEach((k, v) -> {
                LinkedHashMap<String, Object> stringObjectLinkedHashMap = null;
                //如果我这个行有文本对应就为true 没有就是false
                boolean fal = false;
                if ((k - 1) >= linkedHashMaps.size()) {
                    stringObjectLinkedHashMap = new LinkedHashMap<>();
                } else {
                    fal = true;
                    stringObjectLinkedHashMap = linkedHashMaps.get(k - 1);
                }
                LinkedHashMap<String, Object> finalStringObjectLinkedHashMap = stringObjectLinkedHashMap;
                if (!v.isEmpty()) {
                    v.forEach((key, val) -> {
                        String name = fieldName.get(key);
                        int cellLength = finalStringObjectLinkedHashMap.keySet().toArray().length;
                        //对应图片的位置进行赋值。
                        if (key >= cellLength) {
                            for (int i = cellLength; i < key; i++) {
                                finalStringObjectLinkedHashMap.put(fieldName.get(i), null);
                            }
                        }
                        finalStringObjectLinkedHashMap.put(name, val);
                    });
                    if (fal) {
                        linkedHashMaps.remove(k - 1);
                        if ((k - 1) > linkedHashMaps.size()) {
                            linkedHashMaps.add(finalStringObjectLinkedHashMap);
                        } else linkedHashMaps.add((k - 1), finalStringObjectLinkedHashMap);
                    } else {
                        //如果不存在文本行直接后面追加。没生成空行。也可以你们想生成就生成。
                        linkedHashMaps.add(finalStringObjectLinkedHashMap);
                    }
                }
            });
        }
        //进行写出。为了节省造数据格式问题。
//        writePicture(fieldName, linkedHashMaps);
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
     * 读取图片并包装Map -->key  是rowIndex  -->value
     * 是map map-->key -->cellIndex  value->byte[]图片二进制数组
     * @param sheet
     * @return
     */
    private Map<Integer, Map<Integer, byte[]>> picture(Sheet sheet) {
        //map 返回值
        Map<Integer, Map<Integer, byte[]>> map = new LinkedHashMap();
        //sheet.getDrawingPatriarch() 返回工作表现有的图形，如果还没有，则返回null。
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
                            System.out.println("int row1=" + row1);
                            int cell1 = ((XSSFClientAnchor) anchor).getCol1();
                            System.out.println("int cell1=" + cell1);
                            //row1 行数  cell1  列数  picture.getPictureData().getData()  图片二进制Byte
                            setByte(map, row1, cell1, picture.getPictureData().getData());
                            //将poi读取的照片信息放入map中  k 是图片行数  v
                            map.put(row1, map.get(row1));
                            System.out.println("map.get(row1)=" + map.get(row1));
                        }
                    }
                }
            }
        }
        return map;
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

    /**
     * poi 写出带有图片的工具类
     * 只是07的03的就是改变下类就行。
     *
     * @param fieldNames
     * @param values
     * @throws
     * @throws IOException
     */
    public void writePicture(List<String> fieldNames, LinkedList<LinkedHashMap<String, Object>> values) throws InvalidFormatException, IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFDrawing xssfdrawing = sheet.createDrawingPatriarch();
        XSSFRow rowOne = sheet.createRow(0);
        for (int i = 0; i < fieldNames.size(); i++) {
            XSSFCell cell = rowOne.createCell(i);
            cell.setCellValue(fieldNames.get(i));
        }
        for (int i = 0; i < values.size(); i++) {
            LinkedHashMap<String, Object> stringObjectLinkedHashMap = values.get(i);
            XSSFRow row = sheet.createRow((i + 1));
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
        FileOutputStream fileOutputStream = new FileOutputStream("io.xlsx");
        workbook.write(fileOutputStream);
        workbook.close();
        fileOutputStream.close();
    }

}
