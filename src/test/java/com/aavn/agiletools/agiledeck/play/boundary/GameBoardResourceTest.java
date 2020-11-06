package com.aavn.agiletools.agiledeck.play.boundary;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

@QuarkusTest
class GameBoardResourceTest {

    @Test
    public void whenCreateNewGameBoard_thenReturnLocationInHeader() {
        RestAssured.given().queryParam("game", 1)
                .when().put("gameboards")
                .then().statusCode(201).header("Location", CoreMatchers.containsString("gameboards/"));
    }

    @Test
    public void whenCreateNewGameBoard_thenReturnGameNotFound() {
        RestAssured.given().queryParam("game", 2)
                .when().put("gameboards")
                .then().statusCode(400).header("MSG_CODE", CoreMatchers.is("GAME_NOT_FOUND"));
    }

    @Test
    public void whenJoinGame_thenReturnAnswerQuestion() {
        String json = "{\"answerOptions\":[{\"answerGroup\":{\"id\":1,\"name\":\"Approach set\"},\"content\":{\"content\":\"Iterative\",\"contentAsImage\":\"iterative.png\"},\"game\":{\"description\":\"A workshop game to encourage people to think about alternative approaches for tackling projects. - by Scum & Kanban\",\"id\":1,\"name\":\"Iterative - Incremental - Big Bang\"},\"id\":1},{\"answerGroup\":{\"id\":1,\"name\":\"Approach set\"},\"content\":{\"content\":\"Incremental\",\"contentAsImage\":\"incremental.png\"},\"game\":{\"description\":\"A workshop game to encourage people to think about alternative approaches for tackling projects. - by Scum & Kanban\",\"id\":1,\"name\":\"Iterative - Incremental - Big Bang\"},\"id\":2},{\"answerGroup\":{\"id\":1,\"name\":\"Approach set\"},\"content\":{\"content\":\"Bigbang\",\"contentAsImage\":\"bigbang.png\"},\"game\":{\"description\":\"A workshop game to encourage people to think about alternative approaches for tackling projects. - by Scum & Kanban\",\"id\":1,\"name\":\"Iterative - Incremental - Big Bang\"},\"id\":3}],\"gameBoard\":{\"code\":\"4a658613-db88-44be-b2da-a1d90f9f7695\",\"game\":{\"description\":\"A workshop game to encourage people to think about alternative approaches for tackling projects. - by Scum & Kanban\",\"id\":1,\"name\":\"Iterative - Incremental - Big Bang\"},\"id\":1}}";
        RestAssured.given().pathParam("code", "4a658613-db88-44be-b2da-a1d90f9f7695")
                .when().get("gameboards/{code}")
                .then().body(CoreMatchers.is(json));
    }

    @Test
    public void whenJoinGame_thenReturnAnswer() {
        RestAssured.given().pathParam("code", "code-not-found")
                .when().get("gameboards/{code}")
                .then().statusCode(400).header("MSG_CODE", CoreMatchers.is("GAME_BOARD_NOT_FOUND"));
    }
}