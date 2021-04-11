package com.tydic.udbh.time;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Time {

    public static void main(String[] args) {
        BigDecimal success = new BigDecimal(5.00);
        BigDecimal orderNum = new BigDecimal(3.0);

        BigDecimal successRate = success.divide(orderNum, 4, RoundingMode.HALF_UP);
//        BigDecimal multiply = successRate.multiply(new BigDecimal("100"));
        //完成率  完成量/订单量*100%
        float a = Float.parseFloat(successRate.toString()) * 100;
        System.out.println(successRate);
//       System.out.println(multiply);
        System.out.println(a);


    }
}
