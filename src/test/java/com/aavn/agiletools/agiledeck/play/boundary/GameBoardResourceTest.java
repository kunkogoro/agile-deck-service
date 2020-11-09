package com.aavn.agiletools.agiledeck.play.boundary;

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
        Response response = RestAssured
                .given().queryParam("game", 1)
                .when().put("gameboards");

        Assertions.assertEquals(201, response.getStatusCode());
        Assertions.assertNotNull(response.getHeader("Location"));
    }

    @Test
    public void whenCreateNewGameBoard_thenReturnGameNotFound() {
        RestAssured.given().queryParam("game", 2)
                .when().put("gameboards")
                .then().statusCode(400).header("MSG_CODE", CoreMatchers.is("GAME_NOT_FOUND"));
    }

    @Test
    public void whenJoinGame_thenReturnAnswerQuestion() {
        Response response = RestAssured.given().pathParam("code", "b4661d5e-f296-4cf6-887d-cfa0f97d1f36")
                .when().get("gameboards/{code}");

        Assertions.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void whenJoinGame_thenReturnAnswer() {
        RestAssured.given().pathParam("code", "code-not-found")
                .when().get("gameboards/{code}")
                .then().statusCode(400).header("MSG_CODE", CoreMatchers.is("GAME_BOARD_NOT_FOUND"));
    }
}