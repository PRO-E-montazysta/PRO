package org.tests.tool;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ToolControllerTests {
    String bearerToken= "eyJraWQiOiJhYjFhZjZmNC1hMTgyLTQzMTEtYjhkNi05MmQwNzU5ODcyYjgiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiYWRtaW4iLCJleHAiOjE2NzUyNzE1ODYsImlhdCI6MTY3NTI2Nzk4Niwic2NvcGUiOiJDTE9VRF9BRE1JTiJ9.MNkR0wffR004V45hXNQDp0aF6OMiH_cZT0lKs0iov502TkytArFYNb2cew0NQ7MOZWRWyDWEibUYHSb77Yf3vleLcm2MJ4ASlbNtg7C9ICrNBlualrRH-hBaNxLPJKCj08fgjpkN9-aqlzP90TJIyu2JP7oJUJIQD1eWTTJXyjLfV_Yh5wfNWybzQ4wC2udX-zBAJIr-R2Iizo1IpbFMdP7E6UbiVTwT9jJGZPvr068pNcUi4qGGbgwX-R2r9O1FBuJKrD6huxBBRxJFPEaiy_jKhCbFEAupSq1Jm0NfTk1HRNkTKnqwHW-95g0C1qL-w1rHVMbz56xWMbWX3SZZwg";


    @BeforeAll
    public void setUp() throws Exception {

    }
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
                    rootPath("[0]")
                        .body("name", equalTo("sadasd"))
                        .body("code", equalTo("PL"));

    }
}
