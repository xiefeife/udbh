package com.last.demo.report;

import com.last.demo.util.AstoreApp;
import com.last.demo.util.OrderNumAndDuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author: xiehh
 * @Date:2020/12/22 13:04
 * @ClassName:DateUtils
 */
public class DateUtils {
    public static void main(String[] args) {


//        int q = 9;
//
//        int m = 4;
//        System.out.println(q / m);


        BigDecimal bigDecimal = new BigDecimal(15.31);
        int t = 3;
        BigDecimal bigDecima2 = new BigDecimal(t);
        String s = String.valueOf(bigDecimal.intValue() / t);
        System.out.println("相除得到的数/-"+s);
        System.out.println("原来的代码-" + bigDecimal.divide(bigDecima2).toString());


        List<AstoreApp> list = new ArrayList<>();
        AstoreApp astoreApp = new AstoreApp();
        astoreApp.setName("z");
        astoreApp.setOrderNum(1);
        astoreApp.setAvgDeliveryDay("12.5");
        AstoreApp astoreApp2 = new AstoreApp();
        astoreApp2.setName("z");
        astoreApp2.setOrderNum(5);
        astoreApp2.setAvgDeliveryDay("7.5");
        AstoreApp astoreApp1 = new AstoreApp();
        astoreApp1.setName("z");
        astoreApp1.setOrderNum(5);
        astoreApp1.setAvgDeliveryDay("15.44");
        list.add(astoreApp1);
        list.add(astoreApp);
        list.add(astoreApp2);
        int n = 0;
        for (int i = 0; i < list.size(); i++) {
            if ("z".equals(list.get(i).getName())) {
                n += list.get(i).getOrderNum();
            }
        }
//        System.out.println(n);

        String eq = "中国联通";
        String qe = "中国";
        System.out.println(qe.contains(eq));


    }


    private Map<String, Object> moreFlowApp(String name, Map<String, OrderNumAndDuration> map) {
        Double avgDay = 0.0;
        int orderNum = 0;


        return null;
    }


    public static long getTomorrowZeroSeconds() {
        long current = System.currentTimeMillis();// 当前时间毫秒数
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long tomorrowzero = calendar.getTimeInMillis();
        long tomorrowzeroSeconds = (tomorrowzero - current) / 1000;
        System.out.println(tomorrowzeroSeconds);
        return tomorrowzeroSeconds;
    }


}
