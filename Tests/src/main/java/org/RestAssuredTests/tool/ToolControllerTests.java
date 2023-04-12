package org.RestAssuredTests.tool;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.RestAssuredTests.util.AbstractTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ToolControllerTests extends AbstractTest {

    private static int toolId;
    private String bearerToken = TOKEN;

    private String toolCode = "";

    String requestBody =
            "{\n" +
                    "  \"name\": \"string\",\n" +
                    "  \"toolReleases\": [\n" +
                    "    1\n" +
                    "  ],\n" +
                    "  \"warehouseId\": 1,\n" +
                    "  \"toolEvents\": [\n" +
                    "    1\n" +
                    "  ],\n" +
                    "  \"toolTypeId\": 1\n" +
                    "}";
    @Test
    @Order(1)
    public void postToolTest(){

            Response response =
                    given()
                            .headers(
                                    "Authorization","Bearer " + TOKEN,
                                    "Content-Type", ContentType.JSON,
                                    "Accept", ContentType.JSON
                            )
                            .and()
                            .body(requestBody)
                            .when()
                            .post(BASE_PATH + "/tools");

        toolId = response.getBody().jsonPath().getInt("id");
        toolCode = response.getBody().jsonPath().getString("code");

            Assertions.assertEquals(HttpStatus.SC_CREATED, response.statusCode());



    }




    @Test
    @Order(2)
    public void getAllToolsTest() {
        Response response =
                     given()
                        .headers(
                                "Authorization","Bearer " + TOKEN,
                                "Content-Type",ContentType.JSON,
                                "Accept", ContentType.JSON)

                        .get(BASE_PATH + "/tools/all").
                        prettyPeek();

        Assertions.assertTrue(response.getBody().jsonPath().getList("$").stream()
                .anyMatch(toolId -> toolId.toString().contains("id="  + toolId)));


    }

    @Test
    @Order(3)
    public void getToolByCodeTests(){
        Response response = given()
                .headers(
                        "Authorization","Bearer " + TOKEN,
                        "Content-Type",ContentType.JSON,
                        "Accept", ContentType.JSON)

                .get(BASE_PATH + "/bycode/"+toolCode).
                prettyPeek().
                then().
                statusCode(HttpStatus.SC_OK)
                .extract().response();

        Assertions.assertTrue(response.getBody().jsonPath().getList("$").stream()
                .anyMatch(toolCode -> toolCode.toString().contains("code="  + toolCode)));


    }
    @Test
    @Order(4)
    public void deleteToolTest(){


            Response response = given()
                    .headers(
                            "Authorization","Bearer " + TOKEN,
                            "Content-Type", ContentType.JSON,
                            "Accept", ContentType.JSON
                    )
                    .and()
                    .delete(BASE_PATH+ "/tools/")
                    .then()
                    .extract().response();

            response.prettyPrint();
            Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
        }

    }

