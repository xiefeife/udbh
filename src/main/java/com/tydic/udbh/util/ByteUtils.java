package com.last.demo.util;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.io.*;

/**
 * @author: xiehh
 * @Date:2020/11/28 18:22
 * @ClassName:ByteUtils
 */
public class ByteUtils {

    //图片转换成byte数组
    public static byte[] imageTobyte(String path) {
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        } catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }

    /**
     * byte数组到图片
     * @param data
     * @param path
     */
    public static void byteToimage(byte[] data,String path){
        if(data.length<3||path.equals("")) return;
        try{
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            System.out.println("Make Picture success,Please find image in " + path);
        } catch(Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
    }


    //将Inputstream转化为字节数组
    public static  byte[] inputStresam2Byte(InputStream  inputStream) {
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new BufferedInputStream(inputStream);
            out = new ByteArrayOutputStream(1024);
            System.out.println("Available bytes:" + in.available());

            byte[] temp = new byte[1024];
            int size = 0;
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        byte[] content = out.toByteArray();
        System.out.println("Readed bytes count:" + content.length);
        return content;
    }

    //将InputStream转化为字节数组
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            //write方法的含义  write(byte b[], int off, int len
            /**buffer  需要输出的字节
             * off 偏移量  定义从哪里开始读取
             * len 需要读取字节的长度
             */
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    //byte数组转inputstream  InputStream sbs = new ByteArrayInputStream(byte[] buf);


}
