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
public class ElementInWarehouseTests extends AbstractTest{


    private String postBody = "{\n" +
            "  \"inWarehouseCount\": 1,\n" +
            "  \"inUnitCount\": 1,\n" +
            "  \"rack\": \"1\",\n" +
            "  \"shelf\": \"1\",\n" +
            "  \"elementId\": 1,\n" +
            "  \"warehouseId\": 1\n" +
            "}";

    private String postBodyExistingElementInExistingWarehouse = "{\n" +
            "  \"inWarehouseCount\": 2,\n" +
            "  \"inUnitCount\": 4,\n" +
            "  \"rack\": \"25\",\n" +
            "  \"shelf\": \"6\",\n" +
            "  \"elementId\": 1,\n" +
            "  \"warehouseId\": 1\n" +
            "}";

    private String postBodyNotExistingElementInExistingWarehouse = "{\n" +
            "  \"inWarehouseCount\": 2,\n" +
            "  \"inUnitCount\": 4,\n" +
            "  \"rack\": \"25\",\n" +
            "  \"shelf\": \"6\",\n" +
            "  \"elementId\": 99999999,\n" +
            "  \"warehouseId\": 1\n" +
            "}";

    private String postExistingElementInNotExistingWarehouse = "{\n" +
            "  \"inWarehouseCount\": 2,\n" +
            "  \"inUnitCount\": 4,\n" +
            "  \"rack\": \"25\",\n" +
            "  \"shelf\": \"6\",\n" +
            "  \"elementId\": 1,\n" +
            "  \"warehouseId\": 99999999\n" +
            "}";

    private String postNotExistingElementInNotExistingWarehouse = "{\n" +
            "  \"inWarehouseCount\": 2,\n" +
            "  \"inUnitCount\": 4,\n" +
            "  \"rack\": \"25\",\n" +
            "  \"shelf\": \"6\",\n" +
            "  \"elementId\": 1,\n" +
            "  \"warehouseId\": 99999999\n" +
            "}";

    @Test
    @Order(5)
    public void getAll() {

        Response response = given()
                .header("Authorization", "Bearer "+ AbstractTest.TOKEN)
                .contentType(ContentType.JSON)
                .when()
                .get(AbstractTest.BASE_PATH + "/elementInWarehouse/all")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertTrue(response.getBody().jsonPath().getList("$").size()>0);
    }

    @Test
    @Order(1)
    public void postExistingElementInExistingWarehouse(){
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer "+ AbstractTest.TOKEN)
                .and()
                .body(postBodyExistingElementInExistingWarehouse)
                .when()
                .post(AbstractTest.BASE_PATH + "/elementInWarehouse")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(201, response.statusCode());
    }



    @Test
    @Order(2)
    public void postNotExistingElementInExistingWarehouse() {
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer "+ AbstractTest.TOKEN)
                .and()
                .body(postBodyNotExistingElementInExistingWarehouse)
                .when()
                .post(AbstractTest.BASE_PATH + "/elementInWarehouse")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(500, response.statusCode());


    }

    @Test
    @Order(3)
    public void postExistingElementInNotExistingWarehouse(){
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer "+ AbstractTest.TOKEN)
                .and()
                .body(postExistingElementInNotExistingWarehouse)
                .when()
                .post(AbstractTest.BASE_PATH + "/elementInWarehouse")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(500, response.statusCode());

    }

    @Test
    @Order(4)
    public void postNotExistingElementInNotExistingWarehouse(){
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer "+ AbstractTest.TOKEN)
                .and()
                .body(postNotExistingElementInNotExistingWarehouse)
                .when()
                .post(AbstractTest.BASE_PATH + "/elementInWarehouse")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(500, response.statusCode());
    }


    @Test
    @Order(6)
    public void getById() {

        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer "+ AbstractTest.TOKEN)
                .and()
                .body(postBodyExistingElementInExistingWarehouse)
                .when()
                .get(AbstractTest.BASE_PATH + "/elementInWarehouse/25")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("1", response.getBody().jsonPath().getString("elementId"));
        Assertions.assertEquals("1", response.jsonPath().getString("warehouseId"));
        Assertions.assertEquals("1", response.jsonPath().getString("rack"));
        Assertions.assertEquals("1", response.jsonPath().getString("shelf"));
        Assertions.assertEquals("1", response.jsonPath().getString("inWarehouseCount"));
        Assertions.assertEquals("1", response.jsonPath().getString("inUnitCount"));
    }



    @Test
    @Order(7)
    public void delete() {
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer "+ AbstractTest.TOKEN)
                .and()
                .body(postBody)
                .when()
                .delete(AbstractTest.BASE_PATH + "/elementInWarehouse/25")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
    }
}
