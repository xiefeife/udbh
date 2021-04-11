package com.last.demo.util;

import lombok.Data;

/**
 * 统一API响应
 *
 * @author BaiGuoyong
 * @since 2020/8/29 10:40
 **/
@Data
public class Result<T> {

    private String code;

    private String message;

    private T data;

    public static <T> Result<T> succuess(String code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }


    public static <T> Result<T> succuess(String code, String message) {
        return succuess(code, message, null);
    }

    public static <T> Result<T> succuess(ResultCode resultCode, T data) {
        return succuess(resultCode.code(), resultCode.message(), data);
    }

    public static <T> Result<T> succuess(ResultCode resultCode) {
        return succuess(resultCode, null);
    }

    public static <T> Result<T> succuess(T data) {
        return succuess(ResultCode.SUCCESS, data);
    }

    public static <T> Result<T> fail(String code, String message, T data) {
        Result<T> result = new Result<T>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail(ResultCode resultCode, T data) {
        return fail(resultCode.code(), resultCode.message(), data);
    }

    public static <T> Result<T> fail(String code, String message) {
        return fail(code, message, null);
    }

    public static <T> Result<T> fail(ResultCode resultCode) {
        return fail(resultCode.code(), resultCode.message(), null);
    }

    public static <T> Result<T> fail(T data) {
        return fail(ResultCode.FAILED, data);
    }

    public static <T> Result<T> fail() {
        return fail(ResultCode.FAILED, null);
    }

    private void setResultCode(ResultCode resultCode) {
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

}
