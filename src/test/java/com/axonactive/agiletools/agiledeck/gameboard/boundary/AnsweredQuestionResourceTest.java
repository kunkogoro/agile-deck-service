package com.axonactive.agiletools.agiledeck.gameboard.boundary;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

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
                
                // JsonObject jsonObject = Json.createReader(new StringReader(res.body().asString())).readObject();
                // String content = jsonObject.getJsonObject("content").getString("content");
                // Assertions.assertEquals("New Problem",content);
        }
        @Test
        public void whenAddNewQuestion_thenReturnNewQuestion(){
                JsonObject answerContent = Json.createObjectBuilder()
                                .add("content", Json.createObjectBuilder()
                                        .add("content", "Add new problem").build())
                                .build();

                Response response = RestAssured.given()
                                .pathParam("gameBoardCode", "b4661d5e-f296-4cf6-887d-cfa0f97d1f36")
                                .header("Content-Type", "application/json")
                                .body(answerContent)
                                .when()
                                .post("answeredquestions/{gameBoardCode}");
                Assertions.assertEquals(200, response.getStatusCode());
                // System.out.println(response.asString());
        }
}
