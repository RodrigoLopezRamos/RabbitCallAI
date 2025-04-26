package com.analia.web.rs.location;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Location;
import com.analia.common.util.Base26;
import com.analia.location.service.LocationServiceLocal;
import com.analia.web.util.RequestUtils;
import com.analia.web.util.ResponseUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;


import java.math.BigInteger;
import java.util.Map;

import static com.analia.web.util.RequestUtils.getDoubleForKey;

@Path("/rs/location")
@ApplicationScoped
//@Api(tags = {"Location REST API"})
//@SwaggerDefinition(tags = {@Tag(name = "location-resource", description = "Location Resource")})
public class LocationResource {
    @Inject
    private LocationServiceLocal locationServiceLocal;

    @POST
    @Path("/save.s")
    @Consumes("application/json")
    @Produces("application/json")
    //@ApiOperation(value = "Save Location")
    public Response saveLocation(Map<String, Object> input) {

        Response response = null;
        try {
            Location location = new Location();
            location.setId(RequestUtils.getValueForKey("id", input, BigInteger.class));
            location.setAddress1(RequestUtils.getMandatoryStringForKey("address1", input));
            location.setAddress2(RequestUtils.getStringForKey("address2", input));
            location.setCityId(RequestUtils.getValueForKey("cityId", input, BigInteger.class));
            location.setCity(RequestUtils.getMandatoryStringForKey("city", input));
            location.setProvince(RequestUtils.getMandatoryStringForKey("province", input));
            location.setCountry(RequestUtils.getMandatoryStringForKey("country", input));
            location.setPostalOrZipcode(RequestUtils.getMandatoryStringForKey("postalOrZipcode", input));
            location.setLatitude(getDoubleForKey("latitude", input));
            location.setLongitude(getDoubleForKey("longitude", input));
            locationServiceLocal.saveLocation(location);
            response = Response.ok(Base26.encode(location.getId())).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }


}
