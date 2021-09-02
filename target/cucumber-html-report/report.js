$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("file:src/test/resources/bdd/features/news.feature");
formatter.feature({
  "name": "News Feature",
  "description": "",
  "keyword": "Feature"
});
formatter.background({
  "name": "",
  "description": "",
  "keyword": "Background"
});
formatter.step({
  "name": "The News API is up \u0026 running",
  "keyword": "Given "
});
formatter.match({
  "location": "NewsAPIStepDefs.the_news_api_is_up_running()"
});
formatter.result({
  "status": "passed"
});
formatter.scenario({
  "name": "Get News from News API",
  "description": "",
  "keyword": "Scenario"
});
formatter.step({
  "name": "Requested news for keyword \"bitcoin\"",
  "keyword": "When "
});
formatter.match({
  "location": "NewsAPIStepDefs.requestNewsWithKeyword(String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "http response code is 200 OK",
  "keyword": "Then "
});
formatter.match({
  "location": "NewsAPIStepDefs.http_response_code_is_OK(Integer)"
});
formatter.result({
  "status": "passed"
});
});