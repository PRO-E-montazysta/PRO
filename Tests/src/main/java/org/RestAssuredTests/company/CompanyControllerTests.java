package org.RestAssuredTests.company;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.RestAssuredTests.util.AbstractTest;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class CompanyControllerTests extends AbstractTest {

    private static int companyId;
    private String status = "DISABLED";


    private String postBody = "{\n" +
            "  \"companyName\": \"Company Test\",\n" +
            "  \"createdAt\": \"2023-03-24\",\n" +
            "  \"status\": \"ACTIVE\",\n" +
            "  \"statusReason\": \"string\",\n" +
            "  \"warehouses\": [\n" +
            "    \n" +
            "  ],\n" +
            "  \"orders\": [\n" +
            "    \n" +
            "  ],\n" +
            "  \"clients\": [\n" +
            "    \n" +
            "  ],\n" +
            "  \"employments\": [\n" +
            "    \n" +
            "  ]\n" +
            "}";


    private String putBody = "{\n" +
            "  \"id\": " + companyId + ",\n" +
            "  \"companyName\": \"Company Test\",\n" +
            "  \"createdAt\": \"2023-03-24\",\n" +
            "  \"status\": \"" + status + "\",\n" +
            "  \"statusReason\": \"string\",\n" +
            "  \"warehouses\": [\n" +
            "    \n" +
            "  ],\n" +
            "  \"orders\": [\n" +
            "    \n" +
            "  ],\n" +
            "  \"clients\": [\n" +
            "    \n" +
            "  ],\n" +
            "  \"employments\": [\n" +
            "    \n" +
            "  ]\n" +
            "}";

    @Test
    @Order(1)
    public void post() {
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + AbstractTest.TOKEN)
                .and()
                .body(postBody)
                .when()
                .post(AbstractTest.BASE_PATH + "/companies")
                .then()
                .extract().response();

        response.getBody().prettyPeek();
        companyId = response.getBody().jsonPath().getInt("id");

        Assertions.assertEquals(201, response.statusCode());

    }

    @Test
    @Order(2)
    void getAll() {
        Response response = given()
                .header("Authorization", "Bearer " + AbstractTest.TOKEN)
                .contentType(ContentType.JSON)
                .when()
                .get(AbstractTest.BASE_PATH + "/companies/all")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertTrue(response.getBody().jsonPath().getList("$").stream()
                .anyMatch(company -> company.toString().contains("id=" + companyId)));
    }

    @Test
    @Order(3)
    void changeStatus() {
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + AbstractTest.TOKEN)
                .and()
                .body(putBody)
                .when()
                .put(AbstractTest.BASE_PATH + "/companies/" + companyId)
                .then()
                .extract().response();

        response.getBody().prettyPeek();
        System.out.println(putBody);
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(status, response.getBody().jsonPath().getString("status"));

    }

    @Test
    @Order(4)
    void getById() {
        Response response = given()
                .header("Authorization", "Bearer " + AbstractTest.TOKEN)
                .contentType(ContentType.JSON)
                .when()
                .get(AbstractTest.BASE_PATH + "/companies/" + companyId)
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(companyId, response.getBody().jsonPath().getInt("id"));

    }

    @Test
    @Order(5)
    void getByFilterStatus() {
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + AbstractTest.TOKEN)
                .when()
                .get(AbstractTest.BASE_PATH + "/companies/filter?status=" + status)
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertTrue(response.getBody().jsonPath().getList("$").stream()
                .anyMatch(company -> company.toString().contains("id=" + companyId)));
    }

    @Test
    @Order(7)
    void delete() {
        Response response = given()
                .header("Authorization", "Bearer " + AbstractTest.TOKEN)
                .contentType(ContentType.JSON)
                .when()
                .delete(AbstractTest.BASE_PATH + "/companies/" + companyId)
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    @Order(8)
    void getAll_noDeletedId() {
        Response response = given()
                .header("Authorization", "Bearer " + TOKEN)
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_PATH + "/companies/all")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertTrue(response.getBody().jsonPath().getList("$").stream()
                .noneMatch(client -> client.toString().contains("id=" + companyId)));
    }

    @Test
    @Order(9)
    void delete_noExistClient() {
        Response response = given()
                .header("Authorization", "Bearer " + AbstractTest.TOKEN)
                .contentType(ContentType.JSON)
                .when()
                .delete(AbstractTest.BASE_PATH + "/companies/" + companyId)
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(404, response.statusCode());
    }

    @Test
    @Order(10)
    void getById_noExist() {
        Response response = given()
                .header("Authorization", "Bearer " + AbstractTest.TOKEN)
                .contentType(ContentType.JSON)
                .when()
                .get(AbstractTest.BASE_PATH + "/companies/" + companyId)
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(404, response.statusCode());

    }

    @Test
    @Order(11)
    void changeContactDetails_noExist() {
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + AbstractTest.TOKEN)
                .and()
                .body(putBody)
                .when()
                .put(AbstractTest.BASE_PATH + "/companies/" + companyId)
                .then()
                .extract().response();

        response.getBody().prettyPeek();
        System.out.println(putBody);
        Assertions.assertEquals(404, response.statusCode());
    }
}
