package com.last.demo.common;

import lombok.Data;

/**
 * 统一API响应
 *
 * @author BaiGuoyong
 * @since 2020/8/29 10:40
 **/
@Data
public class ResponseResult<T> {

    private String code;

    private String message;

    private T data;

    public static <T> ResponseResult<T> succuess(String code, String message, T data) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(code);
        responseResult.setMessage(message);
        responseResult.setData(data);
        return responseResult;
    }


    public static <T> ResponseResult<T> succuess(String code, String message) {
        return succuess(code, message, null);
    }

    public static <T> ResponseResult<T> succuess(ResponseResultCode responseResultCode, T data) {
        return succuess(responseResultCode.code(), responseResultCode.message(), data);
    }

    public static <T> ResponseResult<T> succuess(ResponseResultCode responseResultCode) {
        return succuess(responseResultCode, null);
    }

    public static <T> ResponseResult<T> succuess(T data) {
        return succuess(ResponseResultCode.SUCCESS, data);
    }

    public static <T> ResponseResult<T> fail(String code, String message, T data) {
        ResponseResult<T> responseResult = new ResponseResult<T>();
        responseResult.setCode(code);
        responseResult.setMessage(message);
        responseResult.setData(data);
        return responseResult;
    }

    public static <T> ResponseResult<T> fail(ResponseResultCode responseResultCode, T data) {
        return fail(responseResultCode.code(), responseResultCode.message(), data);
    }

    public static <T> ResponseResult<T> fail(String code, String message) {
        return fail(code, message, null);
    }
    public static <T> ResponseResult<T> fail(String message,T data) {
        return fail(ResponseResultCode.FAILED.code(), message, data);
    }

    public static <T> ResponseResult<T> fail(ResponseResultCode responseResultCode) {
        return fail(responseResultCode.code(), responseResultCode.message(), null);
    }

    public static <T> ResponseResult<T> fail(String message) {
        return fail(ResponseResultCode.FAILED.code(), message, null);
    }

    public static <T> ResponseResult<T> fail() {
        return fail(ResponseResultCode.FAILED, null);
    }

    private void setResultCode(ResponseResultCode responseResultCode) {
        this.code = responseResultCode.code();
        this.message = responseResultCode.message();
    }

}
