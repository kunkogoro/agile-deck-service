package com.axonactive.agiletools.agiledeck.gameboard.boundary;

    import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.axonactive.agiletools.agiledeck.gameboard.control.AnsweredQuestionService;
import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestion;

@Path("/answeredquestions")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@Transactional
public class AnsweredQuestionResource {
    
    @Inject
    AnsweredQuestionService answeredQuestionService;

    @PUT
    @Path("{id}")
    public Response updateAnsweredQuestion(@PathParam("id") Long answeredQuestionId, AnsweredQuestion newAnsweredQuestion){
        AnsweredQuestion answeredQuestion = answeredQuestionService.update(answeredQuestionId,newAnsweredQuestion);
        
        return Response.ok(answeredQuestion).build();
    }
}
