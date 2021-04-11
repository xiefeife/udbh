package com.tydic.udbh.repository;

import com.last.demo.entity.BusinessTypeConf;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BusinessTypeConfDto {

    BusinessTypeConf selectBusinessType(@Param("businessType")String  businessType );

    int deleteByPrimaryKey(Long businessTypeId);

    int insert(BusinessTypeConf record);

    int insertSelective(BusinessTypeConf record);


    int updateByPrimaryKeySelective(BusinessTypeConf record);

    int updateByPrimaryKey(BusinessTypeConf record);

    List<BusinessTypeConf> queryAllByBusinessType();

    /**
     * 根据主键id查询
     * @param businessTypeId
     * @return
     */
    BusinessTypeConf selectByPrimaryKey(Long businessTypeId);
}
