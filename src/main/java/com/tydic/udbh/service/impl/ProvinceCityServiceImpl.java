package com.tydic.udbh.service.impl;

import com.last.demo.entity.ProvinceCity;
import com.last.demo.service.ProvinceCityService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: xiehh
 * @Date:2020/11/20 0:15
 * @ClassName:ProvinceCityServiceImpl
 */
@Service
public class ProvinceCityServiceImpl implements ProvinceCityService {

//    @Autowired
//    private ProvinceCityDao provinceCityDao;

    @Override
    public List<ProvinceCity> findAllprovince(String provinceName) {
//        List<ProvinceCity> all = provinceCityDao.findAllprovince(provinceName);
        return null;
    }

    /**
     * 根据省份id查询
     *
     * @param provinceCityId
     * @return
     */
    @Override
    public ProvinceCity selectByPrimaryKey(Long provinceCityId) {
//        ProvinceCity provinceCity = provinceCityDao.selectByPrimaryKey(provinceCityId);

        return null;
    }
}
