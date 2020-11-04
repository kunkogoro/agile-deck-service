package com.aavn.agiledeckserver.user.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.aavn.agiledeckserver.play.control.GameBoardService;
import com.aavn.agiledeckserver.play.entity.GameBoard;
import com.aavn.agiledeckserver.user.control.PlayerService;
import com.aavn.agiledeckserver.user.entity.Player;

@Stateless
@Path("/player")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class PlayerResource {
    
    @Inject
    PlayerService playerService;

    @Inject
    GameBoardService gameBoardService;

    @POST
    @Path("/add-player-to-game-board")
    public Response addPlayerToGameBoard(Integer playerId, Integer gameBoardId) {
        Player player = playerService.getById(playerId);
        GameBoard gameBoard = gameBoardService.getById(gameBoardId);
        playerService.addtoGameBoard(player, gameBoard);
        return Response.ok().build();
    }
}
