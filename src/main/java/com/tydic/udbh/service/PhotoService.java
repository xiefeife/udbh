package com.last.demo.service;

import com.last.demo.entity.Photo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author: xiehh
 * @Date:2020/11/28 17:08
 * @ClassName:PhotoService
 */
public interface PhotoService {

    /**
     * 批量添加图片信息 里面包含图片流和图片商家id
     * @param list
     */
    void batchPhoto(List<Photo> list);

    /**
     * 根据商家图片(背景或头像url)查询
     * @param businessId
     * @return
     */
    Photo selectByPrimaryKey(String businessId);

    void savePhoto(Photo photo);

    /**
     * 从数据库查询图片
     */
    void getPhotoFromDb(String path, HttpServletResponse response);

    /**
     * 从oss查询图片
     * @param path
     * @param response
     * @throws IOException
     */
    void getPhotoFromOss(String path, HttpServletResponse response) throws IOException;


    List<Photo> findAllPhoto();

}
