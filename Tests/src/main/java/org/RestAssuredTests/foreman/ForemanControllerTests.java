package org.RestAssuredTests.foreman;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.RestAssuredTests.util.AbstractTest;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ForemanControllerTests extends AbstractTest {

    private String postBody = "{\n" +
            "  \"firstName\": \"Jan\",\n" +
            "  \"lastName\": \"Kowalski\",\n" +
            "  \"email\": \"kowalski@email.com\",\n" +
            "  \"password\": \"Password123\",\n" +
            "  \"username\": \"Kowal\",\n" +
            "  \"roles\": [\n" +
            "    \"FOREMAN\"\n" +
            "  ],\n" +
            "  \"phone\": \"+12897893989\",\n" +
            "  \"pesel\": \"123456789\"\n" +
            "}";

    @Test
    @Order(2)
    public void getAll() {

        Response response = given()
                .header("Authorization", "Bearer "+ TOKEN)
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_PATH + "/foremen")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(1, response.getBody().jsonPath().getList("$").size());
    }

    @Test
    @Order(1)
    public void post() {

        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer "+ TOKEN)
                .and()
                .body(postBody)
                .when()
                .post(BASE_PATH + "/foremen")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(201, response.statusCode());
    }

    @Test
    @Order(3)
    public void getById() {

        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer "+ TOKEN)
                .and()
                .body(postBody)
                .when()
                .get(BASE_PATH + "/foremen/2")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Jan", response.getBody().jsonPath().getString("firstName"));
        Assertions.assertEquals("Kowalski", response.jsonPath().getString("lastName"));
        Assertions.assertEquals("2", response.jsonPath().getString("id"));
        Assertions.assertEquals("kowalski@email.com", response.jsonPath().getString("email"));
        Assertions.assertEquals("Kowal", response.jsonPath().getString("username"));
        Assertions.assertEquals("+12897893989", response.jsonPath().getString("phone"));
        Assertions.assertEquals("123456789", response.jsonPath().getString("pesel"));
    }

    @Test
    @Order(4)
    public void delete() {
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer "+ TOKEN)
                .and()
                .body(postBody)
                .when()
                .delete(BASE_PATH + "/foremen/2")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
    }
}
