package org.RestAssuredTests.fitter;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.RestAssuredTests.util.AbstractTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FitterControllerTests extends AbstractTest {

    private final String postBody = "{\n" +
            "  \"firstName\": \"Karol\",\n" +
            "  \"lastName\": \"Sobotka\",\n" +
            "  \"username\": \"karsob\",\n" +
            "  \"email\": \"karsob@gmail.com\",\n" +
            "  \"phone\": \"123123123\",\n" +
            "  \"pesel\": \"99090167593\",\n" +
            "}";

    @Test
    @Order(2)
    public void getAll() {

        Response response = given()
                .header("Authorization", "Bearer "+ TOKEN)
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_PATH + "/fitters/all")
                .then()
                .extract().response();

        response.getBody().prettyPeek();
        Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
        //Assertions.assertEquals(5, response.getBody().jsonPath().getList("$").size());
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
                .post(BASE_PATH + "/fitters")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(201, response.statusCode());
        System.out.println(response.getBody());
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
                .get(BASE_PATH + "/fitters/2")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Test Fitter 1", response.getBody().jsonPath().getString("firstName"));
        Assertions.assertEquals("Test Fitter 1", response.jsonPath().getString("lastName"));
        Assertions.assertEquals("2", response.jsonPath().getString("id"));
        Assertions.assertEquals("fitter1@ema.il", response.jsonPath().getString("email"));
        Assertions.assertEquals("fitter1", response.jsonPath().getString("username"));
        Assertions.assertEquals("fitter1Phone", response.jsonPath().getString("phone"));
        Assertions.assertEquals("fitter1Pesel", response.jsonPath().getString("pesel"));
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
                .delete(BASE_PATH + "/fitters/2")
                .then()
                .extract().response();

        response.getBody().prettyPeek();
        //Assertions.assertEquals(200, response.statusCode());
    }

}
