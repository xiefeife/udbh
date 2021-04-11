package com.tydic.udbh.stream;

import com.ailk.ecs.ssp.eop.api.ApiException;
import com.ailk.ecs.ssp.eop.api.EopClient;
import com.ailk.ecs.ssp.eop.api.EopReq;
import com.ailk.ecs.ssp.eop.api.EopRsp;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class TestList {

    public static void main(String[] args) throws ApiException {

        //冒泡排序
        List list = Arrays.asList(4, 7, 1, 3, 6, 0);
        int[] zu = {4, 7, 1, 3, 6, 0};
        int temp = 0;
        //执行集合的次数减一
        for (int i = 0; i < zu.length - 1; i++) {
            for (int j = 0; j < zu.length - i - 1; j++) {
                if (zu[j] > zu[j + 1]) {
                    temp = zu[j];
                    zu[j] = zu[j + 1];
                    zu[j + 1] = temp;
                }
            }
            System.out.println("运行了"+i+"次");
        }
        for (int i = 0; i < zu.length; i++) {
            System.out.print(zu[i] +"\t\t");
        }

        //Collections 集合排序


//        字符串截取
//        String str="1,1,1,1,,";
//        String str1=",,1,,1,1,,";
//        System.out.println(Arrays.toString(str.split(","))+"----"+Arrays.toString(str.split(",",-1)));
//        System.out.println(Arrays.toString(str1.split(","))+"----"+Arrays.toString(str1.split(",",-1)));


//        EopClient client = new EopClient("", "", "");
//        EopReq eopReq = new EopReq("");
//        EopRsp eopRsp = client.execute(eopReq);
//
//        String str = "[response={\"rspcode\":\"0000\",\"rspts\":\"20210316180211\",\"rspmsg\":\"{\\\"vpnname\\\":\\\"互联网接入业务\\\",\\\"busiorder\\\":\\\"BUSI002103161802110008001380\\\",\\\"productid\\\":\\\"90612987\\\",\\\"subscrbstat\\\":\\\"10\\\",\\\"custsex\\\":\\\"1\\\",\\\"managercontact\\\":\\\"\\\",\\\"acctid\\\":\\\"\\\",\\\"opendate\\\":\\\"20210123163142\\\",\\\"useintegral\\\":\\\"0\\\",\\\"sendpost\\\":\\\"\\\",\\\"usernumber\\\":\\\"01018594004\\\",\\\"citycode\\\":\\\"110\\\",\\\"sendcode\\\":\\\"\\\",\\\"certtype\\\":\\\"\\\",\\\"custlvl\\\":\\\"\\\",\\\"certype\\\":\\\"02\\\",\\\"sendflag\\\":\\\"\\\",\\\"certaddr\\\":\\\"黑龙江省齐齐哈尔市龙沙区五龙街道新化路德被小区1号楼4单元301室\\\",\\\"sendaddr\\\":\\\"\\\",\\\"productname\\\":\\\"北京智慧沃家套内宽带500M-全家福199元月资费档（合约期优惠资费参见优惠活动说明）\\\",\\\"accid\\\":\\\"1119062300910619\\\",\\\"feetype\\\":\\\"现金\\\",\\\"sendadd\\\":\\\"\\\",\\\"managername\\\":\\\"\\\",\\\"brand\\\":\\\"A\\\",\\\"accname\\\":\\\"张晓晨\\\",\\\"email\\\":\\\"\\\",\\\"simcard\\\":\\\"\\\",\\\"amount\\\":\\\"\\\",\\\"roamstat\\\":\\\"1\\\",\\\"subscrbtype\\\":\\\"\\\",\\\"sendtype\\\":\\\"2\\\",\\\"sendrecord\\\":\\\"05\\\",\\\"creditvale\\\":\\\"99999999\\\",\\\"landlvl\\\":\\\"3\\\",\\\"laststatdate\\\":\\\"20210123163143\\\",\\\"subscrbid\\\":\\\"1121012355286573\\\",\\\"sendname\\\":\\\"\\\",\\\"broadbandcode\\\":\\\"\\\",\\\"custid\\\":\\\"1119062366600841\\\",\\\"viplev\\\":\\\"暂无\\\",\\\"sendcontent\\\":\\\"\\\",\\\"billingtype\\\":\\\"00\\\",\\\"sendemail\\\":\\\"\\\",\\\"certnum\\\":\\\"23020519941030061X\\\",\\\"paytype\\\":\\\"\\\",\\\"custname\\\":\\\"张晓辰\\\"}\",\"transid\":\"1520000011113200000120210316180211590018426\",\"rspdesc\":\"OK\",\"trxid\":\"GWAY2021031618021161100303075\",\"rspsign\":\"I6KT02VKem7pXriX/TYkX2J4DuRpgeIWROcSN7sVsrY5mcMF8OJCngOLCX0Ct3whktR9MH9epW74Rqp2Dwxnfg==\"}]";

    }

}
