package com.analia.web.rs.call;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/agent")
@ApplicationScoped
public class AgentResource {

    @POST
    @Path("/call")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response handleMessage(
            @HeaderParam("X-Vapi-Token") String token,
            @Context HttpServletRequest request) {
        return Response.accepted().build();

    }
}
