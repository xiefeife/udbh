package com.last.demo.entity.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author: xiehh
 * @Date:2020/11/18 14:59
 * @ClassName:BusinessGetInfoRequest
 */
@Data
public class BusinessGetRequest implements Serializable {

    private String businessId;

    private String  businessLngLat;

}
