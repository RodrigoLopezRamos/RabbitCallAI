package com.analia.web.rs.user;

import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.*;
import com.analia.common.util.Base26;
import com.analia.media.service.FileSystemServiceLocal;
import com.analia.notification.service.NotificationEmailServiceLocal;
import com.analia.setttings.core.impl.SettingsCore;
import com.analia.setttings.service.impl.SettingsService;
import com.analia.user.service.UserService;
import com.analia.web.rs.AnaliaResource;
import com.analia.web.util.HeadersUtil;
import com.analia.web.util.RequestUtils;
import com.analia.web.util.ResponseUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/user-profile")
@ApplicationScoped
public class UserProfileResource extends AnaliaResource {

    private static final Log LOG = LogFactory.getLog(UserProfileResource.class);
    @Inject
    private FileSystemServiceLocal fileSystemServiceLocal;

    @Inject
    private UserService userServiceLocal;

    @Inject
    private NotificationEmailServiceLocal notificationEmailServiceLocal;

    @Inject
    private SettingsService settingsServiceLocal;

    /**
     * @return
     */
    @Path("/detail.s")
    @POST
    @Produces("application/json")
    public Response getUser() {
        Response response = null;
        try {
            User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
            UserDirectory userDirectory = userServiceLocal.getUserDirectory();
            UserRole userRole = userServiceLocal.getUserRole();
            File file = fileSystemServiceLocal.getFileWithDirectoryIdAndIndex(userDirectory.getDirectoryId(), 0);
            String referralCodePrefix = settingsServiceLocal.getStringValueForSettingKey(SettingsCore.SYSTEM_USER_REFERRAL_CODE_PREFIX);

            HashMap<String, Object> result = new HashMap<>();
            result.put("firstName", user.getPersona().getFirstName());
            result.put("lastName", user.getPersona().getLastName());
            result.put("userName", user.getPersona().getName());
            result.put("email", user.getPersona().getEmail());
            result.put("dateOfBirth", user.getPersona().getDateOfBirth());
            result.put("roleCode", Base26.encode(userRole.getRoleId()));
            result.put("phoneNumber", user.getPersona().getPhoneNumber());
            result.put("directoryCode", Base26.encode(userDirectory.getDirectoryId()));
            result.put("referralCode", referralCodePrefix + Base26.encode(user.getId()));
            result.put("media", buildResponseForMedias(fileSystemServiceLocal, userDirectory.getDirectoryId()));

            if (file != null) {
                result.put("profilePicture", fileSystemServiceLocal.getMediaUrl(file.getPath()));
            }

            response = Response.ok(result).build();

        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }

    @Path("/setusername.s")
    @POST
    @Produces("application/json")
    public Response setUsername(Map<String, Object> reqBody) {
        Response response = null;
        try {
            this.userServiceLocal.changeUsername(RequestUtils.getMandatoryStringForKey("newUsername", reqBody));
            response = Response.ok(ResponseUtils.createSucessResponse()).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }

    @Path("/setemail.s")
    @POST
    @Produces("application/json")
    public Response setUserEmail(Map<String, Object> reqBody) {
        Response response = null;
        try {
            User user = this.userServiceLocal.changeEmail(RequestUtils.getMandatoryStringForKey("newEmail", reqBody));
            this.notificationEmailServiceLocal.sendConfirmationEmail(user.getPersona().getEmail(), user.getConfirmationCode(), user.getPersona().getName());
            response = Response.ok(ResponseUtils.createSucessResponse()).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }

    @Path("/termsofuseurl.s")
    @POST
    @Produces("application/json")
    public Response getTermsUrl() {
        Response response = null;
        try {
            String termOfUrl = settingsServiceLocal.getStringValueForSettingKey(SettingsCore.SYSTEM_USER_TERMS_URL);
            HashMap<String, String> jsonResult = new HashMap<>();
            jsonResult.put("termsOfUseUrl", termOfUrl);
            response = Response.ok(jsonResult).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }

    @Path("/forgotpassword.s")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response forgotpassword(Map<String, Object> reqBody) {
        Response response = null;
        try {
            String email = RequestUtils.getMandatoryStringForKey("emailAddress", reqBody);
            String resetCode = this.userServiceLocal.forgotPassword(email);
            resetCode = URLEncoder.encode(resetCode, StandardCharsets.UTF_8);
            this.notificationEmailServiceLocal.sendForgotPasswordEmail(email, resetCode, email);
            response = Response.ok(ResponseUtils.createSucessResponse()).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }

    /**
     * Get an array of all keys related to the user profile
     *
     * @return
     */
    @Path("/getUserProfile.s")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response getUserProfileAllKeys() {
        Response response = null;
        try {
            List<UserProfile> userProfiles = userServiceLocal.getUserProfile();
            response = Response.ok(ResponseUtils.createJsonForUserProfileKeys(userProfiles)).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }

    /**
     * @param listMap
     * @return
     */
    @Path("/setUserProfile.s")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response setUserProfile(List<Map<String, Object>> listMap) {
        Response response = null;
        try {
            int maxKeys = 100; // TODO change by a setting
            if (listMap.size() > maxKeys) {
                throw new AnaliaException(ExceptionCode.EXCEEDED_SET_PROFILE_KEY_LIMIT, "Invalid amount of profile keys");
            }
            if (listMap == null || listMap.isEmpty()) {
                response = Response.status(400).build();
                return response;
            }
            for (Map<String, Object> element : listMap) {
                userServiceLocal.saveUserProfile(ResponseUtils.getUserProfile(element)); //FIXME  Really! sending multiples request to database in a loop ?
            }
            HashMap<String, Object> jsonResponse = new HashMap<String, Object>();
            jsonResponse.put("successMessage", "profile saved!");
            response = Response.ok(jsonResponse).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }

        return response;
    }


    /**
     * Get an array of all keys related to the user profile
     *
     * @return
     */
    @Path("/getUserTags.s")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response getUserTags() {
        Response response = null;
        try {
            List<Tag> tags = userServiceLocal.getUserTags();
            response = Response.ok(tags).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }

    /**
     * @param tags
     * @return
     */
    @Path("/setUserTags.s")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response setUserTags(@NotNull List<String> tags) {
        Response response = null;
        try {
            int maxKeys = 200; // TODO change by a setting
            if (tags.size() > maxKeys) {
                throw new AnaliaException(ExceptionCode.EXCEEDED_SET_PROFILE_KEY_LIMIT, "Invalid amount of profile keys");
            }
            if (tags.isEmpty()) {
                response = Response.status(400).build();
                return response;
            }
            Map<String, Object> jsonResponse = new HashMap<String, Object>();
            jsonResponse.put("successMessage", "tags saved!");
            userServiceLocal.setUserTags(tags);
            response = Response.ok(jsonResponse).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }

        return response;
    }


    /**
     * @param map
     * @return
     */
    @Path("/changePassword.s")
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response changePassword(Map<String, Object> map, @Context HttpServletResponse httpResponse) {
        Response response = null;
        try {
            String oldPassword = (String) map.get("oldPassword");
            String newPassword = (String) map.get("newPassword");
            User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
            userServiceLocal.changePassword(oldPassword, newPassword);
            httpResponse.setHeader(HeadersUtil.HEADER_ROLE_CODE, Base26.encode(user.getId()));
            response = Response.ok(ResponseUtils.createSucessResponse()).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }

    /**
     * @return
     */
    @Path("/deactivate.s")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response deactiveAccount() {
        Response response = null;
        try {
            userServiceLocal.deactiveAccount();
            HashMap<String, Object> jsonResponse = new HashMap<String, Object>();
            jsonResponse.put("successMessage", "Bye bye . Your account is deactive!");
            response = Response.ok(jsonResponse).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }

    /**
     * @param map
     * @return
     */
    @Path("/password-reset.n")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response passwordReset(Map<String, Object> map) {
        Response response = null;
        try {
            String resetCode = RequestUtils.getMandatoryStringForKey("resetCode", map);
            if (resetCode == null) {
                throw new AnaliaException(ExceptionCode.PARAMETER_IS_MANDATORY, "Reset Code ");
            }
            this.userServiceLocal.resetPassword(resetCode);
            response = Response.ok(ResponseUtils.createSucessResponse()).build();
        } catch (AnaliaException e) {
            LOG.error(e.getMessage(), e);
        }
        return response;
    }

    /**
     * @param resetCode
     * @return
     */
    @Path("/password-reset")
    @GET
    @Produces("text/html")
    public Response passwordReset(@QueryParam("resetCode") String resetCode) {
        Response response = null;
        try {
            if (resetCode == null) {
                throw new AnaliaException(ExceptionCode.PARAMETER_IS_MANDATORY, "Reset Code ");
            }
            String code = this.userServiceLocal.resetPassword(resetCode);
            response = Response.seeOther(new URI(code)).build();
        } catch (AnaliaException e) {
            LOG.error(e.getMessage(), e);
            String error = "error: REDIRECT URL";
            response = Response.ok(error).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            LOG.error(e.getMessage(), e);
            String error = "error: REDIRECT URL";
            response = Response.ok(error).build();
        }
        return response;
    }

    /**
     * { "fullName":"Rodrigo Lopez", "address1":"", "address2":"",
     * "country":"Canada", "province":"Canada", "city":"Toronto", "zip":"M5M4N6",
     * "phoneNumber":"6477853077" }
     *
     * @param map
     * @return
     */
    @Path("/shipping-address/add.s")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response addShippingAddress(HashMap<String, Object> map) {
        Response response = null;

        response = Response.ok(ResponseUtils.createSucessResponse()).build();

        return response;
    }

    /**
     * [ { "shippingAddressCode":"4", "fullName":"Rodrigo Lopez", "address1":"",
     * "address2":"", "country":"Canada", "province":"Canada", "city":"Toronto",
     * "zip":"M5M4N6", "phoneNumber":"6477853077", "default":true }, {
     * "shippingAddressCode":"R", "fullName":"Rodrigo Lopez Ramos",
     * "address1":"", "address2":"", "country":"Colombia", "province":"Valle",
     * "city":"Cali", "zip":"121331", "phoneNumber":"6477853077", "default":false
     * } ]
     *
     * @param map
     * @return
     */
    @Path("/shipping-address/all.s")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response getShippingAddresses(Map<String, Object> map) {
        Response response = null;
        List<HashMap<String, Object>> listAddress = new ArrayList<>();
        HashMap<String, Object> address1 = new HashMap<>();
        //  address1.put("shippingAddressCode", Base26.encode(12));
        address1.put("fullName", "Speddy Gonzalez");
        address1.put("address1", "256 Michigan Street");
        address1.put("address2", "");
        address1.put("country", "CANADA");
        address1.put("province", "Ontario");
        address1.put("city", "Toronto");
        address1.put("zipOrPostalCode", "M5M4N6");
        address1.put("phoneNumber", "6477853077");
        address1.put("default", false);

        HashMap<String, Object> address2 = new HashMap<>();
        //  address2.put("shippingAddressCode", Base26.encode(192));
        address2.put("fullName", "Speddy Gonzalez");
        address2.put("address1", "256 Metro Street");
        address2.put("address2", "");
        address2.put("country", "CANADA");
        address2.put("province", "Ontario");
        address2.put("city", "Toronto");
        address2.put("zipOrPostalCode", "M5M4N6");
        address2.put("phoneNumber", "6477853077");
        address2.put("default", true);

        listAddress.add(address2);
        listAddress.add(address1);

        response = Response.ok(listAddress).build();

        return response;
    }

}
