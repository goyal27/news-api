package com.sapient.news.config;

import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JettyServletWebServerFactoryConfiguration extends JettyServletWebServerFactory {

  public JettyServletWebServerFactoryConfiguration() {
    addServerCustomizers(new RequestLoggingServerCustomizer());
  }
}
