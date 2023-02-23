package org.RestAssuredTests.tool;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.RestAssuredTests.util.AbstractTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
<<<<<<< HEAD:Tests/src/main/java/org/RestAssuredTests/tool/ToolControllerTests.java
=======
import org.tests.util.AbstractTest;
>>>>>>> master:Tests/src/main/java/org/tests/tool/ToolControllerTests.java

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ToolControllerTests extends AbstractTest {

    private static String names[] = new String[]{"screwdriver", "drill", "knife", "lopata", "dluto"};
<<<<<<< HEAD:Tests/src/main/java/org/RestAssuredTests/tool/ToolControllerTests.java
    private static int[] indexRangeForDeletion;

    private String bearerToken = TOKEN;

=======
    private static int[] indexRangeForDeletion = {1,2};
>>>>>>> master:Tests/src/main/java/org/tests/tool/ToolControllerTests.java

    private String toolCode = "";
    @Test
    @Order(1)
    public void postToolTest(){

        for (String s : names) {
            String requestBody ="" +
                    "{\n" +
                    "  \"name\": \""+s+"\"\n" +
                    "}";
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


            Assertions.assertEquals(HttpStatus.SC_CREATED, response.statusCode());
            System.out.println("wstawiono "+ s);
        }


    }

    @Test
    @Order(2)
    public void getAllToolsTest() {
        int i = 0;
        for (String toolName : names) {
            given()
                    .headers(
                            "Authorization","Bearer " + TOKEN,
                            "Content-Type",ContentType.JSON,
                            "Accept", ContentType.JSON)

                    .get(BASE_PATH + "/tools/all").
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

        //Assertions.assertEquals(1, response.getBody().jsonPath().getList("$").size());

    }
    @Test
    @Order(4)
    public void deleteToolTest(){

        for (int i = indexRangeForDeletion[0]; i < indexRangeForDeletion[1]; i++) {

            Response response = given()
                    .headers(
                            "Authorization","Bearer " + TOKEN,
                            "Content-Type", ContentType.JSON,
                            "Accept", ContentType.JSON
                    )
                    .and()
                    .delete(BASE_PATH+ "/tools/"+i)
                    .then()
                    .extract().response();

            response.prettyPrint();
            System.out.println("usunieto narzedzie o indeksie: "+i);
            Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
        }

    }
}
