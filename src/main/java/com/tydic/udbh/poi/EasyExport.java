package com.tydic.udbh.poi;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.last.demo.entity.BusinessInfo;
import com.last.demo.entity.BusinessInfoExport;
import com.tydic.udbh.service.BusinessInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author: xiehh
 * @Date:2020/11/26 17:00
 * @ClassName:EasyExport
 */
@CrossOrigin
@RestController
@RequestMapping("/easyexcel")
public class EasyExport {

    @Autowired
    private BusinessInfoService businessInfoService;

    @GetMapping("/export")
    public void easyExport(HttpServletResponse response, HttpServletRequest req) {
        try {
            String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            ExcelServlet(response, req, "城市专区" + now + ".xlsx");
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
            //写第一个sheet
            @SuppressWarnings("unchecked")
            Sheet sheet = new Sheet(1, 0);
            sheet.setClazz(BusinessInfoExport.class);
            sheet.setSheetName("商家信息");
            List<BusinessInfo> allBusinessInfo =  businessInfoService.findAllBusinessInfo();//商家表
            List<BusinessInfoExport> result = new ArrayList<>();
            for (int i = 0; i < allBusinessInfo.size(); i++) {
                BusinessInfo s = allBusinessInfo.get(i);
                BusinessInfoExport b = new BusinessInfoExport();
                BeanUtils.copyProperties(s, b);
                b.setBusinessId(String.valueOf(i + 1));
                if (null!=s.getOperationUser()){
                    switch (s.getOperationUser()) {
                        case "1":
                            b.setOperationUser("省级用户");
                            break;
                        case "2":
                            b.setOperationUser("市级用户");
                            break;
                        case "3":
                            b.setOperationUser("县级用户");
                            break;
                        case "4":
                            b.setOperationUser("系统管理员");
                            break;
                        case "5":
                            b.setOperationUser("集团管理角色");
                            break;
                    }
                }
                result.add(b);
            }
            writer.write(result, sheet);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void ExcelServlet(HttpServletResponse response, HttpServletRequest req, String fileName)  {
        response.setContentType("application/vnd.ms-excel");
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String contentDisposition = "";
        try {
            if (req.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
                contentDisposition = "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1");// firefox浏览器
            } else {
                contentDisposition = "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8");// IE浏览器
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        response.setHeader("Content-Disposition", contentDisposition);
        response.setCharacterEncoding("UTF-8");
    }



    /**
     * 无模板导出  亲测可用
     * @param clazz
     * @param fileName
     * @param sheetName
     * @param response
     */
    @GetMapping("/downls")
    public  void exportExcel(Class clazz, String fileName, String sheetName, HttpServletResponse response ) {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("城市专区", "UTF-8") + ".xlsx");
            // 这里需要设置不关闭流
            Set<String> excludeColumnFiledNames = new HashSet<String>();
            List<BusinessInfo> data =  businessInfoService.findAllBusinessInfo();//商家表
            EasyExcel.write(response.getOutputStream(), BusinessInfoExport.class).excludeColumnFiledNames(excludeColumnFiledNames).autoCloseStream(Boolean.FALSE).sheet(sheetName)
                    .doWrite(data);
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            try {
                response.getWriter().println(JSON.toJSONString(map));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }




}
