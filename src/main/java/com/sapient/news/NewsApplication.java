package com.sapient.news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

import static com.sapient.news.util.Constant.*;

@SpringBootApplication
public class NewsApplication {
  public static void main(String[] args) {
    System.setProperty(LOGGER_IMPL, LOG4J_LOGGER);
    TimeZone.setDefault(TimeZone.getTimeZone(UTC_TIME_FORMAT));
    SpringApplication.run(NewsApplication.class, args);
  }
}
