package com.vmaksymenko.stepdefinitions;

import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static org.hamcrest.Matchers.equalTo;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import net.serenitybdd.rest.SerenityRest;

public class CommonStepDefinitions {

  @Then("he should receive response with code {int}")
  public void heShouldReceiveResponseWithCode(int code) {
    restAssuredThat(response -> response.statusCode(code));
  }

  @And("response should have error message {string}")
  public void responseShouldHaveErrorMessageFiles(String message) {
    restAssuredThat(response -> response.body("message", equalTo(message)));
  }
}
