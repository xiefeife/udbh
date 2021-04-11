package com.tydic.udbh.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.last.demo.entity.request.BusinessGetRequest;
import com.last.demo.entity.request.BusinessSerachRequest;
import com.last.demo.entity.request.ExportBusienss;
import com.last.demo.entity.request.ParamsRequest;
import com.last.demo.entity.respon.BusinessInfoRespon;
import com.last.demo.service.BusinessTypeConfService;
import com.last.demo.util.MyPageInfo;
import com.tydic.udbh.entity.BusinessImport;
import com.tydic.udbh.repository.BusinessInfoDto;
import com.tydic.udbh.service.BusinessInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author: xiehh
 * @Date:2020/11/17 16:23
 * @ClassName:DataServiceImpl
 */
@Service
@Transactional
public class BusinessInfoServiceImpl implements BusinessInfoService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

//
//    @Autowired
//    private SqlSessionTemplate sqlSessionTemplate;


    @Resource
    private ProvinceCityServiceImpl provinceCityService;
    @Resource
    private BusinessTypeConfService businessTypeConfService;
    @Resource
    private BusinessInfoDto businessInfoDto;


    @Override
    public com.last.demo.entity.BusinessInfo saveBussinessInfo(com.last.demo.entity.BusinessInfo businessInfo) throws Exception {
        try {
            businessInfo.setBusinessId(UUID.randomUUID().toString());
            com.last.demo.entity.BusinessInfo info = businessInfoDto.insert(businessInfo);
            return info;
        } catch (Exception e) {
            logger.error("商家信息添加失败,失败原因" + e);
            return null;
        }

    }

    /**
     * 查询商户详细信息
     *
     * @param businessGetInfoRequest
     * @return
     * @throws Exception
     */
    @Override
    public com.last.demo.entity.BusinessInfo getBusinessInfo(BusinessGetRequest businessGetInfoRequest) throws Exception {
        com.last.demo.entity.BusinessInfo businessInfo = businessInfoDto.getBusinessInfo(businessGetInfoRequest);
        return businessInfo;
    }

    @Override
    public List<com.last.demo.entity.BusinessInfo> findAllBusinessInfo() {
        List<com.last.demo.entity.BusinessInfo> allBusinessInfo = businessInfoDto.findAllBusinessInfo();
        return allBusinessInfo;
    }

    /**
     * 根据关键字和商户类型查询
     *
     * @param businessSerachRequest
     * @return
     */
    @Override
    public MyPageInfo<BusinessInfoRespon> findByBusinessNameAndBusinessTypeId(BusinessSerachRequest businessSerachRequest) {
        List<com.last.demo.entity.BusinessInfo> mapList = businessInfoDto.findByBusinessNameAndBusinessTypeId(businessSerachRequest);
        List<BusinessInfoRespon> responList = mapList.stream().map(s -> {
            BusinessInfoRespon respon = new BusinessInfoRespon();
            if (null != s.getProvinceCityId()) {
                com.last.demo.entity.ProvinceCity provinceCity = provinceCityService.selectByPrimaryKey(s.getProvinceCityId());
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(provinceCity.getProvinceName()).append(provinceCity.getCityName());
                respon.setProvince(stringBuilder.toString());
            }
            com.last.demo.entity.BusinessTypeConf businessTypeConf = businessTypeConfService.selectByPrimaryKey(s.getBusinessTypeId());
            respon.setBusinessType(businessTypeConf.getBusinessType());
            BeanUtils.copyProperties(s, respon);
            return respon;
        }).collect(Collectors.toList());

        return new MyPageInfo<>(mapList).replace(responList);
    }

    @Override
    public void updateBusinesspr(ParamsRequest params) {
        businessInfoDto.updateBusinesspr(params);
    }


    @Override
    public void add(com.last.demo.entity.BusinessInfo businessInfo) {

        businessInfoDto.add(businessInfo);
    }

    @Override
    public void saveListbusinessList(List<com.last.demo.entity.BusinessInfo> list) {

        businessInfoDto.saveListbusinessList(list);
    }

    /**
     * 导入   底层使用batch
     * @param list
     */
//    @Override
//    public void insertBatch(List<BusinessInfo> list) throws  Exception {
//// 新获取一个模式为BATCH，自动提交为false的session
//// 如果自动提交设置为true,将无法控制提交的条数，改为最后统一提交，可能导致内存溢出
//        System.out.println("batch批量添加");
//        SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
//        try {
//            if(null != list || list.size()>0){
//                int lsize=list.size();
//                for (int i = 0, n=list.size(); i < n; i++) {
//                    BusinessInfo businessInfo =  new BusinessInfo();
//                    businessInfo.setBusinessId(list.get(i).getBusinessId());
//                    businessInfo.setBusinessName(list.get(i).getBusinessName());
//session.insert("com.last.demo.repository.BusinessInfoDto.save",businessInfo);
////session.update("com.xx.mapper.UserMapper.updateByPrimaryKeySelective",_entity);
////                    session.insert(“", user);
//                    if ((i>0 && i % 1000 == 0) || i == lsize - 1) {
//// 手动每1000个一提交，提交后无法回滚
//                        session.commit();
//// 清理缓存，防止溢出
//                        session.clearCache();
//                    }
//                }
//            }
//        } catch (Exception e) {
//// 没有提交的数据可以回滚
//            session.rollback();
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//    }

    /**
     * 导出商家信息
     *
     * @param response
     */
    @Override
    public void exportBusiness(ExportBusienss businessIds, HttpServletResponse response) {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            LocalDateTime nowDateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fileName = "商家信息" + dateTimeFormatter.format(nowDateTime);
            String sheetName = "城市专区";
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20") + ".xlsx");
            // 这里需要设置不关闭流
            Set<String> excludeColumnFiledNames = new HashSet<String>();

            List<String> businessIdlist = businessIds.getBusinessId();
            List<com.last.demo.entity.BusinessInfo> result = businessInfoDto.queryExportList(businessIdlist);
            System.out.println(JSON.toJSONString(result));
            System.out.println(result.size() + "----");
            List<com.last.demo.entity.BusinessInfoExport> exportList = new ArrayList<>();

            for (int i = 0; i < result.size(); i++) {
                com.last.demo.entity.BusinessInfo s= result.get(i);
                com.last.demo.entity.BusinessInfoExport b = new com.last.demo.entity.BusinessInfoExport();
                BeanUtils.copyProperties(s, b);
//                if (null != s.getOperationUser()){
//                    AuthorityUserInfoBO user = authorizationService.getUserInfoByUserId(s.getOperationUser());
//                    b.setOperationUser(user.getName());
//                }
                exportList.add(b);
            }
            EasyExcel.write(response.getOutputStream(), com.last.demo.entity.BusinessInfoExport.class).excludeColumnFiledNames(excludeColumnFiledNames).
                    autoCloseStream(Boolean.FALSE).sheet(sheetName)
                    .doWrite(exportList);
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e);
            try {
                logger.error("s", e);
                response.getWriter().println(JSON.toJSONString(map));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

    @Override
    public void exportBusiness1( HttpServletResponse response) {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            LocalDateTime nowDateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fileName = "商家信息" + dateTimeFormatter.format(nowDateTime);
            String sheetName = "城市专区";
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20") + ".xlsx");
            // 这里需要设置不关闭流
            Set<String> excludeColumnFiledNames = new HashSet<String>();


            List<com.last.demo.entity.BusinessInfo> result = businessInfoDto.findAllBusinessInfo();
            List<com.last.demo.entity.BusinessInfoExport> exportList = new ArrayList<>();

            for (int i = 0; i < result.size(); i++) {
                com.last.demo.entity.BusinessInfo s= result.get(i);
                com.last.demo.entity.BusinessInfoExport b = new com.last.demo.entity.BusinessInfoExport();
                BeanUtils.copyProperties(s, b);
                exportList.add(b);
            }
            EasyExcel.write(response.getOutputStream(), com.last.demo.entity.BusinessInfoExport.class).excludeColumnFiledNames(excludeColumnFiledNames).
                    autoCloseStream(Boolean.FALSE).sheet(sheetName)
                    .doWrite(exportList);
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e);
            try {
                logger.error("s", e);
                response.getWriter().println(JSON.toJSONString(map));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

    @Override
    public void save(List<BusinessImport> list) {

        businessInfoDto.insertStudentVoList(list);
    }

    @Override
    public void save(com.last.demo.entity.BusinessInfo info) {
        businessInfoDto.save(info);

    }


}
