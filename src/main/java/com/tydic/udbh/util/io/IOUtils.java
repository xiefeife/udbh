/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.tydic.udbh.util.io;

import ch.qos.logback.core.joran.conditional.IfAction;

import java.io.*;

/**
 * 数据流工具类
 *
 * @author ThinkGem
 */
public class IOUtils extends org.apache.commons.io.IOUtils {

    public static void main(String[] args) throws IOException {
        String path = "我有一条小毛驴,";
        creatFile(path);
        writeFile(path, path);

    }

    /**
     * 创建指定路径下文件夹
     *
     * @throws IOException
     */
    public static void creatFile(String path) throws IOException {
        File file = new File(path);
        try {
            if (file.exists()) {
                file.delete();
                System.out.println(file.getPath());
                System.out.println("当前文件夹下有word.txt文件,删除成功");
                System.out.println(file.getAbsolutePath());
                System.out.println(file.getCanonicalPath());//得到绝对路径
            } else {
                file.createNewFile();
                System.out.println("当前文件夹下没有word.txt,创建成功");
            }

        } catch (Exception e) {

        }


    }

    /**
     * 向指定路径下文件写入字符串
     *
     * @param path
     * @param msg
     */
    public static void writeFile(String path, String msg) throws IOException {

        File file = new File(path);
        try {
            //创建FileOutputStream对象  往文件写入数据
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bytes = msg.getBytes();
            fileOutputStream.write(bytes);
            fileOutputStream.close();
        } catch (IOException e) {
            e.getMessage();
        }
        try {
            //创建fileIntputStream对象   读取文件shuj
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int len = fileInputStream.read(bytes);
            fileInputStream.read(bytes);
            System.out.println("文件中读取到的信息写出"+new String(bytes,0,len));
            fileInputStream.close();
        } catch (Exception e) {

        }
    }

    /**
     * 读取指定路径下指定文件内信息
     */
    public static void readfile(String path) {


    }


    /**
     * 根据文件路径创建文件输入流处理 以字节为单位（非 unicode ）
     *
     * @param
     * @return
     */
    public static FileInputStream getFileInputStream(String filepath) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filepath);
        } catch (FileNotFoundException e) {
            System.out.println("错误信息:文件不存在");
            e.printStackTrace();
        }
        return fileInputStream;
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }


    /**
     * 根据文件对象创建文件输入流处理 以字节为单位（非 unicode ）
     *
     * @param
     * @return
     */
    public static FileInputStream getFileInputStream(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("错误信息:文件不存在");
            e.printStackTrace();
        }
        return fileInputStream;
    }

    /**
     * 根据文件对象创建文件输出流处理 以字节为单位（非 unicode ）
     *
     * @param file
     * @param append true:文件以追加方式打开,false:则覆盖原文件的内容
     * @return
     */
    public static FileOutputStream getFileOutputStream(File file, boolean append) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file, append);
        } catch (FileNotFoundException e) {
            System.out.println("错误信息:文件不存在");
            e.printStackTrace();
        }
        return fileOutputStream;
    }

    /**
     * 根据文件路径创建文件输出流处理 以字节为单位（非 unicode ）
     *
     * @param
     * @param append true:文件以追加方式打开,false:则覆盖原文件的内容
     * @return
     */
    public static FileOutputStream getFileOutputStream(String filepath, boolean append) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(filepath, append);
        } catch (FileNotFoundException e) {
            System.out.println("错误信息:文件不存在");
            e.printStackTrace();
        }
        return fileOutputStream;
    }

}
