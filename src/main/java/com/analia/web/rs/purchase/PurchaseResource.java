package com.analia.web.rs.purchase;

import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Source;
import com.analia.common.model.User;
import com.analia.common.model.resultset.view.PurchaseDetailView;
import com.analia.common.util.Base26;
import com.analia.media.service.FileSystemServiceLocal;
import com.analia.purchase.service.PurchaseServiceLocal;
import com.analia.purchase.service.SourceServiceLocal;
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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/rs/purchase")
@ApplicationScoped
public class PurchaseResource extends AnaliaResource {
    @Inject
    private SourceServiceLocal sourceServiceLocal;

    @Inject
    private PurchaseServiceLocal purchaseServiceLocal;

    @Inject
    private FileSystemServiceLocal fileSystemServiceLocal;


    @POST
    @Path("/source/add.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addSource(Map<String, Object> input) {

        Response response = null;
        try {
            String sourceCardHolderName = RequestUtils.getMandatoryStringForKey("sourceHolderName", input);
            String nickname = RequestUtils.getMandatoryStringForKey("nickname", input);
            String processorToken = RequestUtils.getMandatoryStringForKey("processorToken", input);
            String phoneNumber = RequestUtils.getMandatoryStringForKey("phoneNumber", input);
            Source source = sourceServiceLocal.addSource(sourceCardHolderName, nickname, processorToken, phoneNumber);
            response = Response.ok(ResponseUtils.createSucessResponse()).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }


    @POST
    @Path("/source/all.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getSources(Map<String, Object> input) {
        Response response = null;
        try {
            List<Source> sources = sourceServiceLocal.getSources();
            List<HashMap<String, Object>> listSources = new ArrayList<>(sources.size());
            User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
            for (Source source : sources) {
                HashMap<String, Object> source1 = new HashMap<>();
                source1.put("sourceCode", Base26.encode(source.getId()));
                source1.put("fullName", source.getHolderName());
                source1.put("nickname", source.getNickname());
                source1.put("sourceTypeCode", Base26.encode(source.getSourcetypeId()));
                source1.put("phoneNumber", user.getPersona().getPhoneNumber());
                source1.put("default", source.isDefaultCard());
                listSources.add(source1);
            }
            response = Response.ok(listSources).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;

    }


    @POST
    @Path("/order/all.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getOrder(Map<String, Object> input) {
        Response response = null;
        try {
            List<PurchaseDetailView> purchaseDetailViews = purchaseServiceLocal.getPurchaseListByUser();
            List<HashMap<String, Object>> listPurchaseDetails = new ArrayList<>(purchaseDetailViews.size());
            for (PurchaseDetailView purchaseDetailView : purchaseDetailViews) {
                HashMap<String, Object> purchaseDetail = new HashMap<>();
                purchaseDetail.put("purchaseCode", Base26.encode(purchaseDetailView.getPurchaseId()));
                purchaseDetail.put("purchaseDetailCode", Base26.encode(purchaseDetailView.getId()));
                purchaseDetail.put("amountPaid", purchaseDetailView.getAmountPaid());
                purchaseDetail.put("totalTaxPaid", purchaseDetailView.getTotalTaxPaid());
                purchaseDetail.put("voucher", purchaseDetailView.getDigitalVoucher());
                purchaseDetail.put("voucherVendorLocationCode", Base26.encode(purchaseDetailView.getVendorLocationVoucherId()));
                purchaseDetail.put("total", purchaseDetailView.getTotalVouchers());
                purchaseDetail.put("terms", purchaseDetailView.getTerms());
                purchaseDetail.put("media", buildResponseForMedia(fileSystemServiceLocal, purchaseDetailView.getVoucherDirectoryId(), 0));
                listPurchaseDetails.add(purchaseDetail);
            }
            response = Response.ok(listPurchaseDetails).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }

}