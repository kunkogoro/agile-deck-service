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
    public void whenCreateNewGameBoard_thenReturnGameNotFound() {
        RestAssured.given().queryParam("game", 1000).when().put("gameboards").then().statusCode(400).header("MSG_CODE",
                CoreMatchers.is("GAME_NOT_FOUND"));
    }

    @Test
    public void whenJoinGame_thenReturnAnswerQuestionDetail() {
        Response response = RestAssured.given().pathParam("code", "b4661d5e-f296-4cf6-887d-cfa0f97d1f36").when()
                .get("gameboards/join/{code}");

        Assertions.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void whenJoinGame_thenReturnAnswer() {
        RestAssured.given().pathParam("code", "code-not-found").when().get("gameboards/join/{code}").then().statusCode(400)
                .header("MSG_CODE", CoreMatchers.is("GAME_BOARD_NOT_FOUND"));
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
                .when().put("answeredquestiondetails/{answerQuestionDetailId}")
                .then()
                .statusCode(400)
                .header("MSG_CODE", CoreMatchers.is("ANSWER_QUESTION_DETAIL_NOT_FOUND"));
    }

    @Test
    public void whenPlayerRejoinGame_thenReturnReturnAnswerQuetionDetail(){
        Response response = RestAssured.given().pathParam("code", "b4661d5e-f296-4cf6-887d-cfa0f97d1f36")
                .queryParam("playerId", 1).when().get("gameboards/rejoin/{code}");

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

    @Test
    public void whenPlayerRejoinGame_thenReturnNewAnswerQuestionDetail(){
        RestAssured.given().pathParam("code", "b4661d5e-f296-4cf6-887d-cfa0f97d1f36")
                .queryParam("playerId", 1)
                .when()
                .get("gameboards/rejoin/{code}")
                .then().statusCode(200);
    }


    @Test
    public void whenCreateNewGameBoard_thenReturnLocationInHeader() {
        Response response = RestAssured.given().queryParam("game", 1).when().put("gameboards");

        Assertions.assertEquals(201, response.getStatusCode());
        Assertions.assertNotNull(response.getHeader("Location"));
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
                .when().put("answeredquestiondetails/{answerQuestionDetailId}");

        Assertions.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void whenPlayerAddAnswer_thenReturnStatusOk(){
        JsonObject answerContent = Json.createObjectBuilder()
                                .add("content", Json.createObjectBuilder()
                                        .add("content", "Abc")
                                        .add("contentAsDescription", "")
                                        .add("contentAsImage", "abc.png")
                                        .build()
                                    )
                                .build();

        Response response = RestAssured.given().pathParam("code", "b4661d5e-f296-4cf6-887d-cfa0f97d1f36")
                                .header("Content-Type", "application/json")
                                .body(answerContent)
                                .when()
                                .put("gameboards/add-answer/{code}");

        Assertions.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void whenPlayerAddAnswerAtTheFirstTime_thenReturnStatusOk(){
        JsonObject answerContent = Json.createObjectBuilder()
                                .add("content", Json.createObjectBuilder()
                                        .add("content", "Abc")
                                        .add("contentAsDescription", "")
                                        .add("contentAsImage", "abc.png")
                                        .build()
                                    )
                                .build();

        RestAssured.given().pathParam("code", "a69d6489-9af1-4685-8896-3454e0a16032")
            .header("Content-Type", "application/json")
            .body(answerContent)
            .when()
            .put("gameboards/add-answer/{code}")
            .then()
            .statusCode(200);                 
    }

    @Test
    public void whenPlayerAddAnswer_thenReturnGameBoardNotFound(){
        JsonObject answerContent = Json.createObjectBuilder()
                                .add("content", Json.createObjectBuilder()
                                        .add("content", "Abc")
                                        .add("contentAsDescription", "")
                                        .add("contentAsImage", "abc.png")
                                        .build()
                                    )
                                .build();

        RestAssured.given().pathParam("code", "game-not-found")
            .header("Content-Type", "application/json")
            .body(answerContent)
            .when()
            .put("gameboards/add-answer/{code}")
            .then()
            .statusCode(400)
            .header("MSG_CODE", CoreMatchers.is("GAME_BOARD_NOT_FOUND"));
    }

    @Test
    public void whenPlayerAddAnswer_thenReturnListAnswerOverLimittation(){
        JsonObject answerContent = Json.createObjectBuilder()
                                .add("content", Json.createObjectBuilder()
                                        .add("content", "Abc")
                                        .add("contentAsDescription", "")
                                        .add("contentAsImage", "abc.png")
                                        .build()
                                    )
                                .build();

        RestAssured.given().pathParam("code", "asd6gfga-f296-sdf3-0fn2-asf86gc1crt2")
            .header("Content-Type", "application/json")
            .body(answerContent)
            .when()
            .put("gameboards/add-answer/{code}")
            .then()
            .statusCode(400)
            .header("MSG_CODE", CoreMatchers.is("LIST_ANSWER_OVER_LIMITTATION"));
    }

    @Test
    public void whenPlayerUpdateAnswer_thenReturnStatusOk(){
        JsonObject answerContent = Json.createObjectBuilder()
                                .add("content", Json.createObjectBuilder()
                                        .add("content", "Abc")
                                        .add("contentAsDescription", "")
                                        .add("contentAsImage", "abc.png")
                                        .build()
                                    )
                                .add("id", 1)
                                .build();
        
        RestAssured.given().pathParam("code", "b4661d5e-f296-4cf6-887d-cfa0f97d1f36")
                    .header("Content-Type", "application/json")
                    .body(answerContent)
                    .when()
                    .put("gameboards/update-answer-content/{code}")
                    .then()
                    .statusCode(200);
    }

    @Test
    public void whenPlayerUpdateAnswerAtTheFirstTime_thenReturnStatusOk(){
        JsonObject answerContent = Json.createObjectBuilder()
                                .add("content", Json.createObjectBuilder()
                                        .add("content", "Abc")
                                        .add("contentAsDescription", "")
                                        .add("contentAsImage", "abc.png")
                                        .build()
                                    )
                                .add("id", 1)
                                .build();
        
        RestAssured.given().pathParam("code", "a2ae195d-017c-485b-a369-2ff0aabf7df9")
                    .header("Content-Type", "application/json")
                    .body(answerContent)
                    .when()
                    .put("gameboards/update-answer-content/{code}")
                    .then()
                    .statusCode(200);
    }

    @Test
    public void whenPlayerUpdateAnswer_thenReturnGameBoardNotFound(){
        JsonObject answerContent = Json.createObjectBuilder()
                                .add("content", Json.createObjectBuilder()
                                        .add("content", "Abc")
                                        .add("contentAsDescription", "")
                                        .add("contentAsImage", "abc.png")
                                        .build()
                                    )
                                .add("id", 1)
                                .build();

        RestAssured.given().pathParam("code", "game-board-not-found")
            .header("Content-Type", "application/json")
            .body(answerContent)
            .when()
            .put("gameboards/update-answer-content/{code}")
            .then()
            .statusCode(400)
            .header("MSG_CODE", CoreMatchers.is("GAME_BOARD_NOT_FOUND"));                        
    }

    @Test
    public void whenPlayerDeleteAnswer_thenReturnStatusOk(){
        RestAssured.given().pathParam("code", "b4661d5e-f296-4cf6-887d-cfa0f97d1f36")
            .header("Content-Type", "application/json")
            .body(4)
            .when()
            .delete("gameboards/delete-answer/{code}")
            .then()
            .statusCode(200);
    }
}