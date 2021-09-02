package com.sapient.news.bdd.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Value;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static com.sapient.news.bdd.util.ExpectedResponseBuilder.aExpectedResponse;

public class NewsAPIStepDefs extends BaseCucumberRunner {
  @Value("${local.server.port:8090}")
  private int port;

  @Value("${news.api.apiKey}")
  private String apiKey;

  @Given("The News API is up & running")
  public void the_news_api_is_up_running() {
    request = given();
    request.port(port);
  }

  @Then("http response code is {int} OK")
  public void http_response_code_is_OK(Integer responseCode) {
    aExpectedResponse().expectedResponseCode(response, responseCode);
  }

  @When("Requested news for keyword {string}")
  public void requestNewsWithKeyword(String keyword) {

    response =
        request
            .contentType(JSON)
            .queryParam("keyword", keyword)
            .get("/v1/news/everything");
  }
}
