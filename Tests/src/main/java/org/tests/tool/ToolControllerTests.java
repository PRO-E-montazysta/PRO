package org.tests.tool;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class ToolControllerTests {
    String bearerToken = "eyJraWQiOiI3NTU0NDIwYy1hZDU0LTQ4ZWQtYmRiZi1mMWZiMTAyODUxMzYiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiYWRtaW4iLCJleHAiOjE2NzUxODY0NzUsImlhdCI6MTY3NTE4Mjg3NSwic2NvcGUiOiJDTE9VRF9BRE1JTiJ9.VE8TLMJ4qyOW_8Ue0jiCkCOprBdziMu4xsXhzRe6eU0V3RYwZ58PnHsBottAIKKsfIkitiTFZwRIZIgNWyQqH6eL4Em_Jm1QxB2R9s0iyV_d9gs4Zih-Ve4K1YD5wCnY2DYyXAmerQwk-0hQ9L_z2yLNq4s7yS1s5UmFRX4pAve5_xG5YdfwUIddq9DWAz7iXKOcVZU07eJP63D-Cnl2c09S0IYRiqFYSprcEqlOEK1WPiDK1YEz9hXhGNMAmIgNMNDtJhEEXJTmvWq6-RT9tIuT3YRiNpRXk8fg1xpR5w9Kw89VGa7gCP9plsSekn0CQbelfhNddKWlG0EWhZHJkQ\n";

    @Test
    public void test() {

        given()
                .headers(
                        "Authorization",
                        "Bearer " + bearerToken,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON).

        get("/api/v1/tools").
            prettyPeek().
            then().
                statusCode(200).
                    body("[0].name", equalTo("sadasd"));

    }
}
