package com.axonactive.agiletools.agiledeck.gameboard.boundary;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.axonactive.agiletools.agiledeck.game.entity.Question;
import com.axonactive.agiletools.agiledeck.gameboard.control.AnsweredQuestionService;
import com.axonactive.agiletools.agiledeck.gameboard.control.GameBoardService;
import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestion;
import com.axonactive.agiletools.agiledeck.gameboard.entity.GameBoard;

@Path("/answeredquestions")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@Transactional
public class AnsweredQuestionResource {
    
    @Inject
    AnsweredQuestionService answeredQuestionService;
    @Inject
    GameBoardService gameBoardService;
    
    @PUT
    @Path("{code}")
    public Response update(@PathParam("code") String gameBoardCode, AnsweredQuestion newAnsweredQuestion){
        GameBoard gameBoard = gameBoardService.getByCode(gameBoardCode);
        Question question = new Question();
        question.setContent(newAnsweredQuestion.getContent());
        return Response.ok(answeredQuestionService.update(gameBoard, question)).build();
    }

    @POST
    @Path("{Code}")
    public Response add(@PathParam("Code") String gameBoardCode, AnsweredQuestion newAnsweredQuestion  ){
        GameBoard gameBoard = gameBoardService.getByCode(gameBoardCode);
        AnsweredQuestion previousAnsweredQuestion = answeredQuestionService.findCurrentPLaying(gameBoard.getId());
        previousAnsweredQuestion.setPlaying(false);
        answeredQuestionService.updateStatusPlaying(previousAnsweredQuestion);
        Question question = new Question();
        question.setContent(newAnsweredQuestion.getContent());
        AnsweredQuestion answeredQuestion = answeredQuestionService.create(question, gameBoard);
        return Response.ok(answeredQuestion).build();
    }
}
