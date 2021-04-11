package com.last.demo.util.oss;

/**
 * @author: xiehh
 * @Date:2020/11/20 16:40
 * @ClassName:AliyunOssResult
 */
public class AliyunOssResult {
    /**
     * code：200长传成功
     * code: 400上传失败
     */
    private int code;

    /**
     * 上传成功的返回url
     */
    private String url;

    /**
     * 错误提示
     */
    private String errMsg;

    /**
     * 文件名字
     */
    private String fileName ;


    public AliyunOssResult() {
    }

    public AliyunOssResult(int code, String url, String errMsg) {
        this.code = code;
        this.url = url;
        this.errMsg = errMsg;
    }

    public AliyunOssResult(int code, String url, String errMsg,String fileName) {
        this.code = code;
        this.url = url;
        this.errMsg = errMsg;
        this.fileName = fileName;
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return "AliyunOssResult{" +
                "code=" + code +
                ", url='" + url + '\'' +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }
}
