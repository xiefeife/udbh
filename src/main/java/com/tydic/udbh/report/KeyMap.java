package com.last.demo.report;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.sql.Array;
import java.util.*;

/**
 * @author: xiehh
 * @Date:2020/12/9 14:11
 * @ClassName:KeyMap
 */
public class KeyMap {
    public static void main(String[] args) {

        Double i = 0.0;
        String s = "12.589";
        String ss = "13.66";

        BigDecimal big1 = new BigDecimal(s);
        BigDecimal big2 = new BigDecimal(ss);
        BigDecimal add = big1.add(big2);
        String string1 = big1.add(big2).toString();


        System.out.println("===add:"+add);
        System.out.println("===string1:"+string1);



//        System.out.println(getMiao());
    }


    public static int getMiao() {
        int dayTime = 24 * 60 * 60;
        int _8hour = 8 * 60 * 60;
        int current = (int) ((System.currentTimeMillis() / 1000L + _8hour) % dayTime);
        return current;
    }

}



