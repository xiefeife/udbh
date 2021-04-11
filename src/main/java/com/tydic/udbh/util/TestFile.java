package com.last.demo.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: xiehh
 * @Date:2020/12/4 10:24
 * @ClassName:TestFile
 */
public class TestFile {
    private Logger log = LoggerFactory.getLogger(this.getClass());


    public static void path(){
        String property = System.getProperty("user.dir");
        System.out.println(property);
    }

    public static void main(String[] args) {
        TestFile.path();

        //创建单个文件
//        boolean file = FileUtils.createFile("D:\\aaa\\a.mp4");
//        //创建目录
//        boolean directory = FileUtils.createDirectory("D:\\aaa\\a");
//        System.out.println(directory);


    }


}
