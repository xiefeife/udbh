package com.tydic.udbh.util;

import cn.hutool.core.date.DateUtil;
import com.aliyuncs.kms.model.v20160120.PutSecretValueRequest;
import com.tydic.udbh.regex.ValidatorUtil;
import org.apache.poi.ss.formula.functions.Now;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTest {


    public static void main(String[] args) {

    }


    public static boolean compareto() throws ParseException {
        String nianx = "2020-09-10";
        if (!ValidatorUtil.isDate(nianx.replace("-", "/"))) {
            System.out.println("2020-09-10");
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fomatDate2 = sdf.parse(nianx.replace("/", "-"));
        Date fomatDate1 = DateUtil.date();
        int result = fomatDate1.compareTo(fomatDate2);
        if (result < 0) {
            System.out.println("2020-09-10");
            return false;
        }
        return true;
    }





    //判断两个时间相差
    public static void getCha() {
        String date2 = "2029-07-19";
        //将字符串格式的日期格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //将日期转成Date对象作比较
            Date fomatDate1 = DateUtil.date();
            Date fomatDate2 = sdf.parse(date2);
            //比较两个日期
            int result = fomatDate2.compareTo(fomatDate1);
            //如果日期相等返回0
            if (result == 0) {
                System.out.println("两个时间相等");
            } else if (result < 0) {
                //小于0，参数date1就是在date2之后
                System.out.println("date1大于date2");
            } else {
                //大于0，参数date1就是在date2之前
                System.out.println("date1小于date2");
            }
        } catch (ParseException e) {

            e.printStackTrace();
        }
    }

    public static String getCurrentDateTime(String dateFormat) {
        DateFormat df = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    private static boolean getMonth(Date date) {
        int month = DateUtil.month(date);
        BigDecimal bigDecimal = new BigDecimal(month);
        BigDecimal add = bigDecimal.add(new BigDecimal(1));
        BigDecimal bigDecimal1 = new BigDecimal("2");
        BigDecimal yu = add.divideAndRemainder(bigDecimal1)[1];
        if (yu.compareTo(BigDecimal.ZERO) == 0) {
            return true;
        }
        return false;
    }

}






