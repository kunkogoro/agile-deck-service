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

}

