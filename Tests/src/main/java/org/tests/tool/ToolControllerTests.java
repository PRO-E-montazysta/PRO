package org.tests.tool;

import io.restassured.http.ContentType;
<<<<<<< HEAD
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
=======
import org.junit.jupiter.api.Test;
import org.testng.annotations.BeforeTest;

>>>>>>> origin/testy-auto-i-api
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ToolControllerTests {
<<<<<<< HEAD
    String bearerToken= "eyJraWQiOiJhYjFhZjZmNC1hMTgyLTQzMTEtYjhkNi05MmQwNzU5ODcyYjgiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiYWRtaW4iLCJleHAiOjE2NzUyNzE1ODYsImlhdCI6MTY3NTI2Nzk4Niwic2NvcGUiOiJDTE9VRF9BRE1JTiJ9.MNkR0wffR004V45hXNQDp0aF6OMiH_cZT0lKs0iov502TkytArFYNb2cew0NQ7MOZWRWyDWEibUYHSb77Yf3vleLcm2MJ4ASlbNtg7C9ICrNBlualrRH-hBaNxLPJKCj08fgjpkN9-aqlzP90TJIyu2JP7oJUJIQD1eWTTJXyjLfV_Yh5wfNWybzQ4wC2udX-zBAJIr-R2Iizo1IpbFMdP7E6UbiVTwT9jJGZPvr068pNcUi4qGGbgwX-R2r9O1FBuJKrD6huxBBRxJFPEaiy_jKhCbFEAupSq1Jm0NfTk1HRNkTKnqwHW-95g0C1qL-w1rHVMbz56xWMbWX3SZZwg";


    @BeforeAll
    public void setUp() throws Exception {

    }
=======
    String bearerToken= "eyJraWQiOiI3NTU0NDIwYy1hZDU0LTQ4ZWQtYmRiZi1mMWZiMTAyODUxMzYiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiYWRtaW4iLCJleHAiOjE2NzUxOTAzOTEsImlhdCI6MTY3NTE4Njc5MSwic2NvcGUiOiJDTE9VRF9BRE1JTiJ9.XjANLTWvwHOBPaRFM4gFy40A1_G7fSAtSEg4o3er0HGlySF3JTk92LfeZ_ktYzV-OP84DBumE7PVE6oMPigiF424EMKYQ8FSPBKUsPgLF3rENVoOI0fqYIUypg9ygh9FBL8Pcc9_XZnPMGbCCIxVnWLeggl1gsHa5zIN6sDurnStEf4Kqa4d-nCsMBtwrabEcArhNlP78I86AoyyB_SXc2FZpDJKwydsAF_--fxCenug98iFmaiJg_eeVFoMl90v_7lSKKu1kB3x9GJxB8TEtDPI43VsnvITIXm1Unuh0qIir2OrkB3YQq5BDPygm__bbJSXzVIdNfc_Do-nqhnWUg\n";


>>>>>>> origin/testy-auto-i-api
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
