package com.analia.web.rs.webhook;

import com.analia.vapi.VapiMessageService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;


@ApplicationScoped
public class VapiMessageResource {

    private static final Logger logger = LoggerFactory.getLogger(VapiMessageResource.class);

    @Inject
    VapiMessageService messageService;

    String apiToken;

    @POST
    @Path("/messages")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response handleMessage(
            @HeaderParam("X-Vapi-Token") String token,
            @Context HttpServletRequest request) {

        try {
            // Read request body manually
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = null;

            try {
                bufferedReader = request.getReader();
                char[] charBuffer = new char[128];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException ex) {
                        // Ignore
                    }
                }
            }

            String json = stringBuilder.toString();
            logger.info("Raw request body: {}", json);
            System.out.println("JSON PAYLOAD: " + json);

            if (json == null || json.trim().isEmpty()) {
                logger.warn("Empty request body received");
                return Response.status(Response.Status.BAD_REQUEST).entity("Empty request body").build();
            }

            // Validate API token for security
            if (!isValidToken(token)) {
                logger.warn("Unauthorized message API request");
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            // Here you would process the message using your service
            // VapiResponse vapiResponse = messageService.processMessage(message);

            return Response.ok().build();

        } catch (Exception e) {
            logger.error("Error processing VAPI message", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error processing message: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/health")
    @Produces(MediaType.TEXT_PLAIN)
    public Response healthCheck() {
        return Response.ok("VAPI Message Server is running").build();
    }

    private boolean isValidToken(String token) {
        return true; //token != null && token.equals(apiToken);
    }
}
