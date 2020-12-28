package com.axonactive.agiletools.agiledeck.file.boundary;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileResourceTest {

    File file;

    @BeforeEach
    public void init() {
        try {
            file = new File("testfile.txt");
            if (!file.exists()) {
                Assertions.assertTrue(file.createNewFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(0)
    public void giveTextFile_whenUploadFileIntoServer_thenReturnOkStatusCode() {
        RestAssured.given()
                .multiPart("file", file)
                .when()
                .post("files/upload")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(1)
    public void whenDownloadFileFromServer_thenReturnOkStatusCode() {
        RestAssured.given()
                .pathParam("fileName", "testfile.txt")
                .when()
                .get("files/download/{fileName}")
                .then()
                .statusCode(200);
    }
}