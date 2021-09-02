package com.sapient.news.config;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Slf4jRequestLog;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;

import static com.sapient.news.util.Constant.UTC_TIME_FORMAT;

public class RequestLoggingServerCustomizer implements JettyServerCustomizer {
  @Override
  public void customize(Server server) {
    HandlerCollection handlers = new HandlerCollection();
    for (Handler handler : server.getHandlers()) {
      handlers.addHandler(handler);
    }
    RequestLogHandler requestLogHandler = new RequestLogHandler();
    Slf4jRequestLog requestLog = new Slf4jRequestLog();
    requestLog.setExtended(true);
    requestLog.setLogLatency(true);
    requestLog.setLogTimeZone(UTC_TIME_FORMAT);
    requestLogHandler.setRequestLog(requestLog);
    handlers.addHandler(requestLogHandler);
    server.setHandler(handlers);
  }
}
