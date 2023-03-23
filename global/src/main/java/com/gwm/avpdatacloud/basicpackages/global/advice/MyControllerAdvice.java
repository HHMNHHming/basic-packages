package com.gwm.avpdatacloud.basicpackages.global.advice;

import com.gwm.avpdatacloud.basicpackages.log.GDCLog;
import com.gwm.avpdatacloud.basicpackages.utils.ResultData;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class MyControllerAdvice {
    /**
     * 请求方式不支持
     * @param e
     * @return
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResultData bizExceptionHandler(HttpRequestMethodNotSupportedException e){
        GDCLog.error(null,"com.gwm.avpdatacloud.basicpackages.global.advice.MyControllerAdvice.bizExceptionHandler()",e.getMessage());
        return ResultData.ERROR("不支持 '"+e.getMethod()+"' 请求");
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultData bizExceptionHandler(MissingServletRequestParameterException e){
        String message = e.getMessage();
        GDCLog.error(null,"com.gwm.avpdatacloud.basicpackages.global.advice.MyControllerAdvice.bizExceptionHandler()",e.getMessage());
        return ResultData.ERROR(message);
    }
    @ExceptionHandler(Exception.class)
    public ResultData bizExceptionHandler(Exception e){
        GDCLog.error(null,"com.gwm.avpdatacloud.basicpackages.global.advice.MyControllerAdvice.bizExceptionHandler()",e.getMessage());
        return ResultData.ERROR("服务器错误，请联系管理人员");
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultData validExceptionHandler(MethodArgumentNotValidException e){
        String defaultMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        GDCLog.error(null,"com.gwm.avpdatacloud.basicpackages.global.advice.MyControllerAdvice.validExceptionHandler1()",e.getMessage());
        return ResultData.ERROR(defaultMessage);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultData validExceptionHandler(ConstraintViolationException e){
        String defaultMessage = e.getMessage();
        GDCLog.error(null,"com.gwm.avpdatacloud.basicpackages.global.advice.MyControllerAdvice.validExceptionHandler2()",e.getMessage());
        return ResultData.ERROR(defaultMessage);
    }
}
