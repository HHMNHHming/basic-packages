package com.gwm.avpdatacloud.basicpackages.utils;

import java.io.Serializable;

/**
 *
 * 响应数据统一封装
 *
 */
public class ResultData<T> implements Serializable {
    public static enum Code{
        SUCCESS(200),ERROR(500),FAILL(400),Unauthorized(401);
        private int code;
        private Code(int code){
            this.code = code;
        }
    }
    private int code;
    private String msg;
    private T obj;

    public ResultData(){

    }
    public ResultData(int code, String msg, T obj){
        this.code = code;
        this.msg = msg;
        this.obj = obj;
    }
    public ResultData(Code code, String msg, T obj){
        this.code = code.code;
        this.msg = msg;
        this.obj = obj;
    }
    public ResultData(Code code, String msg){
        this(code,msg,null);
    }
    public ResultData(int code, String msg){
        this(code,msg,null);
    }

    public static ResultData SUCCESS(String msg,Object obj){
        return new ResultData(Code.SUCCESS,msg,obj);
    }
    public static ResultData SUCCESS(String msg){
        return new ResultData(Code.SUCCESS,msg);
    }

    public static ResultData FAILL(String msg,Object obj){
        return new ResultData(Code.FAILL,msg,obj);
    }
    public static ResultData FAILL(String msg){
        return new ResultData(Code.FAILL,msg);
    }
    public static ResultData ERROR(String msg,Object obj){
        return new ResultData(Code.ERROR,msg,obj);
    }
    public static ResultData ERROR(String msg){
        return new ResultData(Code.ERROR,msg);
    }

    public static ResultData Unauthorized(){
        return new ResultData(Code.Unauthorized,"请求需要用户的身份认证");
    }

    public int getCode() {
        return code;
    }

    public ResultData setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResultData setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getObj() {
        return obj;
    }

    public ResultData setObj(T obj) {
        this.obj = obj;
        return this;
    }
}
