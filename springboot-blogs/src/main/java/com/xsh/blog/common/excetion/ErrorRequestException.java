package com.xsh.blog.common.excetion;


import com.xsh.blog.common.bean.ApiCode;

/**
 * @program: StudentHeart
 * @description: 错误异常请求
 *
 **/
public class ErrorRequestException extends RuntimeException {
    private String msg;
    private Integer status;


    public String getMsg() {
        return msg;
    }

    public Integer getStatus() {
        return status;
    }
    public ErrorRequestException(ApiCode apiCode) {
        this.status = apiCode.getCode();
        this.msg = apiCode.getMessage();
    }
    public ErrorRequestException(ApiCode code, String msg) {
        this.status = code.getCode();
        this.msg = msg;
    }

    public ErrorRequestException(String msg) {
        this.status = 400;
        this.msg = msg;
    }





}