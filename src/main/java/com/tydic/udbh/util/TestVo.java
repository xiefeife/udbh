package com.last.demo.util;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.util.Date;

public class TestVo {

    public static void main(String[] args) {

        boolean month = getMonth(DateUtil.date());
        System.out.println("当前是偶数月  "+month);

    }

    private static boolean getMonth(Date date) {
        int month = DateUtil.month(date);
        BigDecimal bigDecimal = new BigDecimal(month);
        BigDecimal add = bigDecimal.add(new BigDecimal(1));
        BigDecimal bigDecimal1 = new BigDecimal("2");
        System.out.println("month " + add);
        System.out.println("取余 " + add.divideAndRemainder(bigDecimal1)[1]);
        BigDecimal yu = add.divideAndRemainder(bigDecimal1)[1];
        if (yu.compareTo(BigDecimal.ZERO) == 0) {
            return true;
        }
        return false;
    }

}
