package org.RestAssuredTests.client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.RestAssuredTests.util.AbstractTest;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class ClientControllerTests extends AbstractTest {

    private static int clientId;
    private String contactDetails = "nowy@test.pl";


    private String postBody = "{\n" +
            "  \"name\": \"Test Client for company 1\",\n" +
            "  \"contactDetails\": \"test@test.pl\",\n" +
            "  \"companyId\": 1,\n" +
            "  \"orders\": [\n" +
            "    \n" +
            "  ]\n" +
            "}";


    private String putBody = "{\n" +
            "  \"id\": " + clientId + ",\n" +
            "  \"name\": \"Test Client for company 1\",\n" +
            "  \"contactDetails\": \"" + contactDetails + "\",\n" +
            "  \"companyId\": 1,\n" +
            "  \"orders\": [\n" +
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
                .post(AbstractTest.BASE_PATH + "/clients")
                .then()
                .extract().response();

        response.getBody().prettyPeek();
        clientId = response.getBody().jsonPath().getInt("id");

        Assertions.assertEquals(201, response.statusCode());

    }

    @Test
    @Order(2)
    void getAll() {
        Response response = given()
                .header("Authorization", "Bearer " + AbstractTest.TOKEN)
                .contentType(ContentType.JSON)
                .when()
                .get(AbstractTest.BASE_PATH + "/clients/all")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertTrue(response.getBody().jsonPath().getList("$").stream()
                .anyMatch(client -> client.toString().contains("id=" + clientId)));
    }

    @Test
    @Order(3)
    void changeContactDetails() {
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + AbstractTest.TOKEN)
                .and()
                .body(putBody)
                .when()
                .put(AbstractTest.BASE_PATH + "/clients/" + clientId)
                .then()
                .extract().response();

        response.getBody().prettyPeek();
        System.out.println(putBody);
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(contactDetails, response.getBody().jsonPath().getString("contactDetails"));

    }

    @Test
    @Order(4)
    void getById() {
        Response response = given()
                .header("Authorization", "Bearer " + AbstractTest.TOKEN)
                .contentType(ContentType.JSON)
                .when()
                .get(AbstractTest.BASE_PATH + "/clients/" + clientId)
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(clientId, response.getBody().jsonPath().getInt("id"));

    }

    @Test
    @Order(5)
    void getByFilterContactDetails() {
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + AbstractTest.TOKEN)
                .when()
                .get(AbstractTest.BASE_PATH + "/clients/filter?contactDetails="+contactDetails)
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertTrue(response.getBody().jsonPath().getList("$").stream()
                .anyMatch(client -> client.toString().contains("id=" + clientId)));
    }

    @Test
    @Order(5)
    void getByFilterName() {
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + AbstractTest.TOKEN)
                .when()
                .get(AbstractTest.BASE_PATH + "/clients/filter?name=Test Client for company 1")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertTrue(response.getBody().jsonPath().getList("$").stream()
                .anyMatch(client -> client.toString().contains("id=" + clientId)));
    }

    @Test
    @Order(7)
    void delete() {
        Response response = given()
                .header("Authorization", "Bearer " + AbstractTest.TOKEN)
                .contentType(ContentType.JSON)
                .when()
                .delete(AbstractTest.BASE_PATH + "/clients/" + clientId)
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
                .get(BASE_PATH + "/clients/all")
                .then()
                .extract().response();

        response.getBody().prettyPeek();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertTrue(response.getBody().jsonPath().getList("$").stream()
                .noneMatch(client -> client.toString().contains("id=" + clientId)));
    }

    @Test
    @Order(9)
    void delete_noExistClient() {
        Response response = given()
                .header("Authorization", "Bearer " + AbstractTest.TOKEN)
                .contentType(ContentType.JSON)
                .when()
                .delete(AbstractTest.BASE_PATH + "/clients/" + clientId)
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
                .get(AbstractTest.BASE_PATH + "/clients/" + clientId)
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
                .put(AbstractTest.BASE_PATH + "/clients/" + clientId)
                .then()
                .extract().response();

        response.getBody().prettyPeek();
        System.out.println(putBody);
        Assertions.assertEquals(404, response.statusCode());
    }
}
