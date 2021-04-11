package com.last.demo.common;

/**
 * 响应码
 *
 * @author BaiGuoyong
 * @since 2020/8/29 10:45
 **/
public enum ResponseResultCode {
    /**
     * 成功返回
     */
    SUCCESS("0000","成功！"),
    /**
     * 失败返回
     */
    FAILED("A9999","失败！"),
    /**
     * 参数校验失败
     */
    PARAMETER_VERIFY_FAILED("A00001","参数不完整，请检查入参！");

    private final String code;

    private final String message;

    ResponseResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}
