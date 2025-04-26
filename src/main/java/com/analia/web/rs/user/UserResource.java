package com.analia.web.rs.user;

import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.*;
import com.analia.common.model.resultset.TradeResultSet;
import com.analia.common.model.resultset.VoucherResultSet;
import com.analia.common.util.Base26;
import com.analia.common.util.DateUtils;
import com.analia.location.service.LocationServiceLocal;
import com.analia.notification.service.NotificationEmailServiceLocal;
import com.analia.trade.service.TradeServiceLocal;
import com.analia.user.service.UserService;
import com.analia.voucher.service.VoucherServiceLocal;
import com.analia.web.rs.AnaliaResource;
import com.analia.web.util.RequestUtils;
import com.analia.web.util.ResponseUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/user")
@ApplicationScoped
public class UserResource extends AnaliaResource {
    private static final Log LOG = LogFactory.getLog(UserResource.class);


    @Inject
    private UserService userServiceLocal;

    @Inject
    private TradeServiceLocal tradeServiceLocal;

    @Inject
    private VoucherServiceLocal voucherServiceLocal;

    @Inject
    private LocationServiceLocal locationServiceLocal;

    @Inject
    private NotificationEmailServiceLocal notificationEmailServiceLocal;

    /**
     * @param input
     * @return
     */
    @POST
    @Path("/guest.n")
    @Consumes("application/json")
    @Produces("application/json")
    public Response authenticateGuest(HashMap<String, Object> input) {
        Response response = null;
        try {
            User user = userServiceLocal.authenticateGuest();
            UserRole userRole = userServiceLocal.getUserRole();
            HashMap<String, Object> jsonResponse = new HashMap<String, Object>();
            jsonResponse.put("token", user.getSessionToken());
            //  jsonResponse.put("roleCode", Base26.encode(userRole.getRoleId()));
            response = Response.ok(jsonResponse).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }

    /**
     * @param map
     * @return
     */
    @Path("/signUp.s")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response signUp(Map<String, Object> map) {
        Response response = null;
        try {
            int userTypeId = Base26.decode(RequestUtils.getStringForKey("userTypeCode", map)).intValue();
            String userEmail = RequestUtils.getMandatoryStringForKey("userEmail", map);
            String userName = RequestUtils.getMandatoryStringForKey("userName", map);
            String token = RequestUtils.getMandatoryStringForKey("token", map);
            String externalUserId = RequestUtils.getStringForKey("externalUserId", map);
            String firstName = RequestUtils.getStringForKey("firstName", map);
            String lastName = RequestUtils.getStringForKey("lastName", map);
            String phoneNumber = RequestUtils.getStringForKey("phoneNumber", map);
            String dateOfBirth = RequestUtils.getMandatoryStringForKey("dateOfBirth", map);
            String gender = RequestUtils.getMandatoryStringForKey("gender", map);

            User user = userServiceLocal.signUp(userTypeId, firstName, lastName, userName, userEmail, token, externalUserId, phoneNumber, DateUtils.formatDate(dateOfBirth), gender);
            UserRole userRole = userServiceLocal.getUserRole();
            UserDirectory userDirectory = userServiceLocal.getUserDirectory();


            Map<String, Object> jsonResponse = new HashMap<String, Object>();
            jsonResponse.put("token", user.getSessionToken());
            response = Response.ok(jsonResponse).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }


    /**
     * @return
     */
    @Path("/checkemail.n")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response checkEmail(Map<String, Object> map) {
        Response response = null;
        try {
            HashMap<String, Object> jsonResponse = new HashMap<String, Object>();
            String email = RequestUtils.getMandatoryStringForKey("emailAddress", map);
            String message = "email can be use!";
            int valid = 1;
            if (!userServiceLocal.isValidNewEmail(email)) {
                valid = 0;
                message = "email is taken or not well formed!";
            }
            jsonResponse.put("valid", valid);
            jsonResponse.put("message", message);
            response = Response.ok(jsonResponse).build();
        } catch (AnaliaException analiaException) {
            return ResponseUtils.buildErrorResponse(analiaException);
        }

        return response;
    }

    /**
     * @return
     */
    @Path("/confirmEmail/{c}")
    @GET
    public void validationemail(@PathParam("c") String confirmationCode, @Context HttpServletResponse httpServletResponse) {
        try {
            userServiceLocal.confirmUser(confirmationCode);
            // httpServletResponse.sendRedirect();
        } catch (AnaliaException e) {
            LOG.error(e);
        }
    }

    @Path("/confirmAccount/{c}")
    @GET
    @Consumes("application/json")
    @Produces("application/json")
    public Response confirmAccount(@PathParam("c") String confirmationCode) {
        Response response = null;
        try {
            userServiceLocal.confirmUser(confirmationCode);
            Map<String, Object> data = ResponseUtils.createSucessResponse();
            response = Response.ok(data).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }

    /**
     * @return
     */
    @Path("/resend-email.s")
    @POST
    public Response resendCode() {
        Response response = null;
        User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        try {
            UserRole userRole = userServiceLocal.getUserRole();
            if (user.getUserTypeId().intValue() != User.USER_TYPE_EMAIL_ID) {
                throw new AnaliaException(ExceptionCode.USER_WRONG_TYPE, "User Type is invalid");
            }
            if (userRole.getRoleId().intValue() == Role.ROLE_NOT_VALIDATED_USER) {
                userServiceLocal.sentSMS(user.getPersona().getPhoneNumber(), "Please re-enter code :" + user.getConfirmationCode());
                //.notificationEmailServiceLocal.sendConfirmationEmail(user.getPersona().getEmail(), user.getConfirmationCode(), user.getPersona().getName());
            }
            response = Response.ok(ResponseUtils.createSucessResponse()).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }

    /**
     * @return
//     */
//    @Path("/resend-email.s")
//    @POST
//    public Response resendEmail() {
//        Response response = null;
//        User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
//
//        try {
//            UserRole userRole = userServiceLocal.getUserRole();
//            if (user.getUserTypeId().intValue() != User.USER_TYPE_EMAIL_ID) {
//                throw new AnaliaException(ExceptionCode.USER_WRONG_TYPE, "User Type is invalid");
//            }
//            if (userRole.getRoleId().intValue() == Role.ROLE_NOT_VALIDATED_USER) {
//                this.notificationEmailServiceLocal.sendConfirmationEmail(user.getPersona().getEmail(), user.getConfirmationCode(), user.getPersona().getName());
//            }
//            response = Response.ok(ResponseUtils.createSucessResponse()).build();
//        } catch (AnaliaException e) {
//            response = ResponseUtils.buildErrorResponse(e);
//        }
//        return response;
//    }

    /**
     * @param map
     * @param httpServletRequest
     * @return
     */
    @Path("/loginUser.n")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response loginAsUser(Map<String, Object> map, @Context HttpServletRequest httpServletRequest) {
        Response response = null;
        try {
            String userTypeCode = httpServletRequest.getHeader("userTypeCode");
            int userType = Base26.decode(userTypeCode).intValue();
            String userIdentifier = (String) map.get("userIdentifier");
            String token = (String) map.get("token");


            User user = userServiceLocal.authenticateUser(userType, userIdentifier, token);

            UserRole userRole = userServiceLocal.getUserRole();

            UserDirectory userDirectory = userServiceLocal.getUserDirectory();
            Map<String, Object> jsonResponse = new HashMap<String, Object>();
            jsonResponse.put("userName", user.getPersona().getName());
            jsonResponse.put("firstName", user.getPersona().getFirstName());
            jsonResponse.put("lastName", user.getPersona().getLastName());
            jsonResponse.put("phone", user.getPersona().getPhoneNumber());
            jsonResponse.put("token", user.getSessionToken());
            jsonResponse.put("roleCode", Base26.encode(userRole.getRoleId()));
            jsonResponse.put("directoryCode", Base26.encode(userDirectory.getDirectoryId()));
            response = Response.ok(jsonResponse).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }


    /**
     * @param map
     * @param httpServletRequest
     * @return
     */
    @Path("login/token.n")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response loginUser(Map<String, Object> map, @Context HttpServletRequest httpServletRequest) {
        Response response = null;
        try {
            String userTypeCode = httpServletRequest.getHeader("userTypeCode");
            String token = (String) map.get("token");
            User user = userServiceLocal.externalUser(token);
            Map<String, Object> jsonResponse = new HashMap<String, Object>();
            jsonResponse.put("token", user.getSessionToken());
            response = Response.ok(jsonResponse).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }


    /**
     * @return
     */
    @Path("/logout.s")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response logout() {
        Response response = null;
        try {
            userServiceLocal.logout();
            response = Response.ok(ResponseUtils.createSucessResponse()).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }


    @Path("/setLocation.s")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response setLocation(Map<String, Object> input) {
        Response response = null;
        try {
            String zoneCode = RequestUtils.getStringForKey("zoneCode", input);
            BigInteger zoneId = Base26.decode(zoneCode);
            userServiceLocal.setUserLocation(0, 0, zoneId);
            response = Response.ok(ResponseUtils.createSucessResponse()).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }

    @POST
    @Path("/search.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response search(Map<String, Object> input) {
        Response response = null;
        try {
            String querySearchParam = RequestUtils.getStringForKey("querySearchParam", input);

            HashMap<String, Object> result = new HashMap<>();
            List<HashMap<String, Object>> resultListTrades = new ArrayList<>();

            List<VoucherResultSet> vouchers = voucherServiceLocal.getVouchers(querySearchParam, 0, 100);
            List<HashMap<String, Object>> resultListVoucher = new ArrayList<>();
            for (VoucherResultSet voucherResultSet : vouchers) {
                HashMap<String, Object> voucherJSON = new HashMap<>();
                voucherJSON.put("voucherLocationCode", Base26.encode(voucherResultSet.getId()));
                voucherJSON.put("title", voucherResultSet.getTitle());
                voucherJSON.put("categoryName", voucherResultSet.getCategoryName());
                voucherJSON.put("categoryCode", Base26.encode(voucherResultSet.getCategoryId()));
                resultListVoucher.add(voucherJSON);
            }

            List<TradeResultSet> trades = tradeServiceLocal.getTrades(querySearchParam, 0, 100);
            for (TradeResultSet tradeResultSet : trades) {
                HashMap<String, Object> tradeJSON = new HashMap<>();
                tradeJSON.put("tradeLocationCode", Base26.encode(tradeResultSet.getId()));
                tradeJSON.put("title", tradeResultSet.getTitle());
                resultListTrades.add(tradeJSON);
            }
            List<City> cities = locationServiceLocal.getCities(querySearchParam);
            List<HashMap<String, Object>> resultListCities = new ArrayList<>();
            for (City city : cities) {
                HashMap<String, Object> cityJSON = new HashMap<>();
                cityJSON.put("locationCode", Base26.encode(city.getId()));
                cityJSON.put("zoneCode", Base26.encode(city.getZoneId()));
                cityJSON.put("name", city.getName());
                resultListCities.add(cityJSON);
            }

            result.put("vouchers", resultListVoucher);
            result.put("trades", resultListTrades);
            result.put("cities", resultListCities);
            response = Response.ok(result).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }
}
