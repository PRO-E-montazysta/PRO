package org.RestAssuredTests.warehouse;

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
public class WarehouseControllerTests extends AbstractTest{

    private String postBody = "{\n" +
            "  \"name\": \"Scaffolds\",\n" +
            "  \"description\": \"ciekawa\",\n" +
            "  \"openingHours\": \"08:00 - 20:00\",\n" +
            "  \"companyId\": null,\n" +
            "  \"locationId\": null,\n" +
            "  \"elementInWarehouses\": [\n" +
            "    0\n" +
            "  ],\n" +
            "  \"tools\": [\n" +
            "    0\n" +
            "  ]\n" +
            "}";


    private String putBody = "{\n" +
            "  \"name\": \"Screwdrivers\",\n" +
            "  \"description\": \"boring\",\n" +
            "  \"openingHours\": \"08:00 - 20:00\",\n" +
            "  \"companyId\": 1,\n" +
            "  \"locationId\": 1,\n" +
            "  \"elementInWarehouses\": [\n" +
            "    25\n" +
            "  ],\n" +
            "  \"tools\": [\n" +
            "    1\n" +
            "  ]\n" +
            "}";

    private String putBody1 = "{\n" +
            "  \"name\": \"Screwdrivers\",\n" +
            "  \"description\": \"boring\",\n" +
            "  \"openingHours\": \"08:00 - 20:00\",\n" +
            "  \"companyId\": 1,\n" +
            "  \"locationId\": 1,\n" +
            "  \"elementInWarehouses\": [\n" +
            "    25,99999\n" +
            "  ],\n" +
            "  \"tools\": [\n" +
            "    1,99999\n" +
            "  ]\n" +
            "}";

    @Test
    @Order(4)
    public void getAll() {

        Response response = given()
                .header("Authorization", "Bearer "+ AbstractTest.TOKEN)
                .contentType(ContentType.JSON)
                .when()
                .get(AbstractTest.BASE_PATH + "/warehouses/all")
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
                .post(AbstractTest.BASE_PATH + "/warehouses")
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
                .put(AbstractTest.BASE_PATH + "/warehouses/1")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    @Order(3)
    public void putNotExistingElementAndTool() {

        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer "+ AbstractTest.TOKEN)
                .and()
                .body(putBody1)
                .when()
                .put(AbstractTest.BASE_PATH + "/warehouses/1")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(404, response.statusCode());
    }

    @Test
    @Order(5)
    public void getById() {

        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer "+ AbstractTest.TOKEN)
                .and()
                .body(postBody)
                .when()
                .get(AbstractTest.BASE_PATH + "/warehouses/1")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Screwdrivers", response.getBody().jsonPath().getString("name"));
        Assertions.assertEquals("boring", response.jsonPath().getString("description"));
        Assertions.assertEquals("08:00 - 20:00", response.jsonPath().getString("openingHours"));
        Assertions.assertNull( response.jsonPath().getString("company"));
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
                .delete(AbstractTest.BASE_PATH + "/warehouses/1")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
    }
}