package com.sapient.news.bdd.steps;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.sapient.news.NewsApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = {NewsApplication.class})
@ContextConfiguration(
    classes = {NewsApplication.class},
    loader = SpringBootContextLoader.class)
public class BaseCucumberRunner {
  protected RequestSpecification request;
  protected Response response;

  @Value("${local.server.port:8080}")
  private int port;
}
