package com.work.jy.work.api.util;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private boolean result;
    private String msg;
    private int code;
    private T data;

    public static Result serverError() {
        return new Result(ResponseCode.SERVICE_CRASH, false);
    }

    public static Result fail() {
        return new Result(ResponseCode.FAIL_CODE, false);
    }

    public static Result limit() {
        return new Result(ResponseCode.LIMIT_CODE, false);
    }

    public static Result noLogin() {
        return new Result(ResponseCode.TOKEN_EMPTY, false);
    }

    public static Result noAuth() {
        return new Result(ResponseCode.PERMISSION_ERROR, false);
    }

    public static Result fail(ResponseCode responseCode) {
        return new Result(responseCode, false);
    }

    public static Result success() {
        return new Result(ResponseCode.SUCCESS, true);
    }

    public static <T> Result<T> success(T date) {
        return new Result(ResponseCode.SUCCESS, true, date);
    }

    public static Result successMsg(String msg) {
        return new Result(ResponseCode.SUCCESS, true, msg);
    }

    public static <T> Result<T> successMsg(String msg, T date) {
        return new Result(ResponseCode.SUCCESS, true, msg, date);
    }

    public static Result error() {
        return new Result(ResponseCode.ERROR, false);
    }

    public static <T> Result<T> error(T date) {
        return new Result(ResponseCode.ERROR, false, date);
    }

    public static Result errorMsg(String msg) {
        return new Result(ResponseCode.ERROR, false, msg);
    }

    public static <T> Result<T> errorMsg(String msg, T date) {
        return new Result(ResponseCode.ERROR, false, msg, date);
    }

    public static Result paramError(String msg) {
        return new Result(ResponseCode.PARAM_ERROR, false, msg);
    }

    public Result() {
    }

    private Result(ResponseCode responseCode, boolean result) {
        this.msg = responseCode.getMsg();
        this.code = responseCode.getCode();
        this.result = result;
    }

    private Result(ResponseCode responseCode, boolean result, T data) {
        this.msg = responseCode.getMsg();
        this.code = responseCode.getCode();
        this.result = result;
        this.data = data;
    }

    private Result(ResponseCode responseCode, boolean result, String msg) {
        this.code = responseCode.getCode();
        this.msg = msg;
        this.result = result;
    }

    private Result(ResponseCode responseCode, boolean result, String msg, T data) {
        this.code = responseCode.getCode();
        this.msg = msg;
        this.result = result;
        this.data = data;
    }

    public boolean isResult() {
        return this.result;
    }

    public String getMsg() {
        return this.msg;
    }

    public int getCode() {
        return this.code;
    }

    public T getData() {
        return this.data;
    }

    public Result<T> setResult(final boolean result) {
        this.result = result;
        return this;
    }

    public Result<T> setMsg(final String msg) {
        this.msg = msg;
        return this;
    }

    public Result<T> setCode(final int code) {
        this.code = code;
        return this;
    }

    public Result<T> setData(final T data) {
        this.data = data;
        return this;
    }
}
