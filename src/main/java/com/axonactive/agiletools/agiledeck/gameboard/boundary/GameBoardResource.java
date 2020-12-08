package com.axonactive.agiletools.agiledeck.gameboard.boundary;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.axonactive.agiletools.agiledeck.game.control.AnswerService;
import com.axonactive.agiletools.agiledeck.game.control.QuestionService;
import com.axonactive.agiletools.agiledeck.game.entity.Question;
import com.axonactive.agiletools.agiledeck.gameboard.control.AnsweredQuestionDetailService;
import com.axonactive.agiletools.agiledeck.gameboard.control.AnsweredQuestionService;
import com.axonactive.agiletools.agiledeck.gameboard.control.GameBoardService;
import com.axonactive.agiletools.agiledeck.gameboard.control.PlayerService;
import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestion;
import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestionDetail;
import com.axonactive.agiletools.agiledeck.gameboard.entity.GameBoard;
import com.axonactive.agiletools.agiledeck.gameboard.entity.Player;

@Path("/gameboards")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@Transactional
public class GameBoardResource {
    
    @Inject
    GameBoardService gameBoardService;

    @Inject
    PlayerService playerService;

    @Inject
    QuestionService questionService;

    @Inject
    AnsweredQuestionDetailService answeredQuestionDetailService;

    @Inject
    AnsweredQuestionService answeredQuestionService;

    @Inject
    AnswerService answerService;


    @Context
    UriInfo uriInfo;

    @PUT
    public Response create(@QueryParam("game") Long gameId) {
        GameBoard gameBoard = gameBoardService.create(gameId);
        List<Question> questions = questionService.getAllByGameID(gameId);
        Question question = questionService.random(questions, gameBoard.getId());
        answeredQuestionService.create(question, gameBoard);
        URI location = this.uriInfo.getAbsolutePathBuilder().path(String.valueOf(gameBoard.getCode())).build();
        return Response.created(location).entity(gameBoard).build(); 
    }

    @GET
    @Path("/join/{code}")
    public Response join(@PathParam("code") String code) {
        AnsweredQuestion currentQuestion = gameBoardService.join(code);
        Player player = playerService.create(code);
        AnsweredQuestionDetail answeredQuestionDetail = answeredQuestionDetailService.create(currentQuestion, player);
        
        Long gameId = gameBoardService.getByCode(code).getGame().getId();
        answeredQuestionDetail.getAnsweredQuestion().setAnswerOptions(answerService.getByGame(gameId));

        return Response.ok(answeredQuestionDetail).build();
    }

    @GET
    @Path("/rejoin/{code}")
    public Response rejoin(@PathParam("code") String code, @QueryParam("playerId") Long playerId) {
        AnsweredQuestion currentQuestion = gameBoardService.join(code);
        Player player = playerService.findById(playerId);

        AnsweredQuestionDetail answeredQuestionDetail = answeredQuestionDetailService.rejoin(currentQuestion, player);
        if (Objects.isNull(answeredQuestionDetail)) {
            answeredQuestionDetail = answeredQuestionDetailService.create(currentQuestion, player);
        }
        
        Long gameId = gameBoardService.getByCode(code).getGame().getId();
        answeredQuestionDetail.getAnsweredQuestion().setAnswerOptions(answerService.getByGame(gameId));

        return Response.ok(answeredQuestionDetail).build();
    }

    @GET
    @Path("/history/{code}")
    public Response getHistory(@PathParam("code") String code) {
        GameBoard currentGameBoard = gameBoardService.getByCode(code);
        gameBoardService.validate(currentGameBoard);

        Map<String, Object> history = new LinkedHashMap<>();
        List<AnsweredQuestion> questionList = answeredQuestionService.findByGameBoardId(currentGameBoard.getId());
        questionList.forEach(answeredQuestion -> {
            List<AnsweredQuestionDetail> answerList = answeredQuestionDetailService.getAllByAnsweredQuestionId(answeredQuestion.getId())
                    .stream()
                    .filter(answeredQuestionDetail -> Objects.nonNull(answeredQuestionDetail.getAnswer()))
                    .collect(Collectors.toList());
            if (!answerList.isEmpty()) {
                history.put(answeredQuestion.getContent().getContent(), answerList);
            }
        });

        return Response.ok(history).build();
    }
}
