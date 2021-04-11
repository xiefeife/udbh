package com.last.demo.entity;

import java.io.Serializable;

public class Photo implements Serializable {
    private String businessId;

    private byte[] businessImage;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public byte[] getBusinessImage() {
        return businessImage;
    }

    public void setBusinessImage(byte[] businessImage) {
        this.businessImage = businessImage;
    }
}
