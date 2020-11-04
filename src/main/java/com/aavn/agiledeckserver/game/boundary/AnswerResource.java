package com.aavn.agiledeckserver.game.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.aavn.agiledeckserver.game.control.AnswerGroupService;
import com.aavn.agiledeckserver.game.control.AnswerService;
import com.aavn.agiledeckserver.game.entity.AnswerGroup;

@Stateless
@Path("/answer")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class AnswerResource {
    
    @Inject
    AnswerService answerService;

    @Inject
    AnswerGroupService answerGroupService;

    @GET
    @Path("{id}")
    public Response getAnswers(@PathParam("id") Integer id){
        AnswerGroup answerGroup = answerGroupService.getById(id);
        return Response.ok(answerService.getByAnswerGroup(answerGroup)).build();
    };
}
