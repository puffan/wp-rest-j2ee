package com.common.exception;


import com.common.enums.ErrorCode;
import com.common.enums.HttpCode;

/**
 * 所有自定义异常使用此类
 */
public class BusinessException extends Exception {
    private ErrorCode errorCode;
    private HttpCode httpCode;

    public BusinessException(ErrorCode errorCode, HttpCode httpCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.httpCode = httpCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public HttpCode getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(HttpCode httpCode) {
        this.httpCode = httpCode;
    }

    @Override
    public String toString() {
        return "errorCode:" + this.errorCode + ",httpCode:" + this.httpCode + ",msg:" + getMessage();
    }
}
