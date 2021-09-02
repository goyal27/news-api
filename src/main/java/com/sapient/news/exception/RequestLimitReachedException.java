package com.sapient.news.exception;

public class RequestLimitReachedException extends RuntimeException {
    public RequestLimitReachedException(ErrorCode errorCode, String message, Object... args) {
        super(errorCode.formatMessage(String.format(message, args)));
    }
}
