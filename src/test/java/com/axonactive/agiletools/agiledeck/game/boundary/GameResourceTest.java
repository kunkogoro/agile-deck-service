package com.axonactive.agiletools.agiledeck.game.boundary;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

@QuarkusTest
public class GameResourceTest {
    
    @Test
    public void whenGoToHomePage_thenReturnInfomationGames(){
        RestAssured.given().when().get("/games").then().statusCode(200);
    }

    @Test
    public void whenViewHistoryOfGameBoard_thenReturnHistoryOfGameBoard(){
        RestAssured.given().pathParam("code", "b4661d5e-f296-4cf6-887d-cfa0f97d1f36").when().get("gameboards/history/{code}").then().statusCode(200);
    }

}

