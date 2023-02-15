package org.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;

import static io.restassured.RestAssured.given;
@Getter
public class SetUp {


    private static String bearerToken = "";
    private static String tokenBody = "{\n" +
            "  \"username\": \"admin\",\n" +
            "  \"password\": \"password\"\n" +
            "}";

    public static String getBearerToken() {
        Response response =  given()
                .headers(

                        "Content-Type", ContentType.JSON,
                        "Accept", ContentType.JSON
                )
                .and()
                .body(tokenBody)
                .when()
                .post("/api/v1/gettoken")
                .then()
                .extract().response();

        return bearerToken = response.jsonPath().getString("token");
    }

}
