package com.tydic.udbh.repository;

import com.last.demo.entity.BusinessInfo;
import com.last.demo.entity.request.BusinessGetRequest;
import com.last.demo.entity.request.BusinessSerachRequest;
import com.last.demo.entity.request.ParamsRequest;
import com.tydic.udbh.entity.BusinessImport;

import java.util.List;

/**
 * @author: xiehh
 * @Date:2020/11/17 15:41
 * @ClassName:UserImportMapper
 */
public interface BusinessInfoDto {

    List<BusinessInfo> queryExportList(List<String> list);


    void saveListbusinessList(List<BusinessInfo> list);


    void  updateBusinesspr(ParamsRequest params);

    void insertStudentVoList(List<BusinessImport> list);

    BusinessInfo insert(BusinessInfo record);

    BusinessInfo getBusinessInfo(BusinessGetRequest businessGetInfoRequest);

    List<BusinessInfo> findAllBusinessInfo();

    BusinessInfo updateByBusinessId(BusinessInfo businessInfo);
    /**
     * 删除商家  逻辑删除
     * @param businessId
     * @return
     */
    int deleteBusinessStatus(String businessId);

    BusinessInfo insertSelective(BusinessInfo businessInfo);
    /**
     * 根据商家类型查询
     * @param typeId
     * @return
     */
    List<BusinessInfo>  findAllByBusinessTypeId(String typeId);


   List<BusinessInfo>  findByBusinessNameAndBusinessTypeId(BusinessSerachRequest businessSerachRequest);

   void   savaBusiness(List<BusinessInfo> list);


   void add(BusinessInfo businessInfo);
   void save(BusinessInfo businessInfo);

}
