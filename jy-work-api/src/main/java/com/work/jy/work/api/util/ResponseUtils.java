package com.work.jy.work.api.util;

import org.springframework.http.ResponseEntity;

public class ResponseUtils {
    public ResponseUtils() {
    }

    public static <T> ResponseEntity<Result<T>> success(String msg) {
        Result<T> responseForm = new Result();
        responseForm.setMsg(msg);
        responseForm.setData(null);
        responseForm.setCode(200);
        responseForm.setResult(true);
        return ResponseEntity.ok(responseForm);
    }

    public static <T> ResponseEntity<Result<T>> httpCodeError(int errorCode, String errorMsg) {
        Result<T> responseForm = new Result();
        responseForm.setMsg(errorMsg);
        responseForm.setData(null);
        responseForm.setCode(errorCode);
        responseForm.setResult(true);
        return ResponseEntity.status(errorCode).body(responseForm);
    }

    public static <T> ResponseEntity<Result<T>> codeError(int errorCode, String errorMsg) {
        Result<T> responseForm = new Result();
        responseForm.setMsg(errorMsg);
        responseForm.setData(null);
        responseForm.setCode(errorCode);
        responseForm.setResult(true);
        return ResponseEntity.ok(responseForm);
    }

    public static <T> ResponseEntity<Result<T>> error(String errorMsg) {
        Result<T> responseForm = new Result();
        responseForm.setMsg(errorMsg);
        responseForm.setData(null);
        responseForm.setCode(400);
        responseForm.setResult(true);
        return ResponseEntity.ok(responseForm);
    }

    public static <T> ResponseEntity<Result<T>> success(String msg, T data) {
        Result<T> responseForm = new Result();
        responseForm.setMsg(msg);
        responseForm.setData(data);
        responseForm.setCode(200);
        responseForm.setResult(true);
        return ResponseEntity.ok(responseForm);
    }

    public static <T> ResponseEntity<Result<T>> httpCodeError(int errorCode, String errorMsg, T data) {
        Result<T> responseForm = new Result();
        responseForm.setMsg(errorMsg);
        responseForm.setData(data);
        responseForm.setCode(errorCode);
        responseForm.setResult(true);
        return ResponseEntity.status(errorCode).body(responseForm);
    }

    public static <T> ResponseEntity<Result<T>> codeError(int errorCode, String errorMsg, T data) {
        Result<T> responseForm = new Result();
        responseForm.setMsg(errorMsg);
        responseForm.setData(data);
        responseForm.setCode(errorCode);
        responseForm.setResult(true);
        return ResponseEntity.ok(responseForm);
    }

    public static <T> ResponseEntity<Result<T>> error(String errorMsg, T data) {
        Result<T> responseForm = new Result();
        responseForm.setMsg(errorMsg);
        responseForm.setData(data);
        responseForm.setCode(400);
        responseForm.setResult(true);
        return ResponseEntity.ok(responseForm);
    }
}
