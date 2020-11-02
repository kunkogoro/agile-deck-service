package com.aavn.agiledeckserver.game.boundary;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Stateless
@Path("test")
public class DemoRest {
    @GET
    public String test() {
        return "This is content for testing!";
    }
}
