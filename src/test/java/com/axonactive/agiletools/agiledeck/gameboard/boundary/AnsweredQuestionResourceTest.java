package com.axonactive.agiletools.agiledeck.gameboard.boundary;

import javax.json.Json;

import com.axonactive.agiletools.agiledeck.game.entity.QuestionContent;
import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.vertx.core.json.JsonObject;

@QuarkusTest
public class AnsweredQuestionResourceTest {
        @Test
        public void whenChangeNameQuestion_thenReturnNewNameQuestion() {
                // QuestionContent questionContent = new QuestionContent();
                // questionContent.setContent("Content to update");
                // AnsweredQuestion answeredQuestion = new AnsweredQuestion();
                // answeredQuestion.setContent(questionContent);

                JsonObject answerContent = (JsonObject) Json.createObjectBuilder()
                                .add("content", "Bigbang")
                                .add("content", "New problem")
                                .build();

                                
                Response response = RestAssured.given()
                        .pathParam("id", 1)
                        .body(answerContent).when()
                        .put("answeredquestions/{id}").then().assertThat().statusCode(200).extract().response();

                 System.out.println(response);

                Assertions.assertEquals(200, response.getStatusCode());
        }
}
