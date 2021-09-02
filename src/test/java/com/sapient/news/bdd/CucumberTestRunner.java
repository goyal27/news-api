package com.sapient.news.bdd;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/bdd/features",plugin = {"pretty", "html:target/cucumber-html-report","json:target/cucumber.json"})
public class CucumberTestRunner {}