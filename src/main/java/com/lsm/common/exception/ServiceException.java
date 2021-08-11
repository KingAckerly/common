package com.lsm.common.exception;

/**
 * 自定义业务异常
 */
public class ServiceException extends RuntimeException {

    private String code;

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public ServiceException setCode(String code) {
        this.code = code;
        return this;
    }
}
