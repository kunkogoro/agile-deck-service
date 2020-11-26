package com.axonactive.agiletools.agiledeck.game.boundary;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

@QuarkusTest
public class QuestionResourceTest {
    
    @Test
    public void whenClickNextScenario_thenReturnNewAnsweredQuestion(){
        RestAssured.given().pathParam("code", "b4661d5e-f296-4cf6-887d-cfa0f97d1f36")
            .when().get("/questions/{code}")
            .then().statusCode(200);
    }
}
