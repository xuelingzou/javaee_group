package com.example.auto_warehouse.util;

public class JsonResult <T>{
    private T data;
    private int code; // code这里填写HTTP的状态码
    private String msg;

    /**
     * 若没有数据返回，可以人为指定状态码和提示信息
     * @param code
     * @param msg
     */
    public JsonResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 有数据返回时，人为指定状态码和提示信息
     * @param data
     * @param code
     * @param msg
     */
    public JsonResult(int code, String msg, T data) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    /**
     * 有数据返回时，人为指定状态码
     * @param data
     * @param code
     */
    public JsonResult(int code, T data) {
        this.data = data;
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
