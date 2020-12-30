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
    public void giveTextFile_whenUploadFileWithGameBoardCode_thenReturnOkStatusCode() {
        RestAssured.given()
                .multiPart("file", file)
                .when()
                .post("files/upload/b4661d5e-f296-4cf6-887d-cfa0f97d1f36")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    public void whenDownloadFileFromServer_thenReturnOkStatusCode() {
        RestAssured.given()
                .pathParam("fileName", "testfile.txt")
                .pathParam("code", "b4661d5e-f296-4cf6-887d-cfa0f97d1f36")
                .when()
                .get("files/download/{code}/{fileName}")
                .then()
                .statusCode(200);
    }
}