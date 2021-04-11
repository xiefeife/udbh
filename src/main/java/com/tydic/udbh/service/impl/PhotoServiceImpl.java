package com.tydic.udbh.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.model.OSSObject;
import com.google.common.base.Strings;
import com.last.demo.common.ResponseResult;
import com.last.demo.entity.Photo;
import com.last.demo.service.PhotoService;

import com.last.demo.util.JsonUtils;
import com.last.demo.util.oss.AliyunOssConfig;
import com.tydic.udbh.repository.PhotoDao;
import com.tydic.udbh.util.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author: xiehh
 * @Date:2020/11/28 17:08
 * @ClassName:PhotoServiceImpl
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoDao photoDao;

    /**
     * 批量添加图片信息 里面包含图片流和图片商家id
     *
     * @param list
     */
    @Override
    public void batchPhoto(List<Photo> list) {
        photoDao.batchPhoto(list);
    }

    /**
     * 根据商家图片(背景或头像url)查询
     *
     * @param businessId
     * @return
     */
    @Override
    public Photo selectByPrimaryKey(String businessId) {
        Photo photo = photoDao.selectByPrimaryKey(businessId);
        if (Objects.isNull(photo)) {
            return new Photo();
        }
        return photo;
    }

    @Override
    public void savePhoto(Photo photo) {
        photoDao.savePhoto(photo);
    }

    /**
     * 从数据库查询图片
     *
     * @param path
     * @param response
     */
    @Override
    public void getPhotoFromDb(String path, HttpServletResponse response) {
        //从数据库查询数据
        Photo allByBusinessBg = photoDao.selectByPrimaryKey(path);
        byte[] bytes = allByBusinessBg.getBusinessImage();
        ServletOutputStream sos = null;
        try {
            sos = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            sos.write(bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            sos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
    }

    /**
     * 从oss查询图片
     *
     * @param path
     * @param response
     * @throws IOException
     */
    @Override
    public void getPhotoFromOss(String path, HttpServletResponse response) throws IOException {
        //从阿里云查询图片
        response.setCharacterEncoding("utf-8");
        if (Strings.isNullOrEmpty(path)) {
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            try {
                response.getWriter().println(JsonUtils.toString(ResponseResult.fail("路径不能为空！")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OSSObject ossObject = AliyunOssConfig.getOssClient().getObject(AliyunOssConfig.JAVA_BUCKET_NAME, path);
        response.setContentType("image/png;image/gif;image/jpeg;image/bmp");
        try {
            response.getOutputStream().write(IOUtils.toByteArray(ossObject.getObjectContent()));
        } catch (IOException e) {
            e.printStackTrace();
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            try {
                response.getWriter().println(JsonUtils.toString(ResponseResult.fail("路径不能为空！")));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } finally {
            AliyunOssConfig.getOssClient().shutdown();
            try {
                response.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Photo> findAllPhoto() {
        System.out.println(JSON.toJSONString(photoDao.findAllPhoto()));
        return photoDao.findAllPhoto();
    }


}
