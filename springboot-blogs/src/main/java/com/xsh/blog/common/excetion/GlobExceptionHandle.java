package com.xsh.blog.common.excetion;


import com.xsh.blog.common.bean.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @program: ScToken
 * @description: 全局异常处理
 * @author: Mr.Gui
 **/
@RestControllerAdvice
public class GlobExceptionHandle {
    private final Logger logger = LoggerFactory.getLogger(GlobExceptionHandle.class);

    /**
     * 统一失败异常处理
     * @return
     */
    @ExceptionHandler(ErrorRequestException.class)
    public Object handleErrorRequestException(ErrorRequestException e) {
        String message = e.getMsg();
        Integer status = e.getStatus();
        return ApiResult.fail(status,message);
    }

    /**
     * 统一请求方式异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object handleException(HttpRequestMethodNotSupportedException e){
        return ApiResult.fail("请求方式:" + e.getMethod() + ",不支持");
    }

    @ResponseBody
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Object handleException(DataIntegrityViolationException e){
        return ApiResult.fail( e.getMessage());
    }


    @ResponseBody
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Object handleException(SQLIntegrityConstraintViolationException e){
        return ApiResult.fail( e.getMessage());
    }


}
