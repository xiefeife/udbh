package com.tydic.udbh.service.impl;

import com.last.demo.entity.BusinessTypeConf;
import com.last.demo.service.BusinessTypeConfService;
import com.tydic.udbh.repository.BusinessTypeConfDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: xiehh
 * @Date:2020/11/19 1:46
 * @ClassName:BusinessTypeConf
 */
@Service
@Transactional
public class BusinessTypeConfImpl implements BusinessTypeConfService {

    @Resource
    private BusinessTypeConfDto businessTypeConfDto;

    @Override
    public List<BusinessTypeConf> queryAllByBusinessType() {
        return businessTypeConfDto.queryAllByBusinessType();
    }

    @Override
    public BusinessTypeConf selectByPrimaryKey(Long businessTypeId) {

        return businessTypeConfDto.selectByPrimaryKey(businessTypeId);
    }
    /**
     * 根据商家类型名称查询
     * @param businessType
     * @return
     */
    @Override
    public BusinessTypeConf selectBusinessType(String businessType) {
        System.out.println("server+"+businessType);
        if (StringUtils.isEmpty(businessType)){
            return null;
        }
        return businessTypeConfDto.selectBusinessType(businessType);
    }
}
