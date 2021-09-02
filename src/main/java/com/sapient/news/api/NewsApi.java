package com.sapient.news.api;

import com.sapient.news.exception.BadRequestException;
import com.sapient.news.exception.ErrorMessage;
import com.sapient.news.request.IntervalUnit;
import com.sapient.news.request.NewsRequest;
import com.sapient.news.response.NewsGroup;
import com.sapient.news.response.NewsResponse;
import com.sapient.news.service.NewsService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.sapient.news.exception.ErrorCode.ERROR_INVALID_INTERVAL;
import static com.sapient.news.exception.ErrorCode.ERROR_REQUEST_LIMIT_REACHED;
import static com.sapient.news.util.Constant.*;
import static org.eclipse.jetty.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

@RestController
@RequestMapping(value = NEWS_API)
@Slf4j
@ApiResponses(
    value = {
      @ApiResponse(code = BAD_REQUEST_400, message = "Bad Request", response = ErrorMessage.class),
      @ApiResponse(
          code = INTERNAL_SERVER_ERROR_500,
          message = "Internal Server Error",
          response = ErrorMessage.class),
      @ApiResponse(
          code = TOO_MANY_REQUESTS_429,
          message = "Too Many Requests",
          response = ErrorMessage.class),
      @ApiResponse(
          code = UNAUTHORIZED_401,
          message = "Un-Authorized",
          response = ErrorMessage.class),
      @ApiResponse(code = FORBIDDEN_403, message = "Forbidden", response = ErrorMessage.class),
      @ApiResponse(code = NOT_FOUND_404, message = "Not Found", response = ErrorMessage.class)
    })
public class NewsApi {
  @Autowired private NewsService newsService;

  @Value("${news.api.default.interval.unit:Hour}")
  private IntervalUnit defaultIntervalUnit;

  @Value("${news.api.default.interval:12}")
  private Integer defaultInterval;

  @GetMapping(value = "/everything")
  @RateLimiter(name = "getNewsRateLimit", fallbackMethod = "getNewsFallBack")
  public ResponseEntity<NewsResponse> getNews(
      @RequestParam @NonNull String keyword,
      @RequestParam(required = false) Integer interval,
      @RequestParam(required = false) IntervalUnit intervalUnit) {
    NewsRequest newsRequest =
        NewsRequest.builder()
            .interval(Objects.isNull(interval) ? defaultInterval : interval)
            .intervalUnit(Objects.isNull(intervalUnit) ? defaultIntervalUnit : intervalUnit)
            .keyword(keyword)
            .build();
    log.info("New Request {}", newsRequest);
    validate(interval, intervalUnit);
    List<NewsGroup> newsGroups = newsService.getNews(newsRequest);
    return ResponseEntity.ok().body(NewsResponse.builder().newsGroups(newsGroups).build());
  }

  private void validate(Integer interval, IntervalUnit intervalUnit) {
    if (Objects.isNull(interval) && Objects.isNull(intervalUnit)) return;
    if (Objects.isNull(interval) || Objects.isNull(intervalUnit)) {
      log.error("Received Bad Request, Bad interval data");
      throw new BadRequestException(ERROR_INVALID_INTERVAL, INVALID_INTERVAL);
    }
  }

  public ResponseEntity<ErrorMessage> getNewsFallBack(
      String keyword,
      Integer interval,
      IntervalUnit intervalUnit,
      io.github.resilience4j.ratelimiter.RequestNotPermitted ex) {
    log.info("Rate limit applied no further calls are accepted");

    HttpHeaders responseHeaders = new HttpHeaders();

    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
        .headers(responseHeaders) // send retry header
        .body(
            ErrorMessage.builder()
                .code(
                    String.format(
                        EXCEPTION_ERROR_MESSAGE_FORMAT, TOO_MANY_REQUESTS.value(),
                        PROJECT_CODE, ERROR_REQUEST_LIMIT_REACHED.getCode()))
                .timestamp(LocalDateTime.now())
                .message("Too many request - No further calls are accepted")
                .reference(MDC.get(TRACE_ID_HEADER))
                .build());
  }
}
