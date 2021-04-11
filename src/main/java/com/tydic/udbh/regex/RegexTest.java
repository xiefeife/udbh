package com.tydic.udbh.regex;


import cn.hutool.core.date.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegexTest {

    public static void main(String[] args) throws ParseException {

        String str = "2020/09/01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date fomatDate2 = sdf.parse(str.replace("/", "-"));
        System.out.println(str);
        Date fomatDate1 = DateUtil.date();
        int result = fomatDate1.compareTo(fomatDate2);
        if (result < 0) {
            System.out.println(result);
        }


    }
}
