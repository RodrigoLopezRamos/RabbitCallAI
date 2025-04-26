package com.analia.web.rs.dashboard;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.InputStream;

@Path("/dashboard")
public class DashboardResource {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response serveIndex() {
        InputStream indexHtml = getClass().getClassLoader()
                .getResourceAsStream("META-INF/resources/index.html");

        if (indexHtml == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("index.html not found in classpath")
                    .build();
        }

        return Response.ok(indexHtml).build();
    }
}