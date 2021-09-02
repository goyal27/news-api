package com.sapient.news.config;

import com.sapient.news.filter.TraceIdHeaderFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.util.Collections.singletonList;

@Configuration
public class HeaderConfiguration {
  private static final List<String> ALL_URLS = singletonList("/*");

  @Bean
  public FilterRegistrationBean<TraceIdHeaderFilter> traceIdHeaderFilter() {
    FilterRegistrationBean<TraceIdHeaderFilter> filterRegistrationBean =
        new FilterRegistrationBean<>();
    filterRegistrationBean.setOrder(0);
    filterRegistrationBean.setFilter(new TraceIdHeaderFilter());
    filterRegistrationBean.setUrlPatterns(ALL_URLS);
    return filterRegistrationBean;
  }
}
