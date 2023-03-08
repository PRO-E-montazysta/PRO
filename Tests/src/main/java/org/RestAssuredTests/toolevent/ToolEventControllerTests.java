package org.RestAssuredTests.toolevent;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.RestAssuredTests.util.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ToolEventControllerTests extends AbstractTest {

    private static int toolEventId;

    private final String postBody = "{\n" +
            "  \"eventDate\": \"2023-02-28T17:04:52.112Z\",\n" +
            "  \"movingDate\": \"2023-02-28T17:04:52.112Z\",\n" +
            "  \"completionDate\": \"2023-02-28T17:04:52.112Z\",\n" +
            "  \"description\": \"Test description 1\",\n" +
            "  \"status\": \"PLANNED\",\n" +
            "  \"updatedById\": null,\n" +
            "  \"acceptedById\": null,\n" +
            "  \"toolId\": null,\n" +
            "  \"attachments\": []\n" +
            "}";
    private final String putBody = "{\n" +
            "  \"id\": " + toolEventId + ",\n" +
            "  \"eventDate\": \"2023-02-28T17:04:52.112Z\",\n" +
            "  \"movingDate\": \"2023-02-28T17:04:52.112Z\",\n" +
            "  \"completionDate\": \"2023-02-28T17:04:52.112Z\",\n" +
            "  \"description\": \"Test description 222\",\n" +
            "  \"status\": \"FINISHED\",\n" +
            "  \"updatedById\": null,\n" +
            "  \"acceptedById\": null,\n" +
            "  \"toolId\": null,\n" +
            "  \"attachments\": []\n" +
            "}";

    @Test
    @Order(1)
    void post() {
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer "+ TOKEN)
                .and()
                .body(postBody)
                .when()
                .post(BASE_PATH + "/toolEvent")
                .then()
                .extract().response();

        response.getBody().prettyPeek();
        toolEventId = response.getBody().jsonPath().getInt("id");

        Assertions.assertEquals(201, response.statusCode());
    }

    @Test
    @Order(2)
    void getAll() {
        Response response = given()
                .header("Authorization", "Bearer "+ AbstractTest.TOKEN)
                .contentType(ContentType.JSON)
                .when()
                .get(AbstractTest.BASE_PATH + "/toolEvent/all")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertTrue(response.getBody().jsonPath().getList("$").stream()
                        .anyMatch(toolEvent -> toolEvent.toString().contains("id="  + toolEventId)));
    }

//    @Test
//    @Order(3)
//    void put() {
//        Response response = given()
//                .header("Content-type", "application/json")
//                .header("Authorization", "Bearer "+ AbstractTest.TOKEN)
//                .and()
//                .body(putBody)
//                .when()
//                .put(AbstractTest.BASE_PATH + "/toolEvent/" + toolEventId)
//                .then()
//                .extract().response();
//
//        response.getBody().prettyPeek();
//
//        Assertions.assertEquals(200, response.statusCode());
//        Assertions.assertEquals("Test description 222", response.getBody().jsonPath().getString("description"));
//        Assertions.assertEquals("FINISHED", response.jsonPath().getString("status"));
//    }

    @Test
    @Order(4)
    void getById() {
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer "+ TOKEN)
                .and()
                .body(postBody)
                .when()
                .get(BASE_PATH + "/toolEvent/" + toolEventId)
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("PLANNED", response.getBody().jsonPath().getString("status"));
        Assertions.assertNull(response.jsonPath().getString("updatedById"));
        Assertions.assertNull(response.jsonPath().getString("updatedById"));
        Assertions.assertNull(response.jsonPath().getString("toolId"));
    }

    @Test
    @Order(5)
    void delete() {
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer "+ TOKEN)
                .and()
                .body(postBody)
                .when()
                .delete(BASE_PATH + "/toolEvent/" + toolEventId)
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    @Order(6)
    void getAll_noDeletedId() {
        Response response = given()
                .header("Authorization", "Bearer "+ TOKEN)
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_PATH + "/toolEvent/all")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertTrue(response.getBody().jsonPath().getList("$").stream()
                .noneMatch(toolEvent -> toolEvent.toString().contains("id="  + toolEventId)));
    }
}
