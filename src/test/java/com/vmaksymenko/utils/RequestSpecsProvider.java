package com.vmaksymenko.utils;

import static com.vmaksymenko.utils.EnvReader.getToken;
import static com.vmaksymenko.utils.EnvReader.getUser;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSpecsProvider {

  public static RequestSpecification getDefaultSpec() {
    return new RequestSpecBuilder()
        .addHeader("Accept", "application/vnd.github.v3+json")
        .build();
  }

  public static RequestSpecification getAuthenticatedSpec() {
    RequestSpecification authenticatedSpec = getDefaultSpec();
    return authenticatedSpec.auth().preemptive().basic(getUser(), getToken());
  }
}
