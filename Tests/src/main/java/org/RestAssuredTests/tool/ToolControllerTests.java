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

    private static String names[] = new String[]{"screwdriver", "drill", "knife", "lopata", "dluto"};
    private static int[] indexRangeForDeletion;

    private String bearerToken = TOKEN;


    @Test
    @Order(1)
    public void postTool(){

        for (String s : names) {
            String requestBody ="" +
                    "{\n" +
                    "  \"name\": \""+s+"\"\n" +
                    "}";
            Response response =
                    given()
                            .headers(
                                    "Authorization","Bearer " + bearerToken,
                                    "Content-Type", ContentType.JSON,
                                    "Accept", ContentType.JSON
                            )
                            .and()
                            .body(requestBody)
                            .when()
                            .post("/api/v1/tools");


            Assertions.assertEquals(HttpStatus.SC_CREATED, response.statusCode());
            System.out.println("wstawiono "+ s);
        }


    }

    @Test
    @Order(2)
    public void checkIfToolIsInDB() {
        int i = 0;
        for (String toolName : names) {
            given()
                    .headers(
                            "Authorization","Bearer " + bearerToken,
                            "Content-Type",ContentType.JSON,
                            "Accept", ContentType.JSON)

                    .get("/api/v1/tools/all").
                    prettyPeek().
                    then().
                    statusCode(HttpStatus.SC_OK).
                    rootPath("["+i+"]")
                    .body("name", equalTo(toolName))
                    .body("code", matchesPattern("^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$"));

                    i++;
        }

    }

    @Test
    @Order(3)
    public void deleteToolTest(){

        for (int i = indexRangeForDeletion[0]; i < indexRangeForDeletion[1]; i++) {

            Response response = given()
                    .headers(
                            "Authorization","Bearer " + bearerToken,
                            "Content-Type", ContentType.JSON,
                            "Accept", ContentType.JSON
                    )
                    .and()
                    .delete("/api/v1/tools/"+i)
                    .then()
                    .extract().response();

            response.prettyPrint();
            System.out.println("usunieto narzedzie o indeksie: "+i);
            Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
        }

    }
}
