package org.tests.warehouse;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.tests.util.AbstractTest;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WarehouseControllerTests {

    private String postBody = "{\n" +
            "  \"name\": \"Scaffolds\",\n" +
            "  \"description\": \"ciekawa\",\n" +
            "  \"openingHours\": \"08:00 - 20:00\",\n" +
            "  \"company\": \"null\",\n" ;

    @Test
    @Order(2)
    public void getAll() {

        Response response = given()
                .header("Authorization", "Bearer "+ AbstractTest.TOKEN)
                .contentType(ContentType.JSON)
                .when()
                .get(AbstractTest.BASE_PATH + "/warehouse")
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
                .header("Authorization", "Bearer "+ AbstractTest.TOKEN)
                .and()
                .body(postBody)
                .when()
                .post(AbstractTest.BASE_PATH + "/warehouse")
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
                .header("Authorization", "Bearer "+ AbstractTest.TOKEN)
                .and()
                .body(postBody)
                .when()
                .get(AbstractTest.BASE_PATH + "/warehouse/2")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Scaffolds", response.getBody().jsonPath().getString("name"));
        Assertions.assertEquals("ciekawa", response.jsonPath().getString("description"));
        Assertions.assertEquals("08:00 - 20:00", response.jsonPath().getString("openingHours"));
        Assertions.assertEquals("null", response.jsonPath().getString("company"));
    }

    @Test
    @Order(4)
    public void delete() {
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer "+ AbstractTest.TOKEN)
                .and()
                .body(postBody)
                .when()
                .delete(AbstractTest.BASE_PATH + "/warehouse/2")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
    }
}
