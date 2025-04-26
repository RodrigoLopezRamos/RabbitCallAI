package com.analia.web.rs.vendor;

import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.model.*;
import com.analia.common.model.resultset.view.AccountUserView;
import com.analia.common.model.resultset.view.VendorLocationDetailView;
import com.analia.common.util.Base26;
import com.analia.media.service.FileSystemServiceLocal;
import com.analia.metadata.service.MetaDataServiceLocal;
import com.analia.notification.service.NotificationEmailServiceLocal;
import com.analia.purchase.service.PurchaseServiceLocal;
import com.analia.purchase.service.PurchaseVendorServiceLocal;
import com.analia.trade.service.TradeServiceLocal;
import com.analia.user.service.UserActivityService;
import com.analia.user.service.UserService;
import com.analia.vendor.service.VendorServiceLocal;
import com.analia.voucher.service.VoucherServiceLocal;
import com.analia.web.util.HeadersUtil;
import com.analia.web.util.RequestUtils;
import com.analia.web.util.ResponseUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;


import java.math.BigInteger;
import java.util.*;

/**
 * @author Rodrigo Lopez
 * MagnetarLabs Corporation
 * 2016 -2017
 */
@Path("/vendor")
@ApplicationScoped
public class VendorResource {
    @Inject
    private VendorServiceLocal vendorServiceLocal;
    @Inject
    private PurchaseServiceLocal purchaseServiceLocal;
    @Inject
    private PurchaseVendorServiceLocal purchaseVendorServiceLocal;
    @Inject
    private UserService userServiceLocal;
    @Inject
    private UserActivityService userActivityServiceLocal;
    @Inject
    private VoucherServiceLocal voucherServiceLocal;
    @Inject
    private TradeServiceLocal tradeServiceLocal;
    @Inject
    private FileSystemServiceLocal fileServiceLocal;
    @Inject
    private MetaDataServiceLocal metaDataServiceLocal;
    @Inject
    private NotificationEmailServiceLocal notificationEmailServiceLocal;


    /**
     * @param map
     * @param httpServletRequest
     * @return
     */
    @Path("/login.n")
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response login(Map<String, Object> map, @Context HttpServletRequest httpServletRequest) {
        Response response = null;
        try {
            String userIdentifier = RequestUtils.getStringForKey("userIdentifier", map);
            String token = RequestUtils.getStringForKey("token", map);
            User user = userServiceLocal.authenticateUser(User.USER_TYPE_EMAIL_ID, userIdentifier, token);
            UserRole userRole = userServiceLocal.getUserRole();

            BigInteger vendorId = userRole.getVendorId();
            List<VendorLocationDetailView> listMerchantLocations = vendorServiceLocal.getAllVendorLocationDetailViewForVendor(vendorId);
            HashMap<String, Object> result = new HashMap<>();
            result.put("defaultLocationSet", listMerchantLocations.size() <= 1);
            result.put("vendorCode", Base26.encode(listMerchantLocations.get(0).getVendorId()));
            result.put("token", user.getSessionToken());
            result.put("roleCode", Base26.encode(userRole.getRoleId()));
            result.put("firstName", user.getPersona().getFirstName());
            result.put("lastName", user.getPersona().getLastName());

            List<HashMap<String, Object>> locationResultList = new ArrayList<>();
            for (VendorLocationDetailView vendorLocationDetailView : listMerchantLocations) {
                HashMap<String, Object> locationResult = new HashMap<>();
                locationResult.put("locationCode", Base26.encode(vendorLocationDetailView.getVendorlocationId()));
                locationResult.put("locationName", vendorLocationDetailView.getVendorLocationName());
                locationResult.put("zoneCode", Base26.encode(vendorLocationDetailView.getVendorlocationId()));
                locationResultList.add(locationResult);
            }
            result.put("locations", locationResultList);
            response = Response.ok(result).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }


    @Path("/signUp.s")
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response signUpVendor(Map<String, Object> payload, @Context HttpServletRequest httpServletRequest) {
        Response response = null;
        try {
            BigInteger userTypeId = Base26.decode(RequestUtils.getStringForKey("userTypeCode", payload));
            String userEmail = RequestUtils.getMandatoryStringForKey("userEmail", payload);
            String userName = RequestUtils.getMandatoryStringForKey("userName", payload);
            String token = RequestUtils.getMandatoryStringForKey("token", payload);
            String externalUserId = RequestUtils.getStringForKey("externalUserId", payload);
            String firstName = RequestUtils.getStringForKey("firstName", payload);
            String lastName = RequestUtils.getStringForKey("lastName", payload);
            String phoneNumber = RequestUtils.getStringForKey("phoneNumber", payload);
            String dateOfBirth = RequestUtils.getValueForKey("dateOfBirth", payload, String.class);

            String title = RequestUtils.getMandatoryStringForKey("title", payload);
            String description = RequestUtils.getMandatoryStringForKey("description", payload);
            String address = RequestUtils.getMandatoryStringForKey("address", payload);
            String website = RequestUtils.getMandatoryStringForKey("website", payload);
            String city = RequestUtils.getMandatoryStringForKey("city", payload);
            String province = RequestUtils.getMandatoryStringForKey("province", payload);
            String hoursofoperations = RequestUtils.getMandatoryStringForKey("hoursofoperations", payload);//TODO Finish business operations component
            String postalOrZipcode = RequestUtils.getMandatoryStringForKey("postalOrZipcode", payload);
            String country = RequestUtils.getMandatoryStringForKey("country", payload);
            double latitude = RequestUtils.getMandatoryDoubleForKey("latitude", payload);
            double longitude = RequestUtils.getMandatoryDoubleForKey("longitude", payload);

            BigInteger bussinesScheduleId = new BigInteger("0");

            User user = userServiceLocal.signUp(userTypeId.intValue(), firstName, lastName, userName, userEmail, token, externalUserId, phoneNumber, new Date(), "vendor");//TODO birth of day

            AccountUser vendor = vendorServiceLocal.signUpVendor(user.getPersona().getName(), title, userEmail, description, address, website, city, province, phoneNumber, postalOrZipcode, country, latitude, longitude, bussinesScheduleId, user.getId());

            if (userTypeId.intValue() == User.USER_TYPE_EMAIL_ID) {
                notificationEmailServiceLocal.sendConfirmationEmail(user.getPersona().getEmail(), user.getConfirmationCode(), user.getPersona().getName());
            }

            HashMap<String, Object> result = new HashMap<>();
            result.put("vendorCode", Base26.encode(vendor.getId()));
            response = Response.ok(result).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }

    /**
     * @param map
     * @param
     * @return
     */
    @Path("/setSessionLocation.s")
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response setSessionLocation(Map<String, Object> map) {
        Response response = null;
        try {
            vendorServiceLocal.setVendorLoginDefaultLocationForUserId(Base26.decode(RequestUtils.getStringForKey("locationCode", map)));
            response = Response.ok(ResponseUtils.createSucessResponse()).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }

    /***
     *
     * @return
     */
    @Path("/getVendorInfo.v")
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response getVendorInfo() {
        Response response = null;
        try {
            Vendor vendorInContext = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.VENDOR, Vendor.class);
            HashMap<String, Object> result = new HashMap<>();
            result.put("vendorCode", Base26.encode(vendorInContext.getId()));
            result.put("vendorName", vendorInContext.getName());
            List<VendorLocationDetailView> listVendorLocations = vendorServiceLocal.getAllVendorLocationDetailViewForVendor();

            List<HashMap<String, Object>> resultLocations = new ArrayList<HashMap<String, Object>>();
            for (VendorLocationDetailView vendorLocationDetailView : listVendorLocations) {
                HashMap<String, Object> resultLocation = new HashMap<>();
                resultLocation.put("locationCode", Base26.encode(vendorLocationDetailView.getVendorlocationId()));
                resultLocation.put("locationName", vendorLocationDetailView.getVendorLocationName());
                resultLocation.put("address1", vendorLocationDetailView.getAddress1());
                resultLocation.put("address2", vendorLocationDetailView.getAddress2());
                resultLocation.put("country", vendorLocationDetailView.getCountry());
                resultLocation.put("province", vendorLocationDetailView.getProvince());
                resultLocation.put("postalCode", vendorLocationDetailView.getPostalCode());
                resultLocation.put("phoneNumber", vendorLocationDetailView.getPhoneNumber());
                resultLocations.add(resultLocation);
            }
            result.put("locations", resultLocations);
            response = Response.ok(result).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }

    /**
     * @return
     */
    @Path("/getAccountRoles.v")
    @POST
    @Produces("application/json")
    public Response getAccountRoles() {
        Response response = null;
        try {
            List<Role> listRoles = vendorServiceLocal.getVendorListOfRoles();
            List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
            for (Role role : listRoles) {
                HashMap<String, Object> jsonRole = new HashMap<>();
                jsonRole.put("roleCode", Base26.encode(role.getId()));
                jsonRole.put("roleName", role.getName());
                jsonRole.put("roleDescription", role.getDescription());
                result.add(jsonRole);
            }
            response = Response.ok(result).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }

    /**
     * @return
     */
    @Path("/getAccountUserList.v")
    @POST
    @Produces("application/json")
    public Response getAccountuserList() {
        Response response = null;
        try {
            List<AccountUserView> listAccountUsers = vendorServiceLocal.getListAccountUserForVendor();
            List<HashMap<String, Object>> listAccountUsersInfo = new ArrayList<>();
            HashMap<BigInteger, HashMap<String, Object>> filterForUserMultiplesLocations = new HashMap<>();
            for (AccountUserView accountUserView : listAccountUsers) {
                HashMap<String, Object> account;
                if ((account = filterForUserMultiplesLocations.get(accountUserView.getUserId())) == null) {
                    account = new HashMap<>();
                    account.put("firstName", accountUserView.getFirstName());
                    account.put("lastName", accountUserView.getLastName());
                    account.put("userCode", Base26.encode(accountUserView.getUserId()));
                    account.put("email", accountUserView.getEmailAddress());
                    account.put("roleCode", Base26.encode(accountUserView.getRoleId()));
                    List<String> locationCodeList = new ArrayList<String>();
                    locationCodeList.add(Base26.encode(accountUserView.getVendorLocationId()));
                    account.put("locationCode", locationCodeList);
                    filterForUserMultiplesLocations.put(accountUserView.getId(), account);
                    listAccountUsersInfo.add(account);
                } else {
                    @SuppressWarnings("unchecked")
                    List<String> locationCodeList = (List<String>) account.get("locationCode");
                    locationCodeList.add(Base26.encode(accountUserView.getVendorLocationId()));
                    account.put("locationCodes", locationCodeList);
                }
            }
            response = Response.ok(listAccountUsersInfo).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }

    /**
     * @param map
     * @return
     */
    @Path("/removeAccountUser.v")
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response removeAccountuser(Map<String, Object> map) {
        Response response = null;
        try {
            String userCode = RequestUtils.getMandatoryStringForKey("userCode", map);
            BigInteger userId = Base26.decode(userCode);
            vendorServiceLocal.disableAccountUser(userId);
            response = Response.ok(ResponseUtils.createSucessResponse()).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }

    /**
     * @param map
     * @return
     */
    @Path("/saveAccountUser.v")
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @SuppressWarnings("unchecked")
    public Response saveAccountuser(Map<String, Object> map) {
        Response response = null;
        try {
            String userEmail = RequestUtils.getMandatoryStringForKey("userEmail", map);
            String roleCode = RequestUtils.getMandatoryStringForKey("roleCode", map);
            String firstName = RequestUtils.getMandatoryStringForKey("firstName", map);
            String lastName = RequestUtils.getMandatoryStringForKey("lastName", map);
            String password = RequestUtils.getStringForKey("password", map);
            String oldPassword = RequestUtils.getStringForKey("oldPassword", map);
            String userCode = RequestUtils.getStringForKey("userCode", map);
            String dateOfBirth = RequestUtils.getMandatoryStringForKey("dateOfBirth", map);//TODO FORMAT DATE
            String phoneNumber = RequestUtils.getMandatoryStringForKey("phoneNumber", map);
            String title = RequestUtils.getStringForKey("title", map);


            BigInteger userId = userCode != null ? Base26.decode(userCode) : null;

            BigInteger[] locationIds = null;
            List<String> locationCodes = RequestUtils.getValueForKey("locationCodes", map, List.class);

            if (locationCodes != null) {
                locationIds = new BigInteger[locationCodes.size()];
                int i = 0;
                for (String locationCode : locationCodes) {
                    BigInteger locationId = Base26.decode(locationCode);
                    locationIds[i] = locationId;
                    i++;
                }
            }
            BigInteger roleId = Base26.decode(roleCode);
            vendorServiceLocal.saveAccountUserByVendor(userId, roleId, title, firstName, lastName, userEmail, oldPassword, password, phoneNumber, new Date(), locationIds);
            response = Response.ok(ResponseUtils.createSucessResponse()).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }

    /**
     * @param map
     * @return
     */
    @Path("/saveAccountInfo.v")
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @SuppressWarnings("unchecked")
    public Response saveAccountInfo(Map<String, Object> map) {
        Response response = null;
        try {
            String roleCode = RequestUtils.getMandatoryStringForKey("roleCode", map);
            String userCode = RequestUtils.getMandatoryStringForKey("userCode", map);

            List<String> locationCodes = RequestUtils.getValueForKey("locationCodes", map, List.class);
            BigInteger[] locationIds = new BigInteger[locationCodes.size()];
            int i = 0;
            for (String locationCode : locationCodes) {
                BigInteger locationId = Base26.decode(locationCode);
                locationIds[i] = locationId;
                i++;
            }
            BigInteger roleId = Base26.decode(roleCode);
            BigInteger userId = Base26.decode(userCode);
            vendorServiceLocal.saveAccountInfoByVendor(userId, roleId, locationIds);
            response = Response.ok(ResponseUtils.createSucessResponse()).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }

    /**
     * @param map
     * @return
     */
    @Path("/changePassword.v")
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response changePassword(Map<String, Object> map) {
        Response response = null;
        try {
            String oldPassword = RequestUtils.getMandatoryStringForKey("oldPassword", map);
            String newPassword = RequestUtils.getMandatoryStringForKey("newPassword", map);
            userServiceLocal.changePassword(oldPassword, newPassword);
            response = Response.ok(ResponseUtils.createSucessResponse()).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }
}
