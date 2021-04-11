//package com.last.demo.controller;
//
//import com.aliyun.oss.model.OSSObject;
//import com.google.common.base.Strings;
//import com.last.demo.common.ResponseResult;
//import com.last.demo.config.CommonConfig;
//import com.last.demo.entity.Photo;
//import com.last.demo.service.PhotoService;
//import com.last.demo.util.ByteUtils;
//import com.last.demo.util.JsonUtils;
//import com.last.demo.util.Result;
//import com.last.demo.util.io.IOUtils;
//import com.last.demo.util.oss.AliyunOSSUtil;
//import com.last.demo.util.oss.AliyunOssConfig;
//import com.last.demo.util.oss.AliyunOssResult;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author: xiehh
// * @Date:2020/11/28 11:45
// * @ClassName:PhotoController
// */
//@RestController
//@RequestMapping("/api/photo")
//public class PhotoController {
//
//    private Logger log = LoggerFactory.getLogger(this.getClass());
//    @Autowired
//    private PhotoService photoService;
//    final CommonConfig commonConfig;
//
//    public PhotoController(CommonConfig commonConfig) {
//        this.commonConfig = commonConfig;
//    }
//
//    /**
//     * 文件上传
//     *
//     * @param file
//     * @return
//     * @throws IOException
//     */
//    @PostMapping("/import")
//    public Result photoSaveDb(MultipartFile file) throws IOException {
//        if (file.getSize() >= AliyunOssConfig.FILE_MAX_SIZE) {
//            return Result.fail("400", null, "上传失败,图片大于1MB");
//        }
//        Map<String, String> map = new HashMap<>();
//        System.out.println("是否上传" + AliyunOssConfig.isOssAttach);
//        if (AliyunOssConfig.isOssAttach) {
//            //上传文件到oss
//            AliyunOssResult upload = AliyunOSSUtil.upload(file);
//            if (null != upload.getErrMsg()) {
//                map.put("文件上传失败", upload.getErrMsg());
//                return Result.fail(map);
//            }
//            log.info("文件上传生成的url" + upload.getUrl());
//            log.info("返给前台url" + commonConfig.getOssImagesProvider() + AliyunOSSUtil.getImgUrlPath(upload.getUrl()));
//            map.put("url", commonConfig.getOssImagesProvider() + AliyunOSSUtil.getImgUrlPath(upload.getUrl()));
//            map.put("name", upload.getFileName());
//            return Result.succuess(map);
//        } else {
//            List<Photo> list = new ArrayList<Photo>();
//            //原来文件名
//            String realFilename = file.getOriginalFilename();
//            //获取文件格式后缀名
//            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
//            // 取当前时间戳作为文件名
//            String filename = System.currentTimeMillis() + type;
//
//            Photo photo = new Photo();
//            photo.setBusinessId(filename);
//            InputStream bytes = file.getInputStream();
//            photo.setBusinessImage(ByteUtils.toByteArray(bytes));
//            list.add(photo);
//            photoService.batchPhoto(list);
//            map.put("name", filename);
//            map.put("url", commonConfig.appendOssImagesProviderPrefix("/" + filename));
//        }
//        return Result.succuess(map);
//    }
//
//
//    /**
//     * 访问oss图片的方法
//     * @param path
//     * @param response
//     */
//    @GetMapping("/image/{path}")
//    public void getPhoto(@PathVariable("path") String path, HttpServletResponse response) {
//        if (Strings.isNullOrEmpty(path)) {
//            response.reset();
//            response.setContentType("application/json");
//            response.setCharacterEncoding("utf-8");
//            try {
//                response.getWriter().println(JsonUtils.toString(ResponseResult.fail("路径不能为空！")));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        OSSObject ossObject = AliyunOssConfig.getOssClient().getObject(AliyunOssConfig.JAVA_BUCKET_NAME, path);
//        response.setContentType("image/png;image/gif;image/jpeg;image/bmp");
//        try {
//            response.getOutputStream().write(IOUtils.toByteArray(ossObject.getObjectContent()));
//        } catch (IOException e) {
//            e.printStackTrace();
//            response.reset();
//            response.setContentType("application/json");
//            response.setCharacterEncoding("utf-8");
//            try {
//                response.getWriter().println(JsonUtils.toString(ResponseResult.fail("路径不能为空！")));
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
//        } finally {
//            AliyunOssConfig.getOssClient().shutdown();
//            try {
//                response.getOutputStream().close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//}
