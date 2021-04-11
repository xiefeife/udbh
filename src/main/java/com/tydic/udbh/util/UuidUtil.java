package com.tydic.udbh.util;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import java.util.UUID;

/**
 * @author: xiehh
 * @Date:2020/11/22 11:25
 * @ClassName:PoiUtil
 */
public class UuidUtil {


    public static String get8UUID(){
        UUID id=UUID.randomUUID();
        String[] idd=id.toString().split("-");
        return idd[0];
    }

    /**
     * 获取sheet的有效行
     *
     * @param wb
     * @return
     */
    public static Integer getLastRowNum(Workbook wb) {
        //获取第一个画布
        Sheet sheet = wb.getSheetAt(0);
        CellReference cellReference = new CellReference("A4");
        boolean flag = false;
        for (int i = cellReference.getRow(); i <= sheet.getLastRowNum(); ) {
            Row r = sheet.getRow(i);
            if (r == null) {
                //如果是空行（即没有任何数据、格式），直接把它以下的数据往上移动
                sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
                continue;
            }
            flag = false;
            for (Cell c : r) {
                if (c.getCellType() != Cell.CELL_TYPE_BLANK) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                i++;
                continue;
            } else {//如果是空白行（即可能没有数据，但是有一定格式）
                if (i == sheet.getLastRowNum()) {
                    //如果到了最后一行，直接将那一行remove掉
                    sheet.removeRow(r);
                } else {
                    //如果还没到最后一行，则数据往上移一行
                    sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
                }
            }
        }
        System.out.println("有效行数为:" + (sheet.getLastRowNum() + 1));
        return sheet.getLastRowNum() + 1;
    }


}

