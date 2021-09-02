package com.sapient.news.context;

import org.slf4j.MDC;

import javax.servlet.http.HttpServletRequest;

import static com.sapient.news.util.Constant.TRACE_ID_HEADER;

public class NewsAPIContext {
  private NewsAPIContext() {}

  public static final ThreadLocal<TraceId> threadLocalTraceId = new ThreadLocal<>();

  public static TraceId traceIdFrom(HttpServletRequest httpRequest) {
    TraceId traceId = getOrCreateTraceIdFrom(httpRequest);
    threadLocalTraceId.set(traceId);
    return traceId;
  }

  private static TraceId getOrCreateTraceIdFrom(HttpServletRequest httpRequest) {
    String traceIdValue = httpRequest.getHeader(TRACE_ID_HEADER);
    return traceIdValue == null ? new TraceId() : new TraceId(traceIdValue);
  }

  public static TraceId traceId() {
    TraceId traceId = threadLocalTraceId.get();
    if (traceId == null) {
      traceId = new TraceId();
      threadLocalTraceId.set(traceId);
    }

    MDC.put(TRACE_ID_HEADER, traceId.getValue());
    return traceId;
  }
}
