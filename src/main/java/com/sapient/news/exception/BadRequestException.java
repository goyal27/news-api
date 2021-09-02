package com.sapient.news.exception;

public class BadRequestException extends RuntimeException {
  public BadRequestException(ErrorCode errorCode, String message, Object... args) {
    super(errorCode.formatMessage(String.format(message, args)));
  }
}
