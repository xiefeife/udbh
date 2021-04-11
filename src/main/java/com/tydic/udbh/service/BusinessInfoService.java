package com.tydic.udbh.service;

import com.last.demo.entity.BusinessInfo;
import com.last.demo.entity.request.BusinessGetRequest;
import com.last.demo.entity.request.BusinessSerachRequest;
import com.last.demo.entity.request.ExportBusienss;
import com.last.demo.entity.request.ParamsRequest;
import com.last.demo.entity.respon.BusinessInfoRespon;
import com.last.demo.util.MyPageInfo;
import com.tydic.udbh.entity.BusinessImport;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: xiehh
 * @Date:2020/11/17 16:22
 * @ClassName:DataService
 */
public interface BusinessInfoService {


//    void  insertBatch(List<BusinessInfo> list) throws  Exception;




    /**
     * 导出商家信息
     * @param response
     * @param
     */
    void exportBusiness(ExportBusienss businessIds, HttpServletResponse response);

    /**
     * 导出商家信息
     * @param response
     * @param
     */
    void exportBusiness1( HttpServletResponse response);



    void save(List<BusinessImport> list);

    void save(BusinessInfo info);

    BusinessInfo saveBussinessInfo(BusinessInfo businessInfo) throws Exception ;


    BusinessInfo getBusinessInfo(BusinessGetRequest businessGetInfoRequest) throws Exception ;


    List<BusinessInfo> findAllBusinessInfo();

    /**
     * 页面左上角查询
     * @param businessSerachRequest
     * @return
     */
    MyPageInfo<BusinessInfoRespon> findByBusinessNameAndBusinessTypeId(BusinessSerachRequest businessSerachRequest);

    void  updateBusinesspr(ParamsRequest params);



   void add(BusinessInfo businessInfo);

    void saveListbusinessList(List<BusinessInfo> list);

}
