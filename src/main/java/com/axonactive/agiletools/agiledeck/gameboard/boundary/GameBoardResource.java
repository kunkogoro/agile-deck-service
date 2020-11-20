package com.axonactive.agiletools.agiledeck.gameboard.boundary;

import java.net.URI;
import java.util.Objects;

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

import com.axonactive.agiletools.agiledeck.game.entity.AnswerContent;
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
    AnsweredQuestionDetailService answeredQuestionDetailService;

    @Context
    UriInfo uriInfo;

    @PUT
    public Response create(@QueryParam("game") Long gameId) {
        GameBoard gameBoard = gameBoardService.create(gameId);
        URI location = this.uriInfo.getAbsolutePathBuilder().path(String.valueOf(gameBoard.getCode())).build();
        return Response.created(location).entity(gameBoard).build(); 
    }

    @GET
    @Path("/join/{code}")
    public Response join(@PathParam("code") String code) {       
        AnsweredQuestion currentQuestion = gameBoardService.join(code);
        Player player = playerService.create(code);
        AnsweredQuestionDetail answeredQuestionDetail = answeredQuestionDetailService.create(currentQuestion, player);
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
        return Response.ok(answeredQuestionDetail).build();
    }
}
