package com.sapient.news.filter;

import com.sapient.news.context.NewsAPIContext;
import com.sapient.news.context.TraceId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.MDC;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.sapient.news.util.Constant.TRACE_ID_HEADER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TraceIdHeaderFilterShould {
  @Mock private FilterChain filterChain;
  private MockHttpServletRequest request;
  private HttpServletResponse response;

  private static final String TRACE_ID = "a-trace-id";

  private TraceIdHeaderFilter traceIdnHeaderFilter = new TraceIdHeaderFilter();

  @Before
  public void setUp() {
    request = MockMvcRequestBuilders.request(HttpMethod.GET, "/the/uri").buildRequest(null);
    request.addHeader(TRACE_ID_HEADER, TRACE_ID);

    response = new MockHttpServletResponse();

    NewsAPIContext.threadLocalTraceId.remove();
    MDC.remove(TRACE_ID_HEADER);
  }

  @Test
  public void extractTraceIdFromRequest() throws IOException, ServletException {
    traceIdnHeaderFilter.doFilter(request, response, filterChain);

    assertThat(new TraceId(TRACE_ID), is(NewsAPIContext.threadLocalTraceId.get()));
  }

  @Test
  public void setTraceIdInResponseHeader() throws IOException, ServletException {
    traceIdnHeaderFilter.doFilter(request, response, filterChain);

    assertThat(TRACE_ID, is(response.getHeader(TRACE_ID_HEADER)));
  }

  @Test
  public void setTraceIdInSlf4j() throws IOException, ServletException {
    doAnswer(
            invocation -> {
              assertThat(TRACE_ID, is(MDC.get(TRACE_ID_HEADER)));
              return null;
            })
        .when(filterChain)
        .doFilter(request, response);

    traceIdnHeaderFilter.doFilter(request, response, filterChain);
  }

  @Test
  public void callFilterChain() throws IOException, ServletException {
    traceIdnHeaderFilter.doFilter(request, response, filterChain);
    verify(filterChain).doFilter(request, response);
  }
}
