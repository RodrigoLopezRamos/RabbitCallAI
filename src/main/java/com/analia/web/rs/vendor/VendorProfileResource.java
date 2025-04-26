package com.analia.web.rs.vendor;

import com.analia.common.constants.Constants;
import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Location;
import com.analia.common.model.User;
import com.analia.common.model.Vendor;
import com.analia.common.model.VendorLocation;
import com.analia.location.service.LocationServiceLocal;
import com.analia.user.service.UserService;
import com.analia.vendor.service.VendorServiceLocal;
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
import java.util.HashMap;


@Path("/vendor-profile")
@ApplicationScoped
public class VendorProfileResource {
    @Inject
    private VendorServiceLocal vendorServiceLocal;
    @Inject
    private LocationServiceLocal locationServiceLocal;
    @Inject
    private UserService userServiceLocal;

    /**
     * @param reqBody
     * @return
     */
    @Path("/save.v")
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @SuppressWarnings("unchecked")
    public Response saveVendorProfile(HashMap<String, Object> reqBody) {
        Response response = null;
        try {
            String corporateName = RequestUtils.getMandatoryStringForKey("corporateName", reqBody);
            String merchantName = RequestUtils.getMandatoryStringForKey("vendorName", reqBody);
            String locationName = RequestUtils.getMandatoryStringForKey("locationName", reqBody);
            String address1 = RequestUtils.getMandatoryStringForKey("address1", reqBody);
            String address2 = RequestUtils.getStringForKey("address2", reqBody);
            String city     = RequestUtils.getMandatoryStringForKey("city", reqBody);
            String province = RequestUtils.getMandatoryStringForKey("province", reqBody);
            String postalCode = RequestUtils.getMandatoryStringForKey("postalCode", reqBody);
            String country     = RequestUtils.getMandatoryStringForKey("country", reqBody);
            String phoneNumber = RequestUtils.getMandatoryStringForKey("phoneNumber", reqBody);
            String email = RequestUtils.getMandatoryStringForKey("locationEmail", reqBody);

            HashMap<String, Object> mUserAgent = RequestUtils.getValueForKey("agent", reqBody, HashMap.class);
            String firstName = RequestUtils.getMandatoryStringForKey("firstName", mUserAgent);
            String lastName = RequestUtils.getMandatoryStringForKey("lastName", mUserAgent);
            String title = RequestUtils.getMandatoryStringForKey("title", mUserAgent);
            String phoneNumberAgent = RequestUtils.getMandatoryStringForKey("phoneNumber", mUserAgent);
            String agentEmail = RequestUtils.getMandatoryStringForKey("email", mUserAgent);


            double latitude = RequestUtils.getMandatoryDoubleForKey("latitude", reqBody);
            double longitude = RequestUtils.getMandatoryDoubleForKey("longitude", reqBody);

            vendorServiceLocal.saveVendorLocation(BigInteger.valueOf(Constants.NEW_INSTANCE_ID), corporateName, merchantName, address1, address2, city, province, postalCode, country, phoneNumber, locationName, email, latitude, longitude);
            userServiceLocal.saveUserProfile(firstName, lastName, title, phoneNumberAgent, agentEmail);

            response = Response.ok(ResponseUtils.createSucessResponse()).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }


    @Path("/get-profile.v")
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response getVendorProfile() {
        Response response = null;
        try {
            Vendor vendor = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.VENDOR, Vendor.class);
            VendorLocation vendorLocation = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.VENDOR_LOCATION, VendorLocation.class);
            User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
            Location location = locationServiceLocal.getLocation(vendorLocation.getLocationId());

            HashMap<String, Object> result = new HashMap<>();
            result.put("corporateName", vendor.getCorporateName());
            result.put("merchantName", vendor.getName());
            result.put("locationName", vendorLocation.getName());
            result.put("locationEmail", vendorLocation.getLocationEmail());
            result.put("address1", location.getAddress1());
            result.put("address2", location.getAddress2());
            result.put("city", location.getCity());
            result.put("province", location.getProvince());
            result.put("postalCode", location.getPostalOrZipcode());
            result.put("country", location.getCountry());
            result.put("phoneNumber", vendorLocation.getPhoneNumber());
            result.put("email", vendorLocation.getLocationEmail());
            result.put("mainOffice", vendorLocation.isMainOffice());

            HashMap<String, Object> agent = new HashMap<>();
            agent.put("firstName", user.getPersona().getFirstName());
            agent.put("lastName", user.getPersona().getLastName());
            agent.put("phoneNumber", user.getPersona().getPhoneNumber());
            agent.put("title", user.getPersona().getTitle());
            agent.put("email", user.getPersona().getEmail());
            result.put("agent", agent);

            response = Response.ok(result).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }
}
