//package com.tydic.udbh.controller;
//
//import com.alibaba.excel.EasyExcel;
//import com.alibaba.excel.EasyExcelFactory;
//import com.alibaba.excel.ExcelReader;
//import com.alibaba.excel.ExcelWriter;
//import com.alibaba.excel.event.AnalysisEventListener;
//import com.alibaba.excel.metadata.BaseRowModel;
//import com.alibaba.excel.metadata.Sheet;
//import com.alibaba.excel.support.ExcelTypeEnum;
//
//import com.last.demo.entity.request.BusinessGetRequest;
//import com.last.demo.entity.request.BusinessSerachRequest;
//import com.last.demo.entity.request.ParamsRequest;
//import com.last.demo.entity.respon.BusinessInfoRespon;
//import com.last.demo.service.BusinessTypeConfService;
//import com.last.demo.service.ProvinceCityService;
//import com.last.demo.util.BusinessListener;
//import com.last.demo.util.MyPageInfo;
//import com.last.demo.util.Result;
//import com.last.demo.util.oss.AliyunOssConfig;
//import com.tydic.udbh.entity.BusinessImport;
//import com.tydic.udbh.service.BusinessInfoService;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.Resource;
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.UnsupportedEncodingException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.net.URLEncoder;
//import java.util.*;
//
///**
// * @author: xiehh
// * @Date:2020/11/17 11:28
// * @ClassName:UserController
// */
//@RestController
//@RequestMapping("/api/navigation")
//public class BusinessInfoController {
//
//
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Resource
//    private BusinessInfoService businessInfoService;
//    @Resource
//    private BusinessTypeConfService businessTypeConfService;
//    @Resource
//    private ProvinceCityService provinceCityService;
//    Map<String, Object> resultMap = new HashMap<String, Object>();
//
//
//    /**
//     导入excel  读取多个sheet  使用的是阿里easy excel 不支持读取excel带图片
//     * @param file
//     * @return
//     * @throws IOException
//     */
//    @PostMapping("/upload")
//    public String exportAll(MultipartFile file) throws IOException {
//        try {
//            EasyExcel.read(file.getInputStream(), com.last.demo.entity.BusinessInfo.class, new BusinessListener(businessInfoService, businessTypeConfService)).doReadAll();
//            return "excel导入成功";
//        } catch (IOException i) {
//            logger.error("excel导入失败,失败原因", i);
//            return "excel导入失败";
//        }
//    }
//
//    /**
//     * 根据oss文件路径删除文件
//     * @param filePath
//     */
//    @GetMapping("/image")
//    public static void deleteFile(  String filePath) {
//        if (filePath.startsWith("/")) {
//            filePath = filePath.substring(1);
//        }
//        AliyunOssConfig.getOssClient().deleteObject("test-cszq", filePath);
//    }
//
//
//
//    /**
//     * 根据商户关键字和商户类型查询
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("/getBusinessListPage")
//    public Result<MyPageInfo>findAllBusinessPage(
//            @RequestBody(required = false) BusinessSerachRequest request) throws Exception {
//        if (null == request) {
//            request.setBusinessName("");
//        }
//        MyPageInfo<BusinessInfoRespon> data = businessInfoService.findByBusinessNameAndBusinessTypeId(request);
//
//        return Result.succuess (data);
//    }
//
//    /**
//     * 根据商户关键字和商户类型查询
//     * @param businessSerachRequest
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("/getBusinessList")
//    public Object findAllBusiness(@RequestBody(required = false) BusinessSerachRequest businessSerachRequest) throws Exception {
//        if (null == businessSerachRequest) {
//            businessSerachRequest.setBusinessName("");
//        }
//        return Result.succuess("");
//    }
//
//
//    /**
//     * 根据省份表将省份信息赋值给商家
//     * @param provinceName
//     * @return
//     */
//    @GetMapping("/province")
//    public Object getCity(String provinceName) {
//        List<com.last.demo.entity.BusinessInfo> list = new ArrayList<>();
//        List<com.last.demo.entity.BusinessInfo> business = businessInfoService.findAllBusinessInfo();//商家表
//        List<com.last.demo.entity.ProvinceCity> province = provinceCityService.findAllprovince(provinceName);//省份表
//        for (int j = 0; j < business.size(); j++) {//商家的
//            com.last.demo.entity.BusinessInfo b = business.get(j);
//            for (int i = 0; i < province.size(); i++) {//市区的
//                com.last.demo.entity.ProvinceCity p = province.get(i);
//                if (null != b.getBusinessZzType() && null != p.getCountyName() && b.getBusinessZzType().equals(p.getCityName())) {
//                    ParamsRequest params = new ParamsRequest();
//                    b.setProvinceCityId(p.getProvinceCityId());
//                    params.setBusinessId(b.getBusinessId());
//                    params.setProvinceCityId(b.getProvinceCityId());
//                    businessInfoService.updateBusinesspr(params);
//                    list.add(b);
//                }
//                //     }
//            }
//        }
//        System.out.println(list.size());
//        return list;
//    }
//
//
//
//    /**
//     * 添加商家信息
//     * @param businessInfo
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("/saveBusinessInfo")
//    public Object saveBusinessInfo(@RequestBody com.last.demo.entity.BusinessInfo businessInfo) throws Exception {
//        if (null == businessInfo) {
//            resultMap.put("RSP_CODE", "8888");
//            resultMap.put("RSP_DESC", "请求参数不能为空");
//            return Result.fail(resultMap);
//        }
//        com.last.demo.entity.BusinessInfo businessInfo1 = new com.last.demo.entity.BusinessInfo();
//        businessInfo1.setBusinessId(UUID.randomUUID().toString());
//        BeanUtils.copyProperties(businessInfo, businessInfo);
//        com.last.demo.entity.BusinessInfo businessInfo2 = businessInfoService.saveBussinessInfo(businessInfo);
//        return businessInfo2;
//    }
//
//
//    /**
//     * 根据主键id查询商家详细信息
//     *
//     * @param businessGetInfoRequest
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("/getBusinessInfo")
//    public Object getbusinessInfo(@RequestBody BusinessGetRequest businessGetInfoRequest) throws Exception {
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//        if (null == businessGetInfoRequest) {
//            resultMap.put("RSP_CODE", "8888");
//            resultMap.put("RSP_DESC", "请求参数不能为空");
//            return Result.fail(resultMap);
//        }
//        com.last.demo.entity.BusinessInfo businessInfo = businessInfoService.getBusinessInfo(businessGetInfoRequest);
//        System.out.println("*****" + Result.succuess(resultMap));
//        return Result.succuess(businessInfo);
//    }
//
//    /**
//     * 导出指定路径 Excel
//     */
//    @GetMapping("/export2")
//    public void export2() {
//        List<com.last.demo.entity.BusinessInfo> data = businessInfoService.findAllBusinessInfo();
//        String fileName = "E:\\aaaa\\111.xlsx";
//        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
//        // 如果这里想使用03 则 传入excelType参数即可
//        System.out.println("data" + data.size());
//        EasyExcel.write(fileName, com.last.demo.entity.BusinessInfoExport.class).sheet("模板").doWrite(data);
//    }
//
//    /**
//     * 导出单个sheet
//     * @param response
//     * @param
//     * @param
//     * @param fileName
//     * @throws UnsupportedEncodingException
//     */
//    @GetMapping("/exportEasy")
//    public  void writeExcelOneSheet(HttpServletResponse response, String fileName) {
//        try {
//            String value = "attachment; filename=" + new String(
//                    (fileName + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ExcelTypeEnum.XLSX.getValue()).getBytes("gb2312"), "ISO8859-1");
//            response.setContentType("multipart/form-data");
//            response.setCharacterEncoding("utf-8");
//            response.setContentType("application/vnd.ms-excel;charset=utf-8");
//            response.setHeader("Content-disposition", value);
//
//            ServletOutputStream out = response.getOutputStream();
//            ExcelWriter writer = EasyExcelFactory.getWriter(out, ExcelTypeEnum.XLSX, true);
//            //写第一个sheet
//            @SuppressWarnings("unchecked")
//            Sheet sheet = new Sheet(1, 0);
//            // 设置属性类
//            sheet.setClazz(com.last.demo.entity.BusinessInfoExport.class);
//            List<com.last.demo.entity.BusinessInfo> allApps = businessInfoService.findAllBusinessInfo();
//            writer.write(allApps, sheet);
//            writer.finish();
//            out.flush();
//        } catch (IOException e) {
//            logger.error("导出失败，失败原因：{}", e);
//        }
//    }
//
//    /**
//     * Excel导出
//     */
//    @GetMapping("/export")
//    public void exportAzureAd(HttpServletResponse response, HttpServletRequest req) throws IOException {
//        try {
//            String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
//            ExcelServlet(response, req, "azure-ad-" + now + ".xlsx");
//            ServletOutputStream out = response.getOutputStream();
//            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
//            //写第一个sheet
//            @SuppressWarnings("unchecked")
//            Sheet sheet = new Sheet(1, 0);
//            sheet.setClazz(BusinessImport.class);
//            sheet.setSheetName("azureAd");
//            List<com.last.demo.entity.BusinessInfo> allApps = businessInfoService.findAllBusinessInfo();
//            System.out.println("查询的集合长度" + allApps.size());
//            writer.write(allApps, sheet);
//            writer.finish();
//        } catch (
//                IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Excel导出
//     * @param response
//     * @param req
//     * @param fileName
//     */
//    private void ExcelServlet(HttpServletResponse response, HttpServletRequest req, String fileName) {
//        response.setContentType("application/vnd.ms-excel");
//        String contentDisposition = "";
//        try {
//            if (req.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
//                contentDisposition = "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1");// firefox浏览器
//            } else {
//                contentDisposition = "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8");// IE浏览器
//            }
//        } catch (UnsupportedEncodingException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//        response.setHeader("Content-Disposition", contentDisposition);
//        response.setCharacterEncoding("UTF-8");
//    }
//
//    /**
//     * 指定路径批量导入excel (多个sheet页)
//     */
//    @GetMapping("/import")
//    @SuppressWarnings(value = {"unchecked", "deprecation"})
//    public void testimport() {
//        String fileName = "E:\\elk\\商家数据分类数据--jy.xlsx";
//        // 读取全部sheet
//        // 这里需要注意 DemoDataListener的doAfterAllAnalysed 会在每个sheet读取完毕后调用一次。然后所有sheet都会往同一个DemoDataListener里面写
//        List<com.last.demo.entity.BusinessTypeConf> businessTypeConfs = businessTypeConfService.queryAllByBusinessType();
//        EasyExcel.read(fileName, com.last.demo.entity.BusinessInfo.class, new BusinessListener(businessInfoService, businessTypeConfService)).doReadAll();
//    }
//
//    /**
//     * Excel根据sheet数量导入
//     *
//     * @param file
//     * @return
//     * @throws IOException
//     */
//    @GetMapping("/import1")
//    public String importResources(@RequestParam(value = "file") MultipartFile file) throws IOException {
//        try (
//                InputStream inputStream = file.getInputStream()) {
//            AnalysisEventListener listener = new BusinessListener(businessInfoService, businessTypeConfService);
//            ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, listener);
//            Sheet sheet = excelReader.getSheets().get(0);
//            sheet.setClazz(BusinessImport.class);
//            excelReader.read(sheet);
//        }
//        return "success";
//    }
//
//}
//
//
//
