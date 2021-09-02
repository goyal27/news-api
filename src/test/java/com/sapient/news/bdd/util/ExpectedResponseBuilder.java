package com.sapient.news.bdd.util;

import com.jayway.restassured.response.Response;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ExpectedResponseBuilder {
    private String path;

    public static ExpectedResponseBuilder aExpectedResponse() {
        return new ExpectedResponseBuilder();
    }

    public void expectBadRequestContainingMessage(Response response, String message) {
        response.then()
                .body("message", containsString(message)); //
    }

    public void expectNotFoundWithMessage(Response response, String message) {
        response
                .then() //
                .statusCode(NOT_FOUND.value()) //
                .body("message", startsWith(message)) //
                .body("path", is(path));
    }

    public void expectedResponseCode(Response response, int responseCode) {
        if (response == null || response.getStatusCode() != responseCode) {
            throw new RuntimeException("Invalid response or responseCode");
        }
    }

    public void expectedResponseWithMessage(Response response, String message) {
        if (!response.getBody().asString().contains(message)) {
            throw new RuntimeException(String.format(" %s are not found in response", message));
        }
    }
}
