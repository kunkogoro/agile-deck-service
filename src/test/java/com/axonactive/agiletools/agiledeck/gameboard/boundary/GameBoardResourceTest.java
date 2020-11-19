package com.axonactive.agiletools.agiledeck.gameboard.boundary;

import javax.json.Json;
import javax.json.JsonObject;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

@QuarkusTest
class GameBoardResourceTest {
    @Test
    public void whenCreateNewGameBoard_thenReturnLocationInHeader() {
        Response response = RestAssured.given().queryParam("game", 1).when().put("gameboards");

        Assertions.assertEquals(201, response.getStatusCode());
        Assertions.assertNotNull(response.getHeader("Location"));
    }

    @Test
    public void whenCreateNewGameBoard_thenReturnGameNotFound() {
        RestAssured.given().queryParam("game", 2).when().put("gameboards").then().statusCode(400).header("MSG_CODE",
                CoreMatchers.is("GAME_NOT_FOUND"));
    }

    @Test
    public void whenJoinGame_thenReturnAnswerQuestionDetail() {
        Response response = RestAssured.given().pathParam("code", "b4661d5e-f296-4cf6-887d-cfa0f97d1f36")
                .when()
                .get("gameboards/join/{code}");

        Assertions.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void whenJoinGame_thenReturnAnswer() {
        RestAssured.given().pathParam("code", "code-not-found").when().get("gameboards/join/{code}").then().statusCode(400)
                .header("MSG_CODE", CoreMatchers.is("GAME_BOARD_NOT_FOUND"));
    }

    @Test
    public void whenPlayerChooseAnswer_thenReturnAnswerQuestionDetail() {

        JsonObject answerContent = (JsonObject) Json.createObjectBuilder()
                                .add("content", "Bigbang")
                                .add("contentAsImage", "bigbang.png")
                                .build();
        
        Response response = RestAssured.given().pathParam("answerQuestionDetailId", 5)
                                .header("Content-Type", "application/json")
                                .body(answerContent)
                                .when().put("gameboards/{answerQuestionDetailId}");

        Assertions.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void whenPlayerChooseAnswer_thenReturnNotFoundAnswerQuestionDetail(){

        JsonObject answerContent = (JsonObject) Json.createObjectBuilder()
                                .add("content", "Bigbang")
                                .add("contentAsImage", "bigbang.png")
                                .build();
        
        RestAssured.given().pathParam("answerQuestionDetailId", -1)
                .header("Content-Type", "application/json")
                .body(answerContent)
                .when().put("gameboards/{answerQuestionDetailId}")
                .then()
                .statusCode(400)
                .header("MSG_CODE", CoreMatchers.is("ANSWER_QUESTION_DETAIL_NOT_FOUND"));
    }

    @Test
    public void whenPlayerRejoinGame_thenReturnReturnAnswerQuetionDetail(){
        Response response = RestAssured.given().pathParam("code", "b4661d5e-f296-4cf6-887d-cfa0f97d1f36")
                .queryParam("playerId", 5).when().get("gameboards/rejoin/{code}");

        Assertions.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void whenPlayerRejoinGame_thenReturnGameBoardNotFound(){
        RestAssured.given().pathParam("code", "code-not-found")
                .queryParam("playerId", 5)
                .when()
                .get("gameboards/rejoin/{code}")
                .then().statusCode(400)
                .header("MSG_CODE", CoreMatchers.is("GAME_BOARD_NOT_FOUND"));
    }

    @Test
    public void whenPlayerRejoinGame_thenReturnPlayerNotFound(){
        RestAssured.given().pathParam("code", "b4661d5e-f296-4cf6-887d-cfa0f97d1f36")
                .queryParam("playerId", -1)
                .when()
                .get("gameboards/rejoin/{code}")
                .then().statusCode(400)
                .header("MSG_CODE", CoreMatchers.is("PLAYER_NOT_FOUND"));
    }

    // @Test
    // public void whenPlayerRejoinGame_thenReturnNewAnswerQuestionDetail(){
    //     RestAssured.given().pathParam("code", "b4661d5e-f296-4cf6-887d-cfa0f97d1f36")
    //             .queryParam("playerId", 50)
    //             .when()
    //             .get("gameboards/rejoin/{code}")
    //             .then().statusCode(200);
    // }

}