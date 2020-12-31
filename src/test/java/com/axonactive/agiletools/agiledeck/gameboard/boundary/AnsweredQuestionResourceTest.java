package com.axonactive.agiletools.agiledeck.gameboard.boundary;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;

@QuarkusTest
public class AnsweredQuestionResourceTest {
        @Test
        
        public void whenChangeNameQuestion_thenReturnNewNameQuestion() {
                JsonObject answerContent = Json.createObjectBuilder()
                                .add("content", Json.createObjectBuilder()
                                        .add("content", "New Problem").build())
                                .build();
                                
                Response res = RestAssured.given()
                        .pathParam("id", "b4661d5e-f296-4cf6-887d-cfa0f97d1f36")
                        .header("Content-Type", "application/json")
                        .body(answerContent)
                        .when()
                        .put("answeredquestions/{id}");

                Assertions.assertEquals(200, res.getStatusCode());
                
        }
        @Test
        public void whenAddNewQuestion_thenReturnNewQuestion(){
                JsonObject answerContent = Json.createObjectBuilder()
                                .add("content", Json.createObjectBuilder()
                                        .add("content", "Add new problem").build())
                                .build();

                Response response = RestAssured.given()
                                .pathParam("gameBoardCode", "asd6gfga-f296-sdf3-0fn2-asf86gc1crt2")
                                .header("Content-Type", "application/json")
                                .body(answerContent)
                                .when()
                                .post("answeredquestions/{gameBoardCode}");
                Assertions.assertEquals(200, response.getStatusCode());
        }
}
