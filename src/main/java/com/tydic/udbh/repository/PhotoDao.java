package com.tydic.udbh.repository;

import com.last.demo.entity.Photo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PhotoDao {

    int deleteByPrimaryKey(String businessId);

    void savePhoto(Photo photo);

    int insertSelective(Photo record);

    List<Photo> findAllPhoto();


    int updateByPrimaryKeySelective(Photo record);

    int updateByPrimaryKeyWithBLOBs(Photo record);

    /**
     * 批量添加图片信息 里面包含图片流和图片商家id
     * @param list
     */
    void batchPhoto(List<Photo> list);

    /**
     * 根据商家图片查询
     * @param businessId
     * @return
     */
    Photo selectByPrimaryKey(String businessId);

    /**
     * 根据商家背景查询对应图片
     * @param businessBg
     * @return
     */
    List<Photo> findAllByBusinessBg(String businessBg);



}
