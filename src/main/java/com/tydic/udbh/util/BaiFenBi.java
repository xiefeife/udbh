package com.last.demo.util;


import java.math.BigDecimal;

public class BaiFenBi {

    public static void main(String[] args) {

        String str1 = "00";
        String str2 = "01";
        BigDecimal bigDecimal1 = new BigDecimal(str1);

        BigDecimal bigDecimal2 = new BigDecimal(str2);


        int i = bigDecimal1.compareTo(bigDecimal2);
        System.out.println(i);


    }
}
