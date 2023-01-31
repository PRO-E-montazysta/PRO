package org.tests.tool;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.testng.annotations.BeforeTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ToolControllerTests {
    String bearerToken= "eyJraWQiOiI3NTU0NDIwYy1hZDU0LTQ4ZWQtYmRiZi1mMWZiMTAyODUxMzYiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiYWRtaW4iLCJleHAiOjE2NzUxOTAzOTEsImlhdCI6MTY3NTE4Njc5MSwic2NvcGUiOiJDTE9VRF9BRE1JTiJ9.XjANLTWvwHOBPaRFM4gFy40A1_G7fSAtSEg4o3er0HGlySF3JTk92LfeZ_ktYzV-OP84DBumE7PVE6oMPigiF424EMKYQ8FSPBKUsPgLF3rENVoOI0fqYIUypg9ygh9FBL8Pcc9_XZnPMGbCCIxVnWLeggl1gsHa5zIN6sDurnStEf4Kqa4d-nCsMBtwrabEcArhNlP78I86AoyyB_SXc2FZpDJKwydsAF_--fxCenug98iFmaiJg_eeVFoMl90v_7lSKKu1kB3x9GJxB8TEtDPI43VsnvITIXm1Unuh0qIir2OrkB3YQq5BDPygm__bbJSXzVIdNfc_Do-nqhnWUg\n";


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
