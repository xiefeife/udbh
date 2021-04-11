package com.last.demo.entity.request;

import com.last.demo.entity.vo.AbstractPageRequest;
import lombok.Data;


/**
 * @author: xiehh 商家管理左上角查询
 * @Date:2020/11/19 10:37
 * @ClassName:BusinessSerachRequest
 */
@Data
public class BusinessSerachRequest extends AbstractPageRequest {

    private String businessName;

    private Long businessTypeId;
}
