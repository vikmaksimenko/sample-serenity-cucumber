package com.vmaksymenko.stepdefinitions;

import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static org.hamcrest.Matchers.notNullValue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmaksymenko.controllers.GistController;
import com.vmaksymenko.data.Gist;
import com.vmaksymenko.data.GistFile;
import com.vmaksymenko.utils.ResponseSpecsProvider;
import io.cucumber.java.After;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.thucydides.core.annotations.Steps;

public class GistStepDefinitions {

  @ParameterType("private|public")
  public boolean isPublic(String isPublic) {
    return isPublic.equals("private");
  }

  @Steps
  GistController gistController;

  @When("unauthorised user attempts to create a {isPublic} gist {string} with files:")
  public void unauthorisedUserAttemptsToCreateAGistWithFiles(boolean isPublic,
      String description,
      List<Map<String, String>> gistFilesData) {

    Map<String, GistFile> gistFiles = this.prepareGistFiles(gistFilesData);
    gistController.createGist(new Gist(description, isPublic, gistFiles), false);
  }

  @When("Authorised user attempts to create a {isPublic} gist {string} with files:")
  public void authorisedUserAttemptsToCreateAGistWithFiles(boolean isPublic,
      String description,
      List<Map<String, String>> gistFilesData) {
    Map<String, GistFile> gistFiles = this.prepareGistFiles(gistFilesData);
    gistController.createGist(new Gist(description, isPublic, gistFiles), true);
  }

  @When("Authorised user attempts to create a {isPublic} gist {string} without files:")
  public void authorisedUserAttemptsToCreateAGistWithoutFiles(boolean isPublic,
      String description) {
    gistController.createGist(new Gist(description, isPublic, new HashMap<>()), true);
  }

  @And("create a {isPublic} gist {string} response should have files:")
  public void createAGistResponseShouldHaveFiles(boolean isPublic,
      String description,
      List<Map<String, String>> gistFilesData) {

    Map<String, GistFile> gistFiles = this.prepareGistFiles(gistFilesData);
    Gist gist = new Gist(description, isPublic, gistFiles);

    restAssuredThat(response -> {
          response.body("id", notNullValue())
              .and().spec(ResponseSpecsProvider.gistResponseSpec(gist));
        }
    );
  }

  @After
  public void cleanupGists() {
    gistController.cleanupGists();
  }

  private Map<String, GistFile> prepareGistFiles(List<Map<String, String>> gistFilesData) {
    return gistFilesData.stream()
        .map(aMap -> new ObjectMapper().convertValue(aMap, GistFile.class))
        .collect(Collectors.toMap(GistFile::getFilename, item -> item));
  }
}
