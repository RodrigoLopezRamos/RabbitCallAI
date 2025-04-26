package com.analia.web.rs.trade;

import com.analia.common.constants.Constants;
import com.analia.common.exception.AnaliaException;
import com.analia.common.model.*;
import com.analia.common.model.resultset.TradeResultSet;
import com.analia.common.util.Base26;
import com.analia.filters.service.FilterServiceLocal;
import com.analia.location.service.LocationServiceLocal;
import com.analia.media.service.FileSystemServiceLocal;
import com.analia.metadata.service.MetaDataServiceLocal;
import com.analia.trade.service.TradeServiceLocal;
import com.analia.vendor.service.VendorServiceLocal;
import com.analia.web.rs.AnaliaResource;
import com.analia.web.util.RequestUtils;
import com.analia.web.util.ResponseUtils;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;


import java.math.BigInteger;
import java.util.*;

@Path("/rs/trade")
public class TradeResource extends AnaliaResource {
    @Inject
    private TradeServiceLocal tradeService;

    @Inject
    private FileSystemServiceLocal fileSystemServiceLocal;

    @Inject
    private LocationServiceLocal locationServiceLocal;

    @Inject
    private VendorServiceLocal vendorServiceLocal;

    @Inject
    private FilterServiceLocal filterServiceLocal;

    @Inject
    private MetaDataServiceLocal metaDataServiceLocal;

    @POST
    @Path("/all.s")
    @Consumes("application/json")
    @Produces("application/json")
    @SuppressWarnings("unchecked")
    public Response trades(Map<String, Object> input) {
        Response response = null;
        try {
            String categoryCode = RequestUtils.getMandatoryStringForKey("categoryCode", input);
            String categoryTypeCode = RequestUtils.getMandatoryStringForKey("categoryTypeCode", input);
            BigInteger categoryId = Base26.decode(categoryCode);
            int categoryTypeId = Base26.decode(categoryTypeCode).intValue();

            int page = RequestUtils.getMandatoryIntForKey("page", input);
            Integer pageSize = Constants.PAGE_SIZE;
            int sortType = RequestUtils.getMandatoryIntForKey("sortType", input);
            List<HashMap<String, Object>> jsonFilters = RequestUtils.getValueForKey("filters", input, List.class);
            List<Filter> filters = (jsonFilters != null && jsonFilters.size() > 0) ? RequestUtils.getFilters(jsonFilters, filterServiceLocal) : null;
            List<TradeResultSet> trades = tradeService.getTrades(categoryTypeId, page, categoryId, sortType, pageSize, filters);
            int totalCount = tradeService.totalTradeCountByZoneId(categoryId, categoryTypeId);
            List<HashMap<String, Object>> resultList = new ArrayList<>(trades.size());

            for (TradeResultSet trade : trades) {
                HashMap<String, Object> result = new HashMap<>();
                result.put("tradeLocationCode", Base26.encode(trade.getTradeLocationId()));
                result.put("media", buildResponseForMedia(fileSystemServiceLocal, trade.getDirectoryId(), 1));
                result.put("name", trade.getName());
                result.put("title", trade.getTitle());
                result.put("vendorLocationCode", Base26.encode(trade.getVendorLocationId()));
                result.put("cityCode", Base26.encode(trade.getCityId()));
                result.put("categoryCode", Base26.encode(trade.getCategoryId()));
                result.put("latitude", trade.getLatitude());
                result.put("longitude", trade.getLongitude());
                result.put("loved", trade.isFavourited());
                result.put("distance", trade.getDistance());
                result.put("totalReviews", trade.getTotalReviews());
                result.put("description", trade.getDescription());
                result.put("address1", trade.getAddress1());
                result.put("longitude", trade.getLongitude());
                result.put("latitude", trade.getLatitude());
                result.put("totalCount", totalCount);
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
    @Path("/detail.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getTradeDetail(Map<String, Object> input) {
        Response response = null;
        try {
            String tradeLocationCode = (String) input.get("tradeLocationCode");
            BigInteger tradeLocationId = Base26.decode(tradeLocationCode);
            TradeResultSet trade = tradeService.tradeDetail(tradeLocationId);
            HashMap<String, Object> result = new HashMap<>();
            result.put("tradeLocationCode", Base26.encode(trade.getTradeLocationId()));
            result.put("description", trade.getDescription());
            result.put("media", buildResponseForMedias(fileSystemServiceLocal, trade.getDirectoryId()));
            result.put("name", trade.getName());
            result.put("title", trade.getTitle());
            result.put("website", trade.getWebsite());
            result.put("vendorLocationCode", Base26.encode(trade.getVendorLocationId()));
            result.put("address", trade.getAddress1());
            result.put("cityId", Base26.encode(trade.getCityId()));
            result.put("cityName", trade.getCity());
            result.put("province", trade.getProvince());
            result.put("country", trade.getCountry());
            result.put("postalOrZipcode", trade.getPostalOrZipcode());
            result.put("categoryId", Base26.encode(trade.getCategoryId()));
            result.put("overallScore", metaDataServiceLocal.getOverallScore(trade.getTradeId(), null));//TODO this call must be from  query getTrade Detail.
            result.put("voted", metaDataServiceLocal.getReviewByTradeIdOrVoucherId(trade.getId(), null) != null);
            result.put("loved", trade.isFavourited());
            result.put("totalReviews", trade.getTotalReviews());
            result.put("disabled", trade.isDisabled());


            List<Tag> tradeTags = tradeService.getTradeTagsByTradeId(trade.getTradeId());
            List<String> tags = new ArrayList<>(tradeTags.size());
            for (Tag tag : tradeTags) {
                tags.add(tag.getName());
            }
            result.put("tags", tags);
            result.put("latitude", trade.getLatitude());
            result.put("longitude", trade.getLongitude());

            response = Response.ok(result).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }

    /**
     * FIXME disable for production
     *
     * @param payload
     * @return
     */
    @POST
    @Path("/save")
    @Consumes("application/json")
    @Produces("application/json")
    @Transactional
    public Response save(Map<String, Object> payload) {
        Response response = null;
        try {
//         int directoryId = RequestUtils.getMandatoryIntForKey("directoryId", payload);
            BigInteger vendorId = BigInteger.valueOf(3);//RequestUtils.getMandatoryIntForKey("vendorId", payload);

            String name = RequestUtils.getMandatoryStringForKey("name", payload);
            String title = RequestUtils.getMandatoryStringForKey("title", payload);
            String description = RequestUtils.getMandatoryStringForKey("description", payload);
            String address = RequestUtils.getMandatoryStringForKey("address", payload);
            String website = RequestUtils.getMandatoryStringForKey("website", payload);
            String city = RequestUtils.getMandatoryStringForKey("city", payload);
            String province = RequestUtils.getMandatoryStringForKey("province", payload);
            String hoursofoperations = RequestUtils.getMandatoryStringForKey("hoursofoperations", payload);
            String phone = RequestUtils.getMandatoryStringForKey("phone", payload);
            String postalOrZipcode = RequestUtils.getMandatoryStringForKey("postalOrZipcode", payload);
            String country = RequestUtils.getStringForKey("country", payload);

            if (country == null || country.isEmpty()) {
                country = "Canada";
            }

            double latitude = (double) RequestUtils.getValueForKey("latitude", payload);
            double longitude = (double) RequestUtils.getValueForKey("longitude", payload);



            Date notBefore = new Date();
            Date notAfter = new Date();
            Date startDate = new Date();


            Zone zone = locationServiceLocal.getZoneByName(city);
            if (zone == null) {
                zone = new Zone();
                zone.setActive(true);
                zone.setCenterLatitude(latitude);
                zone.setCenterLongitude(longitude);
                zone.setCountry(country);
                zone.setCurrencyType(country.equals("Canada") ? "CAD" : "USD");
                zone.setMaxRadius(100);
                zone.setTimezone("EST");
                zone.setDescription(country + " : " + city);
                zone.setName(city);
                locationServiceLocal.saveZone(zone);
            }

            City cityObj = locationServiceLocal.getCityByName(city);
            if (cityObj == null) {
                cityObj = new City();
                cityObj.setZoneId(zone.getId());
                cityObj.setProvince(province);
                cityObj.setName(city);
                cityObj.setActive(true);
                locationServiceLocal.saveCity(cityObj);
            }


            Location location = new Location();
            location.setAddress1(address);
            location.setCityId(cityObj.getId());
            location.setCity(city);
            location.setProvince(province);
            location.setCountry(country);
            location.setPostalOrZipcode(postalOrZipcode);
            location.setLatitude(latitude);
            location.setLongitude(longitude);

            locationServiceLocal.saveLocation(location);


//            Directory directory = fileSystemServiceLocal.saveDirectory(0, 1, null);
//
//            Vendor vendor = vendorServiceLocal.getVendor(vendorId);
//            if (vendor == null) {
//                throw new AnaliaException(ExceptionCode.ENTITY_NOT_FOUND, "Vendor Does not exist");
//            }

//            VendorLocation vendorLocation = new VendorLocation(0, vendor.getId(), location.getId(), directory.getId(), name, phone, name, true, Constants.SYSTEM_USER_ID,
//                    new Date(), DateUtils.getNextNDay(new Date(), (360 * 2)), new Date(), null);
//            vendorLocation.setBusinessScheduleId(1);
//            vendorServiceLocal.saveVendorLocation(vendorLocation);
//            int categoryId = 8;
//            Trade trade = new Trade(0, directory.getId(), vendor.getId(), name, title, description, website, notBefore, notAfter, startDate, false);
//            trade.setEndDate(new Date());
//            trade.setZoneId(zone.getId());
//
//
//            tradeService.saveTrade(trade);
//            TradeCategory tradeCategory = new TradeCategory(0, categoryId, trade.getId(), directory.getId());
//            tradeService.saveTradeCategory(tradeCategory);
//
//            TradeLocation tradeLocation = new TradeLocation(0, tradeCategory.getId(), vendorLocation.getId());
//            tradeService.saveTradeLocation(tradeLocation);
//            response = Response.ok(trade.getDirectoryId()).build();

        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }
}
