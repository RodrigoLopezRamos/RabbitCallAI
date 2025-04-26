package com.analia.web.rs.vendor;

import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.Tax;
import com.analia.common.model.VendorLocation;
import com.analia.common.model.VoucherType;
import com.analia.common.model.resultset.VoucherResultSet;
import com.analia.common.model.resultset.view.VoucherDetailView;
import com.analia.common.util.Base26;
import com.analia.common.util.DateUtils;
import com.analia.media.service.FileSystemServiceLocal;
import com.analia.setttings.service.impl.SettingsService;
import com.analia.vendor.service.VendorServiceLocal;
import com.analia.voucher.service.VoucherServiceLocal;
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Path("/vendor/voucher")
@ApplicationScoped
public class VendorVoucherResource extends AnaliaResource {

    @Inject
    private VoucherServiceLocal voucherServiceLocal;

    @Inject
    private VendorServiceLocal vendorServiceLocal;

    @Inject
    private FileSystemServiceLocal fileSystemServiceLocal;

//    @Inject
//    private SettingsService settingsServiceLocal;


    /**
     * @return
     */
    @Path("/all.v")
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response getVouchers(HashMap<String, Object> input) {
        Response response = null;
        try {
            String categoryCode = RequestUtils.getMandatoryStringForKey("categoryCode", input);
            String voucherTypeCode = RequestUtils.getMandatoryStringForKey("voucherTypeCode", input);

            BigInteger categoryId = Base26.decode(categoryCode);
            BigInteger voucherTypeId = Base26.decode(voucherTypeCode);
            int page = RequestUtils.getMandatoryIntForKey("page", input);
            Integer pageSize = RequestUtils.getIntegerForKey("pageSize", input);
            Integer sortType = RequestUtils.getIntegerForKey("sortType", input);

            VendorLocation vendorLocation = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.VENDOR_LOCATION, VendorLocation.class);
            List<VoucherResultSet> vouchers = voucherServiceLocal.getVouchersByVendorLocationId(vendorLocation.getId(), voucherTypeId, categoryId, page, pageSize, sortType);
            List<HashMap<String, Object>> resultList = new ArrayList<>(vouchers.size());
            for (VoucherResultSet voucher : vouchers) {
                HashMap<String, Object> result = new HashMap<>();
                result.put("voucherVendorLocationCode", Base26.encode(voucher.getVoucherVendorLocationId()));
                result.put("voucherTypeCode", Base26.encode(voucher.getVoucherTypeId()));
                result.put("zoneCode", Base26.encode(voucher.getZoneId()));
                result.put("media", buildResponseForMedia(fileSystemServiceLocal, voucher.getDirectoryId(), 1));
                result.put("title", voucher.getTitle());
                result.put("latitude", voucher.getLatitude());
                result.put("longitude", voucher.getLongitude());
                result.put("disabled", voucher.isDisabled());

                resultList.add(result);
            }
            response = Response.ok(resultList).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }

    /**
     * @param input
     * @return
     */
    @POST
    @Path("/detail.v")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getVoucher(HashMap<String, Object> input) {
        Response response = null;
        try {
            String voucherVendorLocationCode = RequestUtils.getStringForKey("voucherVendorLocationCode", input);
            BigInteger voucherVendorLocationId = Base26.decode(voucherVendorLocationCode);
            VoucherDetailView voucher = voucherServiceLocal.getVoucherDetailViewByVoucherVendorLocationId(voucherVendorLocationId);
            HashMap<String, Object> result = new HashMap<>();
            if (voucher != null) {
                result.put("voucherVendorLocationCode", Base26.encode(voucherVendorLocationId));
                result.put("voucherCode", Base26.encode(voucher.getId()));
                result.put("voucherTypeCode", Base26.encode(voucher.getVouchertypeId()));
                result.put("voucherProvider", voucher.getVoucherProvider());
                result.put("media", buildResponseForMedias(fileSystemServiceLocal, voucher.getVoucherDirectoryId()));
                result.put("title", voucher.getTitle());
                result.put("shortDescription", voucher.getShortDescription());
                result.put("description", voucher.getDescription());
                result.put("price", voucher.getPrice());
                result.put("termsUrl", voucher.getTermsUrl());
                result.put("finePrint", voucher.getFinePrint());
                result.put("website", voucher.getWebsite());
                result.put("vendorLocationCode", Base26.encode(voucher.getId()));
                result.put("directoryCode", Base26.encode(voucher.getVoucherDirectoryId()));
                result.put("zoneCode", Base26.encode(voucher.getZoneId()));
                result.put("favouriteCount", voucher.getFavouriteCount());
                result.put("likedCount", voucher.getLikedCount());
                result.put("locationCount", voucher.getLocationCount());
                result.put("maximumPerUser", voucher.getMaximumPerUser());
                result.put("maxInventory", voucher.getMaxInventory());
                result.put("notAfter", voucher.getNotAfter());
                result.put("notBefore", voucher.getNotBefore());
                result.put("retailValue", voucher.getRetailValue());
                result.put("price", voucher.getPrice());
                result.put("processingFeePercent", voucher.getProcessingFeePercent());
                result.put("purchaseCreditsReward", voucher.getPurchaseCreditsReward());
                result.put("cashEnabled", voucher.isCashEnabled());
                result.put("creditEnabled", voucher.isCreditEnabled());
                result.put("sold", voucher.getSold());
                result.put("creditEnabled", voucher.getTermsUrl());
                result.put("unlockingCredits", voucher.getUnlockingCredits());
                result.put("videoCount", voucher.getVoucherDirectoryVideoCount());
                result.put("disable", voucher.isDisabled());
            }

            response = Response.ok(result).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }


    /**
     * @return
     */
    @Path("/save.v")
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response saveVoucher(Map<String, Object> input) {
        Response response = null;
        try {

            String voucherVendorLocationCode = RequestUtils.getMandatoryStringForKey("voucherVendorLocationCode", input);
            String taxCode = RequestUtils.getStringForKey("taxCode", input);
            String instructions = RequestUtils.getStringForKey("instructions", input);
            String categoryCode = RequestUtils.getStringForKey("categoryCode", input);
            String voucherTypeCode = RequestUtils.getStringForKey("voucherTypeCode", input);
            String zoneCode = RequestUtils.getStringForKey("zoneCode", input);
            String voucherProvider = RequestUtils.getStringForKey("voucherProvider", input);
            String title = RequestUtils.getStringForKey("title", input);
            String shortDescription = RequestUtils.getStringForKey("shortDescription", input);
            String description = RequestUtils.getStringForKey("description", input);
            String website = RequestUtils.getStringForKey("website", input);
            double retailValue = RequestUtils.getMandatoryDoubleForKey("retailValue", input);
            double price = RequestUtils.getMandatoryDoubleForKey("price", input);

            String notBefore = RequestUtils.getValueForKey("notBefore", input, String.class);
            String notAfter = RequestUtils.getValueForKey("notAfter", input, String.class);
            String startDate = RequestUtils.getValueForKey("startDate", input, String.class);
            String endDate = RequestUtils.getValueForKey("endDate", input, String.class);


            int maxInventory = RequestUtils.getMandatoryIntForKey("maxInventory", input);
            int maximumPerUser = RequestUtils.getMandatoryIntForKey("maximumPerUser", input);


            String statusCode = RequestUtils.getStringForKey("statusCode", input);
            String termsUrl = RequestUtils.getStringForKey("termsUrl", input);
            String finePrint = RequestUtils.getStringForKey("finePrint", input);
            boolean disabled = Boolean.TRUE.equals(RequestUtils.getBooleanForKey("disabled", input));


            BigInteger statusId = Base26.decode(statusCode);
            BigInteger categoryId = Base26.decode(categoryCode);
            BigInteger voucherTypeId = Base26.decode(voucherTypeCode);
            BigInteger zoneId = Base26.decode(zoneCode);
            BigInteger taxId = Base26.decode(taxCode);


            BigInteger voucherVendorLocationId = voucherVendorLocationCode != null ? Base26.decode(voucherVendorLocationCode) : null;

            BigInteger voucherVendorLocationResult = voucherServiceLocal.saveVoucher(voucherVendorLocationId, categoryId, BigDecimal.valueOf(price), voucherTypeId, zoneId, voucherProvider, title,
                    shortDescription, description, website, BigDecimal.valueOf(retailValue), DateUtils.formatDate(notBefore), DateUtils.formatDate(notAfter), DateUtils.formatDate(startDate), DateUtils.formatDate(endDate),
                    BigInteger.valueOf(maxInventory),
                    BigInteger.valueOf(maximumPerUser), statusId, termsUrl, finePrint, disabled, instructions, taxId);

            HashMap<String, Object> result = new HashMap<>();
            result.put("voucherVendorLocationCode", Base26.encode(voucherVendorLocationResult));
            response = Response.ok(result).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }


    @Path("/types.v")
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response getVoucherTypes() {
        Response response = null;
        try {
            List<HashMap<String, Object>> listResult = new ArrayList<>();
            List<VoucherType> listVouchersTypes = voucherServiceLocal.getVoucherTypes();
            for (VoucherType voucherType : listVouchersTypes) {
                HashMap<String, Object> result = new HashMap<>();
                result.put("voucherTypeCode", Base26.encode(voucherType.getId()));
                result.put("name", voucherType.getName());
                result.put("description", voucherType.getDescription());
                result.put("creditEnabled", voucherType.isCreditEnabled());
                result.put("cashEnabled", voucherType.isCashEnabled());
                listResult.add(result);
            }
            response = Response.ok(listResult).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }


    @Path("/tax/all.v")
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response getTaxGroups() {
        Response response = null;
        try {
            List<HashMap<String, Object>> listResult = new ArrayList<>();
            List<Tax> listTaxesName = voucherServiceLocal.getTaxNames();
            for (Tax tax : listTaxesName) {
                HashMap<String, Object> result = new HashMap<>();
                result.put("taxCode", Base26.encode(tax.getId()));
                result.put("taxGroupCode", Base26.encode(tax.getTaxgroupId()));
                result.put("description", tax.getDescription());
                listResult.add(result);
            }
            response = Response.ok(listResult).build();
        } catch (AnaliaException analiaException) {
            response = ResponseUtils.buildErrorResponse(analiaException);
        }
        return response;
    }

}
