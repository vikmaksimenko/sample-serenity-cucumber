package com.vmaksymenko.controllers;

import static com.vmaksymenko.utils.EnvReader.getUser;

import com.vmaksymenko.data.Gist;
import com.vmaksymenko.utils.RequestSpecsProvider;
import net.serenitybdd.rest.SerenityRest;

public class GistController {

  // TODO: Refactor to use actors
  // TODO: Find a way to set default URI
  public void createGist(Gist gist, boolean isAuthorised) {
    SerenityRest
        .given()
        .spec(isAuthorised
            ? RequestSpecsProvider.getAuthenticatedSpec()
            : RequestSpecsProvider.getDefaultSpec())
        .body(gist)
        .when()
        .post("https://api.github.com/gists");
  }


  public void deleteGist(String gistId, boolean isAuthorised) {
    SerenityRest
        .given()
        .spec(isAuthorised
            ? RequestSpecsProvider.getAuthenticatedSpec()
            : RequestSpecsProvider.getDefaultSpec())
        .when()
        .delete("https://api.github.com/gists/{gist_id}", gistId);
  }

  public void cleanupGists() {
    SerenityRest
        .given()
        .spec(RequestSpecsProvider.getAuthenticatedSpec())
        .param("per_page", "100")
        .when()
        .get(" https://api.github.com/users/{user}/gists", getUser())
        .then()
        .statusCode(200)
        .extract()
        .response()
        .jsonPath()
        .getList("id")
        .forEach(id -> deleteGist((String) id, true));
  }
}
