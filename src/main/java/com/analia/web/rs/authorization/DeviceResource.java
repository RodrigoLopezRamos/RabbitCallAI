package com.analia.web.rs.authorization;

import com.analia.authorization.service.AuthorizationService;
import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Device;
import com.analia.web.filters.DeviceFilter;
import com.analia.web.util.RequestUtils;
import com.analia.web.util.ResponseUtils;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;


@Path("/device")
@RequestScoped
public class DeviceResource {

    @Inject
    private AuthorizationService authorizationServiceLocal;


    @POST
    @Path("/register.c")
    @Consumes("application/json")
    @Produces("application/json")
    public Response register(@Context @NotNull HttpHeaders headers) {
        Response response = null;
        try {
            String uuid = headers.getRequestHeaders().getFirst("uuid");
            String userAgent = headers.getRequestHeaders().getFirst("userAgent");
                RequestUtils.validateNotNull("uuid", uuid);
                RequestUtils.validateNotNull("userAgent", userAgent);
            Device device = this.authorizationServiceLocal.registerDevice(uuid, userAgent);
            String nonce = device.generateNonceString();
            AnaliaUserContext.getContext().setAttribute(AnaliaUserContext.DEVICE, device);
            HashMap<String, Object> result = new HashMap<>();
            result.put("deviceId", device.getUuid());
            response = Response.ok(result).header(DeviceFilter.NONCE, nonce)
                    .build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }

    @GET
    @Path("/alive")
    @Consumes("application/json")
    @Produces("application/json")
    public Response alive() {
        Response response = null;
        response = Response.ok().build();
        return response;
    }
}
