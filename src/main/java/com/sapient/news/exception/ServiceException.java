package com.sapient.news.exception;

public class ServiceException extends RuntimeException {
    public ServiceException(ErrorCode errorCode, String message, Object... args) {
        super(errorCode.formatMessage(String.format(message, args)));
    }
}
