package com.tydic.udbh.controller;


import com.last.demo.config.EsConfig;
import com.last.demo.config.POIExcel;
import com.last.demo.entity.BusinessInfo;
import com.last.demo.entity.BusinessTypeConf;
import com.last.demo.entity.Photo;
import com.last.demo.entity.request.ExportBusienss;
import com.last.demo.service.BusinessTypeConfService;
import com.last.demo.service.PhotoService;
import com.last.demo.util.ByteUtils;
import com.last.demo.util.Result;
import com.last.demo.util.TestBaiDuApi;
import com.last.demo.util.oss.AliyunOssConfig;
import com.last.demo.util.oss.AliyunOssResult;
import com.tydic.udbh.service.BusinessInfoService;
import com.tydic.udbh.util.oss.AliyunOSSUtil;
import org.apache.http.entity.ContentType;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


/**
 * @author: xiehh        导入商家信息excel
 * @Date:2020/11/22 11:52
 * @ClassName:poiController
 */
@RestController
@RequestMapping("/api/excel")
public class ExcelController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BusinessInfoService businessInfoService;
    @Autowired
    private BusinessTypeConfService businessTypeConfService;
    @Autowired
    private PhotoService photoService;



    /**
     * excel导入带图片  底层使用foreach
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/import")
    public Result importExport(MultipartFile file) throws Exception {
        {
//            String oldName = file.getOriginalFilename();
//            String fileType = oldName.substring(oldName.lastIndexOf(".") + 1);
//            if ("xls".equals(fileType)) {
//                return Result.fail("导入失败,请更换07版excel!");
//            }
            Map<String, String> resultMap = new HashMap<>();
            List<BusinessInfo> businessInfoList = new ArrayList<>();
            //得到工作簿            }
            Workbook wb = new XSSFWorkbook(file.getInputStream());
            //获取每个Sheet表
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                XSSFSheet sheet = (XSSFSheet) wb.getSheetAt(i);
                //获取图片和位置 (xlsx)   获取图片和为位置 返回值是 key--2-8列数
                Map<String, PictureData> map = POIExcel.getPictures2(sheet);
                //lastRowNum 使用这个函数只会获得一样格式的excel 格式不一样的不会取到
                Integer lastRowNum = getLastRowNum(wb);
                logger.info("读取当前第:{}个sheet", i + 1);
                List<String> stringList = POIExcel.printImg(map);
                //获取导入excel的第一行表头进行校验
                Row row0 = sheet.getRow(0);
                if (!"序号".equals(row0.getCell(0).toString())) {
                    return Result.fail("第一列列表头不正确,请检查后再导入");
                }
                if (!"商家地市".equals(row0.getCell(1).toString())) {
                    return Result.fail("第二列表头不正确,请检查后再导入");
                }
                if (!"商家类型".equals(row0.getCell(2).toString())) {
                    return Result.fail("第三列表头不正确,请检查后再导入");
                }
                if (!"商家名称".equals(row0.getCell(3).toString())) {
                    return Result.fail("第四列表头不正确,请检查后再导入");
                }
                if (!"地址".equals(row0.getCell(4).toString())) {
                    return Result.fail("第五列表头不正确,请检查后再导入");
                }
                if (!"位置".equals(row0.getCell(5).toString())) {
                    return Result.fail("第六列表头不正确,请检查后再导入");
                }
                if (!"电话".equals(row0.getCell(6).toString())) {
                    return Result.fail("第七列表头不正确,请检查后再导入");
                }
                if (!"店铺展示".equals(row0.getCell(7).toString())) {
                    return Result.fail("第八列表头不正确,请检查后再导入");
                }
                if (!"广告位1".equals(row0.getCell(8).toString())) {
                    return Result.fail("第九列表头不正确,请检查后再导入");
                }
                if (!"广告位2".equals(row0.getCell(9).toString())) {
                    return Result.fail("第十列表头不正确,请检查后再导入");
                }
                if (!"广告位3".equals(row0.getCell(10).toString())) {
                    return Result.fail("第十一表头不正确,请检查后再导入");
                }
                if (!"广告位4".equals(row0.getCell(11).toString())) {
                    return Result.fail("第十二列表头不正确,请检查后再导入");
                }
                if (!"操作时间".equals(row0.getCell(12).toString())) {
                    return Result.fail("第十三列表头不正确,请检查后再导入");
                }
                if (!"操作人".equals(row0.getCell(13).toString())) {
                    return Result.fail("第十四列表头不正确,请检查后再导入");
                }
                // 获取每行  sheet.getLastRowNum()
                for (int j = 1; j < lastRowNum; j++) {
                    Row row = sheet.getRow(j);
                    BusinessInfo b = new BusinessInfo();
                    b.setBusinessId(UUID.randomUUID().toString().replace("-", ""));
                    //给商家类型赋值
//                    if (null != row.getCell(2).toString() ){
//                        BusinessTypeConf businessTypeConf = businessAssignment(row.getCell(2).toString());
//                        b.setBusinessTypeId(businessTypeConf.getBusinessTypeId());
//                    }
                    if (null != row.getCell(0)) {
                        row.getCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);
                        b.setBusinessOriginalId(row.getCell(0).toString());
                    }
                    //商家地市
                    if (null != row.getCell(1)) {
                        b.setBusinessZzLogo(row.getCell(1).toString());
                    }
                    //商家名称
                    if (null != row.getCell(3)) {
                        row.getCell(3).setCellType(HSSFCell.CELL_TYPE_STRING);
                        b.setBusinessName(row.getCell(3).getStringCellValue());
                    }
                    //商家地址
                    if (null != row.getCell(4)) {
                        b.setBusinessAddress(row.getCell(4).getStringCellValue());
                    }
                    //商家电话
                    if (null != row.getCell(6)) {
//                        row.getCell(6).setCellType(HSSFCell.CELL_TYPE_STRING);
                        b.setBusinessPhone(row.getCell(6).toString());
                    }
                    //exccel导入时 头像为空处理
                    if (null == row0.getCell(7)){
                    b.setBusinessHeadUrl("");
                    }
                    //excel导入时  背景为空
                    if (null == row0.getCell(9)){
                    }
                    for (int r = 0; r < 13; r++) {
                        int random = (int) (Math.random() * 100);
                        b.setBusinessLngLat("40.2264133" + random + ",116.23761791" + random + "");
                    }
                    b.setProvinceCityId(403L);
                    b.setBusinessStatus(String.valueOf(1));
                    //商家头像背景
                    StringBuffer bg = new StringBuffer();
                    StringBuffer urllist = new StringBuffer();
                    for (String s : stringList) {
                        if (j == Integer.valueOf(s.substring(0, s.indexOf("-")))) {
                            String str1 = s.substring(0, s.indexOf("-"));
                            String str2 = s.substring(str1.length() + 1, s.length());
                            if (s.contains("7")) {
                                if (AliyunOssConfig.isOssAttach) {
                                    //存储到oss
                                    b.setBusinessHeadUrl(getPhotoUrl(s));
                                } else {
                                    Photo headPhoto = new Photo();
                                    //获取到头像的路径
                                    String headPath = POIExcel.getJarPath() + s + ".jpg";
                                    //poi读取到照片的路径  必须是存在的
                                    headPhoto.setBusinessImage(ByteUtils.imageTobyte(headPath));
                                    //数据库中photo的主键id
                                    String hdPath = UUID.randomUUID().toString().replace("-", "") + ".jpg";
                                    //对应商家表里头像的路径
                                    headPhoto.setBusinessId(hdPath);
                                    b.setBusinessHeadUrl(hdPath);
                                    photoService.savePhoto(headPhoto);
                                    // photoList.add(headPhoto);
                                    File headFile = new File(headPath);
                                    if (headFile.delete()) {
                                        logger.info("文件：{} 删除成功！", headFile.getPath());
                                    } else {
                                        logger.error("文件：{} 删除异常！", headFile.getPath());
                                    }
                                }
                            }
                            if (Integer.valueOf(str2) > 7) {
                                bg.append("," + s);
                            }
                        }
                    }
                    String str = bg.toString();
                    if (null != str && !"".equals(str)) {
                        String sub = str.substring(1);
                        //bglist是所有商家背景图片目录的集合
                        List<String> bglist = Arrays.asList(sub.split(","));
                        bglist.stream().forEach(f -> {
                            if (AliyunOssConfig.isOssAttach) {
                                try {
                                    //存储图片到oss
                                    String url = null;
                                    url = getPhotoUrl(f);
                                    urllist.append(url + ",");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Photo bgPhoto = new Photo();
                                String bgPath = POIExcel.getJarPath() + f + ".jpg";
                                String bgpath = UUID.randomUUID().toString().replace("-", "") + ".jpg";//图片的主键id
                                //poi读取照片表的路径 必须真实存在
                                bgPhoto.setBusinessImage(ByteUtils.imageTobyte(bgPath));
                                bgPhoto.setBusinessId(bgpath);
                                photoService.savePhoto(bgPhoto);
                                //    photoList.add(bgPhoto);
                                urllist.append(bgpath + ",");
                                File headFile = new File(bgPath);
                                if (headFile.delete()) {
                                    logger.info("文件：{} 删除成功！", headFile.getPath());
                                } else {
                                    logger.error("文件：{} 删除异常！", headFile.getPath());
                                }
                            }

                        });
                        b.setBusinessBgUrl(urllist.toString().substring(0, urllist.lastIndexOf(",")));
                    }
                    businessInfoList.add(b);
//                    businessInfoService.save(b);  单个添加
                }
            }
            System.out.println("excel导入list数量+" + businessInfoList.size());
            businessInfoService.saveListbusinessList(businessInfoList);





            resultMap.put("message", "导入成功");
            return Result.succuess(resultMap);
        }
    }
    /**
     * excel导入带图片  底层使用batch
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/import1")
    public Result importExport1(MultipartFile file) throws Exception {
        {
            Map<String, String> resultMap = new HashMap<>();
            List<BusinessInfo> businessInfoList = new ArrayList<>();
            //得到工作簿            }
            Workbook wb = new XSSFWorkbook(file.getInputStream());
            //获取每个Sheet表
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                XSSFSheet sheet = (XSSFSheet) wb.getSheetAt(i);
                //获取图片和位置 (xlsx)   获取图片和为位置 返回值是 key--2-8列数
                Map<String, PictureData> map = POIExcel.getPictures2(sheet);
                //lastRowNum 使用这个函数只会获得一样格式的excel 格式不一样的不会取到
                Integer lastRowNum = getLastRowNum(wb);
                logger.info("读取当前第:{}个sheet", i + 1);
                List<String> stringList = POIExcel.printImg(map);
                //获取导入excel的第一行表头进行校验
                Row row0 = sheet.getRow(0);
                if (!"序号".equals(row0.getCell(0).toString())) {
                    return Result.fail("第一列列表头不正确,请检查后再导入");
                }
                if (!"商家地市".equals(row0.getCell(1).toString())) {
                    return Result.fail("第二列表头不正确,请检查后再导入");
                }
                if (!"商家类型".equals(row0.getCell(2).toString())) {
                    return Result.fail("第三列表头不正确,请检查后再导入");
                }
                if (!"商家名称".equals(row0.getCell(3).toString())) {
                    return Result.fail("第四列表头不正确,请检查后再导入");
                }
                if (!"地址".equals(row0.getCell(4).toString())) {
                    return Result.fail("第五列表头不正确,请检查后再导入");
                }
                if (!"位置".equals(row0.getCell(5).toString())) {
                    return Result.fail("第六列表头不正确,请检查后再导入");
                }
                if (!"电话".equals(row0.getCell(6).toString())) {
                    return Result.fail("第七列表头不正确,请检查后再导入");
                }
                if (!"店铺展示".equals(row0.getCell(7).toString())) {
                    return Result.fail("第八列表头不正确,请检查后再导入");
                }
                if (!"广告位1".equals(row0.getCell(8).toString())) {
                    return Result.fail("第九列表头不正确,请检查后再导入");
                }
                if (!"广告位2".equals(row0.getCell(9).toString())) {
                    return Result.fail("第十列表头不正确,请检查后再导入");
                }
                if (!"广告位3".equals(row0.getCell(10).toString())) {
                    return Result.fail("第十一表头不正确,请检查后再导入");
                }
                if (!"广告位4".equals(row0.getCell(11).toString())) {
                    return Result.fail("第十二列表头不正确,请检查后再导入");
                }
                if (!"操作时间".equals(row0.getCell(12).toString())) {
                    return Result.fail("第十三列表头不正确,请检查后再导入");
                }
                if (!"操作人".equals(row0.getCell(13).toString())) {
                    return Result.fail("第十四列表头不正确,请检查后再导入");
                }
                // 获取每行  sheet.getLastRowNum()
                for (int j = 1; j < lastRowNum; j++) {
                    Row row = sheet.getRow(j);
                    BusinessInfo b = new BusinessInfo();
                    b.setBusinessId(UUID.randomUUID().toString().replace("-", ""));
                    //给商家类型赋值
//                    if (null != row.getCell(2).toString() ){
//                        BusinessTypeConf businessTypeConf = businessAssignment(row.getCell(2).toString());
//                        b.setBusinessTypeId(businessTypeConf.getBusinessTypeId());
//                    }
                    if (null != row.getCell(0)) {
                        row.getCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);
                        b.setBusinessOriginalId(row.getCell(0).toString());
                    }
                    //商家地市
                    if (null != row.getCell(1)) {
                        b.setBusinessZzLogo(row.getCell(1).toString());
                    }
                    //商家名称
                    if (null != row.getCell(3)) {
                        row.getCell(3).setCellType(HSSFCell.CELL_TYPE_STRING);
                        b.setBusinessName(row.getCell(3).getStringCellValue());
                    }
                    //商家地址
                    if (null != row.getCell(4)) {
                        b.setBusinessAddress(row.getCell(4).getStringCellValue());
                    }
                    //商家电话
                    if (null != row.getCell(6)) {
//                        row.getCell(6).setCellType(HSSFCell.CELL_TYPE_STRING);
                        b.setBusinessPhone(row.getCell(6).toString());
                    }
                    //exccel导入时 头像为空处理
                    if (null == row0.getCell(7)){
                        b.setBusinessHeadUrl("");
                    }
                    //excel导入时  背景为空
                    if (null == row0.getCell(9)){
                    }
                    for (int r = 0; r < 13; r++) {
                        int random = (int) (Math.random() * 100);
                        b.setBusinessLngLat("40.2264133" + random + ",116.23761791" + random + "");
                    }
                    b.setProvinceCityId(403L);
                    b.setBusinessStatus(String.valueOf(1));
                    //商家头像背景
                    StringBuffer bg = new StringBuffer();
                    StringBuffer urllist = new StringBuffer();
                    for (String s : stringList) {
                        if (j == Integer.valueOf(s.substring(0, s.indexOf("-")))) {
                            String str1 = s.substring(0, s.indexOf("-"));
                            String str2 = s.substring(str1.length() + 1, s.length());
                            if (s.contains("7")) {
                                if (AliyunOssConfig.isOssAttach) {
                                    //存储到oss
                                    b.setBusinessHeadUrl(getPhotoUrl(s));
                                } else {
                                    Photo headPhoto = new Photo();
                                    //获取到头像的路径
                                    String headPath = POIExcel.getJarPath() + s + ".jpg";
                                    //poi读取到照片的路径  必须是存在的
                                    headPhoto.setBusinessImage(ByteUtils.imageTobyte(headPath));
                                    //数据库中photo的主键id
                                    String hdPath = UUID.randomUUID().toString().replace("-", "") + ".jpg";
                                    //对应商家表里头像的路径
                                    headPhoto.setBusinessId(hdPath);
                                    b.setBusinessHeadUrl(hdPath);
                                    photoService.savePhoto(headPhoto);
                                    // photoList.add(headPhoto);
                                    File headFile = new File(headPath);
                                    if (headFile.delete()) {
                                        logger.info("文件：{} 删除成功！", headFile.getPath());
                                    } else {
                                        logger.error("文件：{} 删除异常！", headFile.getPath());
                                    }
                                }
                            }
                            if (Integer.valueOf(str2) > 7) {
                                bg.append("," + s);
                            }
                        }
                    }
                    String str = bg.toString();
                    if (null != str && !"".equals(str)) {
                        String sub = str.substring(1);
                        //bglist是所有商家背景图片目录的集合
                        List<String> bglist = Arrays.asList(sub.split(","));
                        bglist.stream().forEach(f -> {
                            if (AliyunOssConfig.isOssAttach) {
                                try {
                                    //存储图片到oss
                                    String url = null;
                                    url = getPhotoUrl(f);
                                    urllist.append(url + ",");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Photo bgPhoto = new Photo();
                                String bgPath = POIExcel.getJarPath() + f + ".jpg";
                                String bgpath = UUID.randomUUID().toString().replace("-", "") + ".jpg";//图片的主键id
                                //poi读取照片表的路径 必须真实存在
                                bgPhoto.setBusinessImage(ByteUtils.imageTobyte(bgPath));
                                bgPhoto.setBusinessId(bgpath);
                                photoService.savePhoto(bgPhoto);
                                //    photoList.add(bgPhoto);
                                urllist.append(bgpath + ",");
                                File headFile = new File(bgPath);
                                if (headFile.delete()) {
                                    logger.info("文件：{} 删除成功！", headFile.getPath());
                                } else {
                                    logger.error("文件：{} 删除异常！", headFile.getPath());
                                }
                            }

                        });
                        b.setBusinessBgUrl(urllist.toString().substring(0, urllist.lastIndexOf(",")));
                    }
                    businessInfoList.add(b);
//                    businessInfoService.save(b);  单个添加
                }
            }
            System.out.println("excel导入list数量+" + businessInfoList.size());
//            businessInfoService.insertBatch(businessInfoList);
            resultMap.put("message", "导入成功");
            return Result.succuess(resultMap);
        }
    }



    /**
     * Excel导出 使用easy excel  带参数导出
     */
    @PostMapping("/export")
    public void exportBusiness(@RequestBody ExportBusienss businessIds, HttpServletResponse response) {
        businessInfoService.exportBusiness(businessIds,response);
    }

    /**
     * Excel导出 使用easy excel  不带参数导出
     */
    @GetMapping("/export1")
    public void exportBusiness( HttpServletResponse response) {
        businessInfoService.exportBusiness1(response);
    }




    /**
     * 给导入的商家省份id赋值
     *
     * @param provinceName
     * @return
     */
//    @GetMapping("/province")
//    public Result getProvince(@PathVariable("provinceName") String provinceName) {
//        List<BusinessInfoExport> businessInfos = businessInfoService.queryAllBusiness();
//        List<ProvinceCity> province = provinceCityService.findAllprovince(provinceName);//省份表
//        try {
//            for (int j = 0; j < businessInfos.size(); j++) {//商家的
//                BusinessInfo b = businessInfos.get(j);
//                for (int i = 0; i < province.size(); i++) {//市区的
//                    ProvinceCity p = province.get(i);
//                    if (null != b.getBusinessZzType() && null != p.getCountyName()
//                            && b.getBusinessZzType().equals(p.getCityName())) {
//                        BusinessSetPrRequest response = new BusinessSetPrRequest();
//                        b.setProvinceCityId(p.getProvinceCityId());
//                        response.setBusinessId(b.getBusinessId());
//                        response.setProvinceCityId(p.getProvinceCityId());
//                        businessInfoService.updateBusinessPr(response);
//                    }
//                    //     }
//                }
//            }
//            return Result.succuess("200", "商家省份id赋值成功");
//        } catch (Exception e) {
//            logger.error("商家省份id赋值失败");
//            return Result.succuess("400", "商家省份id赋值失败");
//        }
//
//    }

    /**
     * 获取sheet的有效行
     *
     * @param wb
     * @return
     */
    public static Integer getLastRowNum(Workbook wb) {
        //获取第一个画布
        Sheet sheet = wb.getSheetAt(0);
        CellReference cellReference = new CellReference("A4");
        boolean flag = false;
        for (int i = cellReference.getRow(); i <= sheet.getLastRowNum(); ) {
            Row r = sheet.getRow(i);
            if (r == null) {
                //如果是空行（即没有任何数据、格式），直接把它以下的数据往上移动
                sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
                continue;
            }
            flag = false;
            for (Cell c : r) {
                if (c.getCellType() != Cell.CELL_TYPE_BLANK) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                i++;
                continue;
            } else {//如果是空白行（即可能没有数据，但是有一定格式）
                if (i == sheet.getLastRowNum()) {
                    //如果到了最后一行，直接将那一行remove掉
                    sheet.removeRow(r);
                } else {
                    //如果还没到最后一行，则数据往上移一行
                    sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
                }
            }
        }
        System.out.println("有效行数为:" + (sheet.getLastRowNum() + 1));
        return sheet.getLastRowNum() + 1;
    }


    /**
     * 配合本地路径 上传到阿里云
     *
     * @param
     * @return
     * @throws IOException
     */
    private String getPhotoUrl(String s) throws IOException {
        //图片路径
        String headPath = POIExcel.getJarPath() + s + ".jpg";
        File file = new File(headPath);
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("copy" + file.getName(),
                file.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(),
                fileInputStream);
        AliyunOssResult upload = AliyunOSSUtil.upload(multipartFile);
        if (file.delete()) {
            logger.info("文件：{} 删除成功！", file.getPath());
        } else {
            logger.error("文件：{} 删除异常！", file.getPath());
        }
        return AliyunOSSUtil.getImgUrlPath(upload.getUrl());
    }


    /**
     * 根据商家全路劲查找对应经纬度
     *
     * @param city    市区
     * @param county  县区
     * @param address 地址
     * @param data    商家实体类
     * @return
     */
    private String getLngLat(String city, String county, String address, BusinessInfo data) {
        String precision = null;//商家经度
        String latitude = null;
        StringBuffer province = new StringBuffer("河南省");
        //需要对商家地址进行过滤  排除XX省XX市
        StringBuffer append = province.append(city);
        Map lngAndLat = new HashMap();
        if (null != address && !address.contains(append.toString())) {
            StringBuffer detailAddress = province.append(city).append(county).append(address);
            lngAndLat = TestBaiDuApi.getLngAndLat(detailAddress.toString());
        } else {
            lngAndLat = TestBaiDuApi.getLngAndLat(address);
        }
        Double lng = (Double) lngAndLat.get("lng");
        Double lat = (Double) lngAndLat.get("lat");
        if (null == lng && null == lat) {
            data.setBusinessLngLat(null);
        }
        if (null != lng && null != lat) {
            precision = lng.toString();
            latitude = lat.toString();
        }
        //先纬度 后经度  方便存入es
        return (latitude + "," + precision);
    }


}
