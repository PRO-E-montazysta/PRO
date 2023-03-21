package org.RestAssuredTests.tool;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;

import org.RestAssuredTests.util.AbstractTest;

public class ToolTypeControllerTests extends AbstractTest {


    private String postBody = "{\n" +
            "  \"name\": \"hammer\",\n" +

            "  \"criticalNumber\": 10,\n" +

            "  \"attachments\": [\n" +
            "    2\n" +
            "  ],\n" +
            "  \"orderStages\": [\n" +
            "    2\n" +
            "  ],\n" +
            "  \"tools\": [\n" +
            "    2\n" +
            "  ]\n" +
            "}";


    private String putBody = "{\n" +
            "  \"name\": \"screwdriver\",\n" +

            "  \"criticalNumber\": 10,\n" +

            "  \"attachments\": [\n" +
            "    2\n" +
            "  ],\n" +
            "  \"orderStages\": [\n" +
            "    2\n" +
            "  ],\n" +
            "  \"tools\": [\n" +
            "    2\n" +
            "  ]\n" +
            "}";

    @Test
    @Order(5)
    public void getAll() {

        Response response = given()
                .header("Authorization", "Bearer "+ AbstractTest.TOKEN)
                .contentType(ContentType.JSON)
                .when()
                .get(AbstractTest.BASE_PATH + "/tooltype/all")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertTrue(response.getBody().jsonPath().getList("$").size()>0);
    }

    @Test
    @Order(1)
    public void post() {

        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer "+ AbstractTest.TOKEN)
                .and()
                .body(postBody)
                .when()
                .post(AbstractTest.BASE_PATH + "/tooltype")
                .then()
                .extract().response();

        response.getBody().prettyPeek();


        Assertions.assertEquals(201, response.statusCode());
    }

    @Test
    @Order(2)
    public void put() {

        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer "+ AbstractTest.TOKEN)
                .and()
                .body(putBody)
                .when()
                .put(AbstractTest.BASE_PATH + "/tooltype/1")
                .then()
                .extract().response();

        response.getBody().prettyPeek();


        Assertions.assertEquals(200, response.statusCode());
    }



    @Test
    @Order(3)
    public void getById() {

        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer "+ AbstractTest.TOKEN)
                .and()
                .body(postBody)
                .when()
                .get(AbstractTest.BASE_PATH + "/tooltype/1")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());

    }

    @Test
    @Order(4)
    public void getByFilter() {

        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer "+ AbstractTest.TOKEN)
                .queryParam("name", "hammer")
                .and()
                .body(postBody)
                .when()
                .get(AbstractTest.BASE_PATH + "/tooltype/filter")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("hammer", response.getBody().jsonPath().getString("[0].name"));
        Assertions.assertEquals("10", response.jsonPath().getString("[0].criticalNumber"));
    }

    @Test
    @Order(6)
    public void delete() {

        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer "+ AbstractTest.TOKEN)
                .and()
                .body(postBody)
                .when()
                .delete(AbstractTest.BASE_PATH + "/tooltype/1" )
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
    }
}
