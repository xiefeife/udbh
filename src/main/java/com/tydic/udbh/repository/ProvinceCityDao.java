package com.tydic.udbh.repository;

import com.last.demo.entity.ProvinceCity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProvinceCityDao {
    int deleteByPrimaryKey(Long provinceCityId);



    ProvinceCity selectByPrimaryKey(Long provinceCityId);



    List<ProvinceCity>  findAllprovince(@Param("provinceName") String provinceName);
}
