package org.tests.util;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public abstract class AbstractTest {

    public static final String TOKEN;
    public static final String BASE_PATH = "/api/v1";

    static {

        String body = "{\"username\": \"admin\", \"password\": \"password\"}";

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .post(BASE_PATH + "/gettoken")
                .then()
                .extract().response();

        TOKEN = response.getBody().jsonPath().getString("token");
    }
}
