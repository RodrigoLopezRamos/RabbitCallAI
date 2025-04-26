package com.analia.web.rs.voucher;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Filter;
import com.analia.common.model.Voucher;
import com.analia.common.model.resultset.VoucherResultSet;
import com.analia.common.util.Base26;
import com.analia.filters.service.FilterServiceLocal;
import com.analia.location.service.LocationServiceLocal;
import com.analia.media.service.FileSystemServiceLocal;
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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/voucher")
@ApplicationScoped
public class VoucherResource extends AnaliaResource {
    @Inject
    private FileSystemServiceLocal fileSystemServiceLocal;
    @Inject
    private LocationServiceLocal locationServiceLocal;
    @Inject
    private VendorServiceLocal vendorServiceLocal;
    @Inject
    private VoucherServiceLocal voucherServiceLocal;
    @Inject
    private FilterServiceLocal filterServiceLocal;

    @POST
    @Path("/all.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getVouchers(Map<String, Object> input) {
        Response response = null;
        try {

            String categoryCode = "2";//RequestUtils.getStringForKey("categoryCode", input);
            String voucherTypeCode = "1";//RequestUtils.getStringForKey("voucherTypeCode", input);
            BigInteger categoryId = categoryCode != null ? Base26.decode(categoryCode) : null;
            BigInteger voucherTypeId = Base26.decode(voucherTypeCode);
            int page = RequestUtils.getMandatoryIntForKey("page", input);
            Integer pageSize = RequestUtils.getIntegerForKey("pageSize", input);
            int sortType = RequestUtils.getMandatoryIntForKey("sortType", input);
            List<HashMap<String, Object>> jsonFilters = RequestUtils.getValueForKey("filters", input, List.class);
            List<Filter> filters = (jsonFilters != null && jsonFilters.size() > 0) ? RequestUtils.getFilters(jsonFilters, filterServiceLocal) : null;
            List<VoucherResultSet> vouchers = voucherServiceLocal.getVouchers(voucherTypeId, page, sortType, categoryId, pageSize, filters);
            int totalCount = voucherServiceLocal.totalVoucherCountByZoneId(voucherTypeId, categoryId);

            List<HashMap<String, Object>> resultList = new ArrayList<>(vouchers.size());
            for (VoucherResultSet voucher : vouchers) {
                HashMap<String, Object> result = new HashMap<>();
                result.put("voucherVendorLocationCode", Base26.encode(voucher.getVoucherVendorLocationId()));
                result.put("voucherTypeCode", Base26.encode(voucher.getVoucherTypeId()));
                result.put("voucherCode", Base26.encode(voucher.getVoucherId()));
                result.put("zoneCode", Base26.encode(voucher.getZoneId()));
                result.put("media", buildResponseForMedia(fileSystemServiceLocal, voucher.getDirectoryId(), 0));
                result.put("title", voucher.getTitle());
                result.put("latitude", voucher.getLatitude());
                result.put("longitude", voucher.getLongitude());
                result.put("totalCount", totalCount);
                result.put("categoryCode", Base26.encode(voucher.getCategoryId()));
                result.put("categoryName", voucher.getCategoryName());

                resultList.add(result);
            }
            response = Response.ok(resultList).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }

    @POST
    @Path("/all/byLocation.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getVouchersByVendorLocation(Map<String, Object> input) {
        Response response = null;
        try {

            String categoryCode = RequestUtils.getStringForKey("categoryCode", input);
            String voucherTypeCode = RequestUtils.getStringForKey("voucherTypeCode", input);
            BigInteger categoryId = Base26.decode(categoryCode);
            BigInteger voucherTypeId = Base26.decode(voucherTypeCode);

            String vendorLocationCode = RequestUtils.getMandatoryStringForKey("vendorLocationCode", input);
            BigInteger vendorLocationId = Base26.decode(vendorLocationCode);
            int page = RequestUtils.getMandatoryIntForKey("page", input);
            Integer pageSize = RequestUtils.getIntegerForKey("pageSize", input);
            Integer sortType = RequestUtils.getIntegerForKey("sortType", input);

            List<VoucherResultSet> vouchers = voucherServiceLocal.getVouchersByVendorLocationId(vendorLocationId, voucherTypeId, categoryId, page, pageSize, sortType);
            List<Map<String, Object>> resultList = new ArrayList<>(vouchers.size());
            for (VoucherResultSet voucher : vouchers) {
                Map<String, Object> result = new HashMap<>();
                result.put("voucherVendorLocationCode", Base26.encode(voucher.getVoucherVendorLocationId()));
                result.put("voucherTypeCode", Base26.encode(voucher.getVoucherTypeId()));
                result.put("zoneCode", Base26.encode(voucher.getZoneId()));
                result.put("media", buildResponseForMedia(fileSystemServiceLocal, voucher.getDirectoryId(), 1));
                result.put("title", voucher.getTitle());
                result.put("latitude", voucher.getLatitude());
                result.put("longitude", voucher.getLongitude());
                resultList.add(result);
            }
            response = Response.ok(resultList).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }

    @POST
    @Path("/detail.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getVoucher(Map<String, Object> input) {
        Response response = null;
        try {
            String voucherCode = RequestUtils.getStringForKey("voucherVendorLocationCode", input);
            BigInteger voucherVendorLocationId = Base26.decode(voucherCode);
            Voucher voucher = voucherServiceLocal.getVoucherByVoucherVendorLocationId(voucherVendorLocationId);
            HashMap<String, Object> result = new HashMap<>();
            if (voucher != null) {
                result.put("voucherVendorLocationCode", Base26.encode(voucherVendorLocationId));
                result.put("voucherCode", Base26.encode(voucher.getId()));
                result.put("voucherTypeCode", voucher.getVoucherTypeId());
                result.put("voucherProvider", voucher.getVoucherProvider());
                result.put("media", buildResponseForMedias(fileSystemServiceLocal, voucher.getDirectoryId()));
                result.put("title", voucher.getTitle());
                result.put("shortDescription", voucher.getShortDescription());
                result.put("description", voucher.getDescription());
                result.put("price", voucher.getPrice());
                result.put("termsUrl", voucher.getTermsUrl());
                result.put("finePrint", voucher.getFinePrint());
                result.put("website", voucher.getWebsite());
                result.put("vendorLocationCode", Base26.encode(voucher.getId()));
                result.put("zoneCode", Base26.encode(voucher.getZoneId()));

            }
            response = Response.ok(result).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }

    @POST
    @Path("/bought/all.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getVouchersBoughtInPurchaseForVoucherId(Map<String, Object> input) {
        Response response = null;
        try {
            String voucherCode = RequestUtils.getStringForKey("voucherVendorLocationCode", input);
            BigInteger voucherVendorLocationId = Base26.decode(voucherCode);
            Voucher voucher = voucherServiceLocal.getVoucherByVoucherVendorLocationId(voucherVendorLocationId);
            List<HashMap<String, Object>> resultList = new ArrayList<>();
            if (voucher != null) {
                List<Voucher> vouchers = voucherServiceLocal.getVouchersBoughtInPurchaseForVoucherId(voucher.getId());
                for (Voucher voucherInList : vouchers) {
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("voucherVendorLocationCode", Base26.encode(voucherVendorLocationId));
                    result.put("voucherCode", Base26.encode(voucherInList.getId()));
                    result.put("voucherTypeCode", voucherInList.getVoucherTypeId());
                    result.put("voucherProvider", voucherInList.getVoucherProvider());
                    result.put("media", buildResponseForMedias(fileSystemServiceLocal, voucherInList.getDirectoryId()));
                    result.put("title", voucherInList.getTitle());
                    result.put("shortDescription", voucherInList.getShortDescription());
                    result.put("description", voucherInList.getDescription());
                    result.put("price", voucherInList.getPrice());
                    result.put("termsUrl", voucherInList.getTermsUrl());
                    result.put("finePrint", voucherInList.getFinePrint());
                    result.put("website", voucherInList.getWebsite());
                    result.put("vendorLocationCode", Base26.encode(voucherInList.getId()));
                    resultList.add(result);
                }
            }
            response = Response.ok(resultList).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }

    @POST
    @Path("/shipping-methods/all.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getShippingMethods(Map<String, Object> input) {
        Response response = null;
        List<HashMap<String, Object>> listShippingMethods = new ArrayList<>();
        HashMap<String, Object> method1 = new HashMap<>();
        //  method1.put("shippingMethodCode", Base26.encode(12));
        method1.put("name", "Groud");
        method1.put("deliveryTime", "5 - 7 Days");
        method1.put("cost", 0.10);

        HashMap<String, Object> method2 = new HashMap<>();
        // method2.put("shippingMethodCode", Base26.encode(132));
        method2.put("name", "Expedited");
        method2.put("deliveryTime", "5 - 7 Days");
        method2.put("cost", 123.12);

        listShippingMethods.add(method1);
        listShippingMethods.add(method2);

        response = Response.ok(listShippingMethods).build();

        return response;

    }

}