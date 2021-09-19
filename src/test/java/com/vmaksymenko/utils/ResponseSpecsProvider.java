package com.vmaksymenko.utils;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.vmaksymenko.data.Gist;
import com.vmaksymenko.data.GistFile;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import java.util.Map;

public class ResponseSpecsProvider {

  // TODO: Check if it can be improved with hasItems or allOf
  public static ResponseSpecification gistResponseSpec(Gist gist) {
    ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
        .expectBody("public", is(gist.getPublic()))
        .expectBody("description", is(gist.getDescription()));

    for (Map.Entry<String, GistFile> entry : gist.getFiles().entrySet()) {
      String filePath = "files['" + entry.getKey() + "']";
      responseSpecBuilder.expectBody(filePath, notNullValue())
          .expectBody(filePath + "['filename']", is(entry.getValue().getFilename()))
          .expectBody(filePath + "['type']", is(entry.getValue().getType()))
          .expectBody(filePath + "['language']", is(entry.getValue().getLanguage()));
    }

    return responseSpecBuilder.build();
  }
}
