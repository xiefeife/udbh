package com.last.demo.util;

import java.io.Serializable;
import java.util.StringJoiner;

public class CompAbilityTimeLine implements Serializable {
    private String deliverTime;
    private String rate;

    public String getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(String deliverTime) {
        this.deliverTime = deliverTime;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CompAbilityTimeLine.class.getSimpleName() + "[", "]")
                .add("deliverTime='" + deliverTime + "'")
                .add("rate='" + rate + "'")
                .toString();
    }
}
