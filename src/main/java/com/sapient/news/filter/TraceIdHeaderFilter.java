package com.sapient.news.filter;

import com.sapient.news.context.TraceId;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.sapient.news.context.NewsAPIContext.traceIdFrom;
import static com.sapient.news.util.Constant.TRACE_ID_HEADER;

public class TraceIdHeaderFilter implements Filter {

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {

    final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
    final HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

    TraceId traceId = traceIdFrom(httpRequest);

    httpResponse.setHeader(TRACE_ID_HEADER, traceId.getValue());

    MDC.put(TRACE_ID_HEADER, traceId.getValue());
    filterChain.doFilter(httpRequest, httpResponse);
  }
}
