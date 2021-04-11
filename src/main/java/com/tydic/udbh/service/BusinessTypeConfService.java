package com.last.demo.service;


import com.last.demo.entity.BusinessTypeConf;

import java.util.List;

/**
 * @author: xiehh
 * @Date:2020/11/19 1:46
 * @ClassName:BusinessTypeConfService
 */
public interface BusinessTypeConfService {

    List<BusinessTypeConf> queryAllByBusinessType();
    BusinessTypeConf selectByPrimaryKey(Long businessTypeId);
    /**
     * 根据商家类型名称查询
     * @param businessType
     * @return
     */
    BusinessTypeConf selectBusinessType(String  businessType );

}
