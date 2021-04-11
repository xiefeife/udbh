package com.tydic.udbh.poi;

import com.alibaba.excel.EasyExcel;
import com.last.demo.entity.BusinessInfo;
import com.tydic.udbh.service.BusinessInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiehh
 * @Date:2020/11/26 17:57
 * @ClassName:PhotoE
 */
@RestController
@RequestMapping("/photo")
public class PhotoExport {

    @Resource
    private BusinessInfoService businessInfoService;

    @GetMapping("/export")
    public void imageWrite() throws Exception {
        String fileName = "imageWrite" + System.currentTimeMillis() + ".xlsx";
        // 如果使用流 记得关闭
        InputStream inputStream = null;
        try {
//            BusinessInfo businessInfo = new BusinessInfo();
            List<BusinessInfo> list = new ArrayList<>();
            List<BusinessInfo> infoList = businessInfoService.findAllBusinessInfo();
            BusinessInfo businessInfo = new BusinessInfo();
            businessInfo.setBusinessHeadUrl("http:\\test-cszq.oss-cn-xixian-xxunicom-d01-a.ops.develop.xixian.unicom.local\\B20A2E8BD46E4B2198F91EAE5F3CDA0D.jpg");
            list.add(businessInfo);
//            infoList.add(businessInfo);
//            放入五种类型的图片 实际使用只要选一种即可
//            imageData.setByteArray(FileUtils.readFileToByteArray(new File(imagePath)));
//            imageData.setFile(new File(imagePath));
//            imageData.setString(imagePath);
//            inputStream = FileUtils.openInputStream(new File(imagePath));
//            imageData.setInputStream(inputStream);
//            imageData.setUrl(new URL(
//                    "https://raw.githubusercontent.com/alibaba/easyexcel/master/src/test/resources/converter/img.jpg"));
            EasyExcel.write(fileName, BusinessInfo.class).sheet().doWrite(list);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

}
