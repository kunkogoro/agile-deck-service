package com.axonactive.agiletools.agiledeck.gameboard.boundary;

import java.util.Arrays;
import java.util.List;

import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestionDetail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

@QuarkusTest
public class AnsweredQuestionDetailResourceTest {
    
    @Test
    public void whenGetAll_thenReturnListOfAnsweredQuestionOfPlayers() {
        Response response = RestAssured.given().pathParam("id", 1).when().get("answeredquestiondetails/{id}")
                .then().assertThat().statusCode(200)
                .extract().response(); 
                
        AnsweredQuestionDetail[] answeredQuestionDetailArray = response.as(AnsweredQuestionDetail[].class);
        List<AnsweredQuestionDetail> answeredQuestionDetailList = Arrays.asList(answeredQuestionDetailArray);

        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertTrue(answeredQuestionDetailList.size() > 0);
    }

    @Test
    public void whenGetAllWithAnsweredQuestionHasNoAnsweredQuestionDetails_thenReturnEmptyList() {
        Response response = RestAssured.given().pathParam("id", 0).when().get("answeredquestiondetails/{id}")
                .then().assertThat().statusCode(200)
                .extract().response(); 
                
        AnsweredQuestionDetail[] answeredQuestionDetailArray = response.as(AnsweredQuestionDetail[].class);
        List<AnsweredQuestionDetail> answeredQuestionDetailList = Arrays.asList(answeredQuestionDetailArray);

        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertEquals(answeredQuestionDetailList.size(), 0);
    }

}
