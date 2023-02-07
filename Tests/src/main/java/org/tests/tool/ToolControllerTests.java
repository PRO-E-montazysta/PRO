package org.tests.tool;

import com.emontazysta.configuration.SecurityProperties;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.tests.SetUp;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ToolControllerTests {

    String requestBody ="{\n" +
            "  \"name\": \"costam\"\n" +
            "}";

    @Test
    public void checkIfToolIsInDB() {


        given()
                .headers(
                        "Authorization","Bearer " + SetUp.getBearerToken(),
                        "Content-Type",ContentType.JSON,
                        "Accept", ContentType.JSON)

        .get("/api/v1/tools/all").
            prettyPeek().
            then().
               statusCode(HttpStatus.SC_OK).
                    rootPath("[0]")
                        .body("name", equalTo("costam"));
//                        .body("code", equalTo("PL"));


    }

    @Test
    public void postTool(){
       Response response =  given()
                .headers(
                        "Authorization","Bearer " + SetUp.getBearerToken(),
                        "Content-Type", ContentType.JSON,
                        "Accept", ContentType.JSON
                        )
            .and()
                .body(requestBody)
                .when()
                .post("/api/v1/tools")
                .then()
                .extract().response();



//        Assertions.assertEquals(201, response.statusCode());
//        Assertions.assertEquals("śrubokręt", response.jsonPath().getString("name"));

    }
}
