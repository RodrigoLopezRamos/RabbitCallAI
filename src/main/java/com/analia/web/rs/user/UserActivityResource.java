package com.analia.web.rs.user;

import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.model.ShoppingCart;
import com.analia.common.model.User;
import com.analia.common.model.resultset.NotificationResultSet;
import com.analia.common.model.resultset.ShoppingCartResultSet;
import com.analia.common.model.resultset.view.VoucherDetailView;
import com.analia.common.util.Base26;
import com.analia.media.service.FileSystemServiceLocal;
import com.analia.notification.service.NotificationEmailServiceLocal;
import com.analia.purchase.service.PurchaseServiceLocal;
import com.analia.user.service.UserActivityService;
import com.analia.user.service.UserService;
import com.analia.web.rs.AnaliaResource;
import com.analia.web.util.RequestUtils;
import com.analia.web.util.ResponseUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/user-activity")
@ApplicationScoped
public class UserActivityResource extends AnaliaResource {
    private static final String USER_CODE = "userCode";
    @Inject
    private UserService userServiceLocal;

    @Inject
    private PurchaseServiceLocal purchaseServiceLocal;

    @Inject
    private FileSystemServiceLocal fileSystemServiceLocal;

    @Inject
    private NotificationEmailServiceLocal notificationEmailServiceLocal;

    @Inject
    private UserActivityService userActivityServiceLocal;


    @POST
    @Path("/message/user.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getMYyMessages(Map<String, Object> input) {
        Response response = null;
        try {
            BigInteger userId = RequestUtils.getStringForKey(USER_CODE, input) != null ? Base26.decode(RequestUtils.getStringForKey(USER_CODE, input)) : null;
            List<NotificationResultSet> notificationResultSets = new ArrayList<>();
            if (userId != null) {
                notificationResultSets = userActivityServiceLocal.getChatNotificationsByUserId(userId);
            }
            response = Response.ok(notificationResultSets).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }


    @POST
    @Path("/favourited.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response favourited(Map<String, Object> input) {
        Response response = null;
        try {
            BigInteger tradeId = RequestUtils.getStringForKey("tradeLocationCode", input) != null ? Base26.decode(RequestUtils.getStringForKey("tradeLocationCode", input)) : null;
            BigInteger voucherId = RequestUtils.getStringForKey("voucherLocationCode", input) != null ? Base26.decode(RequestUtils.getStringForKey("voucherLocationCode", input)) : null;
            BigInteger storyId = RequestUtils.getStringForKey("storyCode", input) != null ? Base26.decode(RequestUtils.getStringForKey("storyCode", input)) : null;
            boolean loved = RequestUtils.getMandatoryBooleanForKey("favourited", input);
            userServiceLocal.saveUserTrending(tradeId, voucherId, storyId, loved);
            response = Response.ok(ResponseUtils.createSucessResponse()).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }

    @POST
    @Path("/manageCart.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response manageCart(Map<String, Object> input) {
        Response response = null;
        try {
            String voucherCode = RequestUtils.getStringForKey("voucherCode", input);
            int amount = RequestUtils.getMandatoryIntForKey("amount", input);
            BigInteger voucherId = Base26.decode(voucherCode);
            ShoppingCart shoppingCartResultSet = userServiceLocal.manageCart(voucherId, BigInteger.valueOf(amount));
            Map<String, Object> map = ResponseUtils.createSucessResponse();
            if (shoppingCartResultSet != null) {
                map.put("quantity", shoppingCartResultSet.getQuantity());
            }

            response = Response.ok(map).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;

    }


    @POST
    @Path("/checkout.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response checkout(Map<String, Object> input) {
        Response response = null;
        try {
            User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
            String sourceCode = RequestUtils.getStringForKey("sourceCode", input);
            boolean creditUsed = RequestUtils.getMandatoryBooleanForKey("creditUsed", input);
            String purchase = purchaseServiceLocal.checkout(sourceCode != null ? Base26.decode(sourceCode) : null, creditUsed);

            notificationEmailServiceLocal.sendEmail(user.getPersona().getEmail(), "Su pedido esta confirmado! Electrojaponesa", purchase, "text/html");
            response = Response.ok(ResponseUtils.createSucessResponse()).build();
            //.sendEmail(user.getEmail(),"Invoice PotyLand",new ByteArrayInputStream(purchase.getBytes("UTF-8"),"text/html"));
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }


    @Path("/friend/all.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response follow(Map<String, Object> input) {
        Response response = null;
        try {
            Integer tradeLocationId = RequestUtils.getIntegerForKey("tradeLocationCode", input);
            boolean creditUsed = RequestUtils.getMandatoryBooleanForKey("creditUsed", input);
            response = Response.ok(getCartSummary(creditUsed)).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }


    @POST
    @Path("/cart.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getUserCart(Map<String, Object> input) {
        Response response = null;
        try {
            boolean creditUsed = RequestUtils.getMandatoryBooleanForKey("creditUsed", input);
            response = Response.ok(getCartSummary(creditUsed)).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }

    /**
     * @param creditUsed
     * @return
     * @throws AnaliaException
     */
    private Map<String, Object> getCartSummary(boolean creditUsed) throws AnaliaException {
        HashMap<String, Object> result = new HashMap<>();
        List<HashMap<String, Object>> vouchers = new ArrayList<>();
        List<ShoppingCartResultSet> voucherDetailViews = purchaseServiceLocal.getPurchaseSummary(creditUsed);
        ShoppingCartResultSet grandShoppingCartResultSet = null;
        BigInteger grandTotalVouchers = BigInteger.valueOf(0);
        for (ShoppingCartResultSet shoppingCartResultSet : voucherDetailViews) {
            grandShoppingCartResultSet = shoppingCartResultSet;
            HashMap<String, Object> voucher = new HashMap<>();
            VoucherDetailView voucherDetailView = shoppingCartResultSet.getVoucherDetailView();
            grandTotalVouchers.add(shoppingCartResultSet.getQuantity());
            voucher.put("quantity", shoppingCartResultSet.getQuantity());
            voucher.put("remainingInventory", shoppingCartResultSet.getRemainingInventory());
            voucher.put("remainingAllowance", shoppingCartResultSet.getRemainingAllowance());
            voucher.put("maximumPerUser", voucherDetailView.getMaximumPerUser());
            voucher.put("voucherVendorLocationCode", Base26.encode(voucherDetailView.getVendorLocationVoucherId()));
            voucher.put("voucherCode", Base26.encode(voucherDetailView.getVoucherId()));
            voucher.put("title", voucherDetailView.getTitle());
            voucher.put("description", voucherDetailView.getDescription());
            voucher.put("price", voucherDetailView.getPrice());
            voucher.put("creditsReward", voucherDetailView.getPurchaseCreditsReward());
            voucher.put("retailValue", voucherDetailView.getRetailValue());
            voucher.put("shortDescription", voucherDetailView.getShortDescription());
            voucher.put("termsUrl", voucherDetailView.getTermsUrl());
            voucher.put("website", voucherDetailView.getWebsite());
            voucher.put("finePrint", voucherDetailView.getFinePrint());
            voucher.put("unlockingCredits", voucherDetailView.getUnlockingCredits());
            voucher.put("instructions", voucherDetailView.getInstructions());
            voucher.put("subTotal", shoppingCartResultSet.getSubTotal().setScale(2, RoundingMode.UP));// 1
            voucher.put("tax", shoppingCartResultSet.getTaxToPaid().setScale(2, RoundingMode.UP));// 1
            voucher.put("total", shoppingCartResultSet.getTotal().setScale(2, RoundingMode.UP));// 1
            voucher.put("media", buildResponseForMedia(fileSystemServiceLocal, voucherDetailView.getVoucherDirectoryId(), 1));
            vouchers.add(voucher);
        }
        if (grandShoppingCartResultSet != null) {
            result.put("grandSubtotal", grandShoppingCartResultSet.getGrandSubTotal().setScale(2, RoundingMode.UP));
            result.put("grandTaxTotal", grandShoppingCartResultSet.getGrandTaxToPaid().setScale(2, RoundingMode.UP));
            result.put("grandTotal", grandShoppingCartResultSet.getGrandTotal().setScale(2, RoundingMode.UP));
        }
        result.put("grandTotalVouchers", grandTotalVouchers);
        result.put("vouchers", vouchers);

        return result;
    }


}
