package com.last.demo.util.tree;

import cn.hutool.core.date.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

public class MonthTest {

    public static void main(String[] args) {
        Date date = DateUtil.date();
//        Date date = DateUtil.parse("2020-12-02");
//        System.out.println("date "+date);
        //获得月份，从0开始计数
        DateUtil.month(date);
        int month = DateUtil.month(date);
        BigDecimal bigDecimal = new BigDecimal(month);
        BigDecimal add = bigDecimal.add(new BigDecimal(1));
        BigDecimal bigDecimal1 = new BigDecimal("2");
        System.out.println("month "+add);
        System.out.println( "取余 "+add.divideAndRemainder(bigDecimal1)[1]);
        BigDecimal yu = add.divideAndRemainder(bigDecimal1)[1];
       if (yu.compareTo(BigDecimal.ZERO) == 0){
            System.out.println("是偶数月");
       }

    }
}
