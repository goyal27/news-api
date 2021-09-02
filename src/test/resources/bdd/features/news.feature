Feature: News Feature

  Background:
    Given The News API is up & running

  Scenario: Get News from News API
    When Requested news for keyword "bitcoin"
    Then http response code is 200 OK