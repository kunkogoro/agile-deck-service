package com.axonactive.agiletools.agiledeck.game.boundary;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

@QuarkusTest
public class QuestionResourceTest {
    
    @Test
    public void whenClickNextScenario_thenReturnNewAnsweredQuestion(){
        RestAssured.given().pathParam("code", "asd6gfga-f296-sdf3-0fn2-asf86gc1crt2")
            .when().get("/questions/{code}")
            .then().statusCode(200);
    }
}
