package com.lt.stock.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @description: 返回数据类
 * @author: ~Teng~
 * @date: 2023/1/6 18:43
 */
//保证序列化json的时候,如果是null的对象,key也会消失
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> implements Serializable {
    private static final long serialVersionUID = 7735505903525411467L;

    // 成功值
    private static final int SUCCESS_CODE = 1;

    // 失败值
    private static final int ERROR_CODE = 0;

    /**
     * 状态码
     */
    private int code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    private Response(int code) {
        this.code = code;
    }

    private Response(int code, T data) {
        this.code = code;
        this.data = data;
    }

    private Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Response(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Response<T> ok() {
        return new Response<T>(SUCCESS_CODE, "success");
    }

    public static <T> Response<T> ok(String msg) {
        return new Response<T>(SUCCESS_CODE, msg);
    }

    public static <T> Response<T> ok(T data) {
        return new Response<T>(SUCCESS_CODE, data);
    }

    public static <T> Response<T> ok(String msg, T data) {
        return new Response<T>(SUCCESS_CODE, msg, data);
    }

    public static <T> Response<T> error() {
        return new Response<T>(ERROR_CODE, "error");
    }

    public static <T> Response<T> error(String msg) {
        return new Response<T>(ERROR_CODE, msg);
    }

    public static <T> Response<T> error(int code, String msg) {
        return new Response<T>(code, msg);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
