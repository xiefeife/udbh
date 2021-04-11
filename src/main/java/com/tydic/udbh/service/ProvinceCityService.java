package com.last.demo.service;

import com.last.demo.entity.ProvinceCity;

import java.util.List;

/**
 * @author: xiehh
 * @Date:2020/11/20 0:14
 * @ClassName:ProvinceCityService
 */
public interface ProvinceCityService {

  List<ProvinceCity> findAllprovince(String provinceName);

  /**
   * 根据省份id查询
   * @param provinceCityId
   * @return
   */
  ProvinceCity  selectByPrimaryKey(Long provinceCityId );

}
