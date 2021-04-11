package com.tydic.udbh.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.last.demo.entity.BusinessTypeConf;
import com.last.demo.service.BusinessTypeConfService;
import com.tydic.udbh.entity.BusinessImport;
import com.tydic.udbh.service.BusinessInfoService;

import java.util.*;

/**
 * @author: xiehh
 * @Date:2020/11/17 12:12
 * @ClassName:userDataListener
 */
public class BusinessListener extends AnalysisEventListener<BusinessImport> {


    private static final int BATCH_COUNT = 20;
    List<BusinessImport> list = new ArrayList<BusinessImport>();


    /**
     * 对商家类型进行赋值
     * @param sheetName
     * @param data
     */
    private void bussinessType(String sheetName, BusinessImport data) {
        List<BusinessTypeConf> businessTypeConfs = businessTypeConfService.queryAllByBusinessType();
        for (BusinessTypeConf s : businessTypeConfs) {
            if (sheetName.equals(s.getBusinessType())) {
                data.setBusinessTypeId(s.getBusinessTypeId());
            }
        }
    }

    @Override
    @SuppressWarnings(value = {"unchecked", "deprecation"})
    public void invoke(BusinessImport data, AnalysisContext analysisContext) {

        Sheet currentSheet = analysisContext.getCurrentSheet();
        //获取当前sheet名字
        String sheetName = currentSheet.getSheetName();
        bussinessType(sheetName, data);
        data.setBusinessId(UUID.randomUUID().toString());

        //获取市
        String city = data.getBusinessZzLogo();
        //获取县
        String county = data.getBusinessZzType();

        //商家地址
        String address = data.getBusinessAddress();

        StringBuffer province = new StringBuffer("河南省");

        //需要对商家地址前面进行过滤  排除XX省XX市
        StringBuffer append = province.append(data.getBusinessZzLogo());

        Map lngAndLat = new HashMap();
        if (null != address && !address.contains(append.toString())) {
            StringBuffer detailAddress = province.append(city).append(county).append(address);
            lngAndLat = com.last.demo.util.TestBaiDuApi.getLngAndLat(detailAddress.toString());
        } else {
            lngAndLat = com.last.demo.util.TestBaiDuApi.getLngAndLat(address);
        }
        Double lng = (Double) lngAndLat.get("lng");
        Double lat = (Double) lngAndLat.get("lat");
        if (null == lng && null == lat) {
            data.setBusinessLngLat(null);
        }
        if (null != lng && null != lat) {
            String str = lng.toString();
            String str1 = lat.toString();
            data.setBusinessLngLat(str + "," + str1);
        }
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }

    private BusinessInfoService dataService;
    private BusinessTypeConfService businessTypeConfService;


    public BusinessListener(BusinessInfoService dataService, BusinessTypeConfService businessTypeConfService) {
        // 这里是demo，所以随便new一个。实际使用如果到了spring,请使用下面的有参构造函数
        this.dataService = dataService;
        this.businessTypeConfService = businessTypeConfService;
    }


    /**
     * 加上存储数据库
     */
    private void saveData() {

        dataService.save(list);
    }
}
