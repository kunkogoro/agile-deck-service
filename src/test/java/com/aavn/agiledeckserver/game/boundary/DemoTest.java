package com.aavn.agiledeckserver.game.boundary;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class DemoTest {
    private Demo demo;

    @BeforeEach
    public void initialize() {
        demo = new Demo();
    }

    @Test
    public void sayHello() {
        assertEquals("hello", demo.sayHello());
    }

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/test")
                .then()
                .statusCode(200)
                .body(is("This is content for testing!"));
    }
}