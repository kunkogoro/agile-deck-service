package com.axonactive.agiletools.agiledeck.game.boundary;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class QuestionResourceTest {
    
    @Test
    public void whenClickNextScenario_thenReturnNewAnsweredQuestion(){
        RestAssured.given().pathParam("code", "b4661d5e-f296-4cf6-887d-cfa0f97d1f36")
            .when().get("/questions/{code}")
            .then()
            .statusCode(200);
    }

    @Test
    public void whenClickNextScenario_thenReturnNewAnsweredQuestionByOrder() {
        RestAssured.given().pathParam("code", "b4661d5e-f296-4cf6-887d-cfa0f97d1f36")
                .when().get("/questions/{code}/order")
                .then()
                .statusCode(200);
    }

    @Test
    public void giveListQuestion_whenAddListQuestionIntoServer_thenReturnOkStatus() {
        String data = "[{\"content\":{\"content\":\"New Question 1\"}},{\"content\":{\"content\":\"New Question 2\"}},{\"content\":{\"content\":\"New Question 3\"}},{\"content\":{\"content\":\"New Question 4\"}},{\"content\":{\"content\":\"New Question 5\"}}]";
        RestAssured.given()
                .queryParam("gameBoardCode", "asd6gfga-f296-sdf3-0fn2-asf86gc1crt2")
                .header("Content-Type", "application/json")
                .body(data)
                .when()
                .post("questions")
                .then()
                .statusCode(200);
    }
}
