package com.lsm.common.exception;

import com.lsm.common.base.Result;
import com.lsm.common.base.ReturnResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 系统异常
     *
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Result handlerException(HttpServletRequest request, Exception exception) {
        String errorMsg = exception.getMessage();
        exception.printStackTrace();
        return ReturnResponse.assembleRespError(errorMsg);
    }

    /**
     * 自定义业务异常
     *
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler(value = ServiceException.class)
    public Result handlerServiceException(HttpServletRequest request, Exception exception) {
        String errorMsg = exception.getMessage();
        exception.printStackTrace();
        return ReturnResponse.assembleRespError(errorMsg);
    }
}
