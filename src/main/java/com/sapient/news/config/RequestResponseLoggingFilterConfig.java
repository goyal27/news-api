package com.sapient.news.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import static com.sapient.news.util.Constant.REQUEST_DATA;
import static com.sapient.news.util.Constant.REQUEST_MAX_PAY_LOAD_LENGTH;

@Configuration
@ConditionalOnExpression("${log.request_response.enabled:true}")
public class RequestResponseLoggingFilterConfig {
  @Bean
  public CommonsRequestLoggingFilter logFilter() {
    CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
    filter.setIncludeQueryString(true);
    filter.setIncludePayload(true);
    filter.setMaxPayloadLength(REQUEST_MAX_PAY_LOAD_LENGTH);
    filter.setIncludeHeaders(false);
    filter.setAfterMessagePrefix(REQUEST_DATA);
    return filter;
  }
}
