package com.work.jy.work.api.util;


public enum ResponseCode {
    SUCCESS(200, "请求正确"),
    ERROR(400, "请求不正确"),
    PARAM_ERROR(401, "参数错误"),
    PAY_ERROR_CODE(405, "支付失败"),
    FAIL_CODE(500, "禁止访问"),
    LIMIT_CODE(501, "服务繁忙"),
    SERVICE_CRASH(503, "服务器异常"),
    SERVICE_RETRY(504, "服务器连接失败"),
    TOKEN_EMPTY(506, "没有登陆"),
    TOKEN_EXPIRED(507, "登陆失效"),
    TOKEN_REPEAT(508, "异地登录"),
    PERMISSION_ERROR(510, "没有权限");

    private int code;
    private String msg;

    private ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
