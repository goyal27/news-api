package com.sapient.news.context;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static com.sapient.news.context.NewsAPIContext.*;
import static com.sapient.news.util.Constant.TRACE_ID_HEADER;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class NewsAPIContextShould {
  @Mock private HttpServletRequest httpRequest;

  @Test
  public void returnNewTraceIdWhenItIsNotPresentInRequestHeader() {
    given(httpRequest.getHeader(TRACE_ID_HEADER)).willReturn(null);

    TraceId correlationId = traceIdFrom(httpRequest);

    assertNotNull(correlationId);
  }

  @Test
  public void returnTraceIdWithSameValueSpecifiedOnRequestHeader() {
    given(httpRequest.getHeader(TRACE_ID_HEADER)).willReturn("REQUEST_CORRELATION_ID_VALUE");

    TraceId correlationId = traceIdFrom(httpRequest);

    assertThat(correlationId.getValue(), is("REQUEST_CORRELATION_ID_VALUE"));
  }

  @Test
  public void setRequestTraceIdOnThreadLocal() {
    given(httpRequest.getHeader(TRACE_ID_HEADER)).willReturn("REQUEST_CORRELATION_ID_VALUE");

    TraceId correlationId = traceIdFrom(httpRequest);

    assertThat(correlationId, is(threadLocalTraceId.get()));
    assertThat(correlationId.getValue(), is("REQUEST_CORRELATION_ID_VALUE"));
  }

  @Test
  public void setTraceIdOnThreadLocalWhenOneDoesNotExist() {
    TraceId correlationId = traceId();

    assertThat(correlationId, is(threadLocalTraceId.get()));
  }

  @Test
  public void returnSameTraceIdWhenAlreadySet() {
    TraceId firstGenerationCorrelationId = traceId();
    TraceId secondGenerationCorrelationId = traceId();

    assertThat(firstGenerationCorrelationId, is(secondGenerationCorrelationId));
    assertThat(firstGenerationCorrelationId, is(threadLocalTraceId.get()));
  }

  @After
  public void tearDown() {
    removeTraceIdFromThreadLocal();
  }

  private void removeTraceIdFromThreadLocal() {
    threadLocalTraceId.remove();
  }
}
