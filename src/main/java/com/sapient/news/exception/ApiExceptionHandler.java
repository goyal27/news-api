package com.sapient.news.exception;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static com.sapient.news.exception.ErrorCode.*;
import static com.sapient.news.util.Constant.*;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
@Component
public class ApiExceptionHandler {
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorMessage> exceptionHandler(
      HttpServletRequest request, BadRequestException exception) {
    log.warn(RECEIVED_A_BAD_REQUEST_MESSAGE, exception);
    return ResponseEntity.badRequest()
        .body(
            ErrorMessage.builder()
                .code(
                    String.format(
                        EXCEPTION_ERROR_MESSAGE_FORMAT,
                        BAD_REQUEST.value(),
                        PROJECT_CODE,
                        BAD_REQUEST_ERROR.getCode()))
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .reference(MDC.get(TRACE_ID_HEADER))
                .build());
  }

  @ExceptionHandler(ServiceException.class)
  public ResponseEntity<ErrorMessage> exceptionHandler(
      HttpServletRequest request, ServiceException exception) {
    log.warn(INTERNAL_SERVER_ERROR_MESSAGE, exception);
    return ResponseEntity.status(INTERNAL_SERVER_ERROR)
        .body(
            ErrorMessage.builder()
                .code(
                    String.format(
                        EXCEPTION_ERROR_MESSAGE_FORMAT,
                        INTERNAL_SERVER_ERROR.value(),
                        PROJECT_CODE,
                        INTERNAL_SERVER_ERROR_CODE.getCode()))
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .reference(MDC.get(TRACE_ID_HEADER))
                .build());
  }

  @ExceptionHandler(RequestLimitReachedException.class)
  public ResponseEntity<ErrorMessage> exceptionHandler(
      HttpServletRequest request, RequestLimitReachedException exception) {
    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS_429)
        .body(
            ErrorMessage.builder()
                .code(
                    String.format(
                        EXCEPTION_ERROR_MESSAGE_FORMAT, TOO_MANY_REQUESTS.value(),
                        PROJECT_CODE, ERROR_REQUEST_LIMIT_REACHED.getCode()))
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .reference(MDC.get(TRACE_ID_HEADER))
                .build());
  }
}
