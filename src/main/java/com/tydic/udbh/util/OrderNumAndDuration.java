package com.last.demo.util;

import java.io.Serializable;

public class OrderNumAndDuration  implements Serializable {

    /**
     * 订购量
     */
    private int orderNum;

    /**
     * 平均交付时长
     */
    private String avgDeliveryDay;

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getAvgDeliveryDay() {
        return avgDeliveryDay;
    }

    public void setAvgDeliveryDay(String avgDeliveryDay) {
        this.avgDeliveryDay = avgDeliveryDay;
    }
}
