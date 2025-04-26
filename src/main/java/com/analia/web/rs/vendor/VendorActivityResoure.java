package com.analia.web.rs.vendor;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;
import jakarta.ws.rs.Path;


//import com.analia.purchase.service.PurchaseVendorServiceLocal;


@Path("/rs/merchant-activity")
@ApplicationScoped
public class VendorActivityResoure {
    //   @EJB
//   private PurchaseVendorServiceLocal purchaseMerchantServiceLocal;
////   @EJB
//    private TradeServiceLocal tradeServiceLocal;
//@Inject
//    private VoucherServiceLocal perkServiceLocal;
//@Inject
//    private VendorServiceLocal merchantServiceLocal;
//@Inject
//    private AuditServiceLocal auditServiceLocal;
//@Inject
//    private UserServiceLocal userServiceLocal;

//   @Path("/merchant-analytic-summary.biz")
//   @POST
//   @Produces("application/json")
//   @Consumes("application/json")
//   public Response merchantAnalyticSummary(HashMap<String, Object> reqBody)
//   {
//      Response response = null;
//      try
//      {
//         Date startDate = MapUtil.getMandatoryDateForKey("startDate", reqBody);
//         Date endDate = MapUtil.getMandatoryDateForKey("endDate", reqBody);
//         List<HashMap<String, Object>> listHashMaps = new ArrayList<>();
//         List<RSSharingStats> sharingStats = merchantServiceLocal.getSharingCountForWutzWhatByMerchant(startDate, endDate);
//         for (RSSharingStats rsSharingStats : sharingStats)
//         {
//            HashMap<String, Object> sharing = new HashMap<>();
//            sharing.put("wutzwhatCode", NumericBase26.encode(rsSharingStats.wutzwhatId));
//            sharing.put("network", rsSharingStats.shareType);
//            sharing.put("count", rsSharingStats.sharingCount);
//            listHashMaps.add(sharing);
//         }
//         HashMap<String, Object> result = new HashMap<>();
//         result.put("shared", listHashMaps);
//         result.put("wutzWhats", JSONUtils.createResponseForGetWWListForMerchantId(wutzWhatServiceLocal.getWutzWhatListForMerchantWithViewsCount(startDate, endDate)));
//         result.put("perks", JSONUtils.createJsonResponseForMerchantPerkList(perkServiceLocal.getPerkListForMerchantWithViewsCount(startDate, endDate)));
//         response = Response.ok(result).build();
//      } catch (WutzWhatException wutzwhatexception)
//      {
//         response = ResponseUtils.buildErrorResponse(wutzwhatexception);
//      }
//      return response;
//   }
//
//   /**
//    * 
//    * request: { }
//    * 
//    * response: { perksSold perksRedeemed }
//    * 
//    * @param reqBody
//    * @return
//    */
//
//   @Path("/get-active-feature-perks.biz")
//   @POST
//   @Produces("application/json")
//   @Consumes("application/json")
//   public Response getActiveFeaturePerks()
//   {
//      Response response = null;
//      try
//      {
//         List<HashMap<String, Object>> result = new ArrayList<>();
//         List<RSPerkStats> activePerks = purchaseMerchantServiceLocal.getActivePerkByMerchant();
//         for (RSPerkStats rsPerkStats : activePerks)
//         {
//            HashMap<String, Object> activePerk = new HashMap<>();
//            activePerk.put("perkCode", NumericBase26.encode(rsPerkStats.perkId));
//            activePerk.put("perksRedeemed", rsPerkStats.perksRedeemed);
//            activePerk.put("perksSold", rsPerkStats.perksSold);
//            result.add(activePerk);
//         }
//         response = Response.ok(result).build();
//      } catch (WutzWhatException wutzwhatexception)
//      {
//         response = ResponseUtils.buildErrorResponse(wutzwhatexception);
//      }
//      return response;
//   }
//
//   /**
//    * request: { month year }
//    * 
//    * response: { customersCount totalSpent <total customers paid before tax>
//    * perksSold amountEarned salesDetails: [ { day count } ] }
//    */
//
//   @Path("/get-sold-perks-summary.biz")
//   @POST
//   @Produces("application/json")
//   @Consumes("application/json")
//   public Response getSoldPerksSummary(HashMap<String, Object> reqBody)
//   {
//      Response response = null;
//      try
//      {
//         Date startDate = MapUtil.getMandatoryDateForKey("startDate", reqBody);
//         Date endDate = MapUtil.getMandatoryDateForKey("endDate", reqBody);
//
//         List<VwPurchaseDetail> vwPurchaseDetails = purchaseMerchantServiceLocal.getListVwPurchaseDetailForMerchantId(startDate, endDate);
//
//         int customerCount = 0;
//         int perksSold = 0;
//         BigDecimal totalSpent = new BigDecimal("0.0");
//         BigDecimal amountEarned = new BigDecimal("0.0");
//
//         HashMap<Integer, Boolean> filterPurchaseDetail = new HashMap<>();
//         HashMap<Integer, Boolean> filterCustomerCount = new HashMap<>();
//         List<HashMap<String, Object>> listSaleDetails = new ArrayList<>();
//
//         boolean repeatedDateObject;
//         for (VwPurchaseDetail vwPurchaseDetail : vwPurchaseDetails)
//         {
//            HashMap<String, Object> resultSaleDetail = new HashMap<>();
//            repeatedDateObject = false;
//            if (!filterPurchaseDetail.containsKey(vwPurchaseDetail.getPurchaseId()))
//            {
//               if (!filterCustomerCount.containsKey(vwPurchaseDetail.getUserId()))
//               {
//                  customerCount++;
//                  filterCustomerCount.put(vwPurchaseDetail.getUserId(), true);
//               }
//               filterPurchaseDetail.put(vwPurchaseDetail.getPurchaseId(), true);
//
//               for (HashMap<String, Object> resSaleDetailObj : listSaleDetails)
//               {
//                  if (((String) resSaleDetailObj.get("date")).equals(DateUtils.formatDate(vwPurchaseDetail.getCreatedDatetime())))
//                  {
//                     repeatedDateObject = true;
//                     int saleDetailsQty = (int) resSaleDetailObj.get("quantity");
//                     saleDetailsQty = saleDetailsQty + vwPurchaseDetail.getQuantity();
//                     resSaleDetailObj.put("quantity", saleDetailsQty);
//                  }
//               }
//
//               if (!repeatedDateObject)
//               {
//                  resultSaleDetail.put("date", DateUtils.formatDate(vwPurchaseDetail.getCreatedDatetime()));
//                  resultSaleDetail.put("quantity", vwPurchaseDetail.getQuantity());
//                  listSaleDetails.add(resultSaleDetail);
//               }
//
//               totalSpent = totalSpent.add((vwPurchaseDetail.getAmountPaid().subtract(vwPurchaseDetail.getTotalTaxPaid())));
//               amountEarned = totalSpent.subtract(totalSpent.multiply(vwPurchaseDetail.getProcessingFeePercent()).divide(new BigDecimal(100)));
//               perksSold++;
//            }
//         }
//         HashMap<String, Object> result = new HashMap<>();
//         result.put("customersCount", customerCount);
//         result.put("perksSold", perksSold);
//         result.put("amountEarned", amountEarned.setScale(2, RoundingMode.UP));
//         result.put("totalSpent", totalSpent.setScale(2, RoundingMode.UP));
//         result.put("salesDetails", listSaleDetails);
//         response = Response.ok(result).build();
//      } catch (WutzWhatException wutzwhatexception)
//      {
//         response = ResponseUtils.buildErrorResponse(wutzwhatexception);
//      }
//      return response;
//   }

//   /**
//    * 
//    * @return
//    */
//   @Path("/application-statistics.biz")
//   @POST
//   @Produces("application/json")
//   @Consumes("application/json")
//   public Response applicationStatistics()
//   {
//      Response response = null;
//      try
//      {
//         int systemUsers = userServiceLocal.countTotalUser();
//         HashMap<String, Object> hashMap = new HashMap<>();
//         /*
//          * As per the requirement discussed on 28-08-2014, Commenting out the
//          * code that converts the user count into a range
//          */
//         // String totalUserString = this.buildTotalUserString(systemUsers);
//         hashMap.put("userCount", systemUsers);
//         List<HashMap<String, Object>> contentRequests = new ArrayList<>();
//         List<RSGlobalStats> globalStats = auditServiceLocal.getGlobalStats();
//         // finding out total number of requests...
//         long totalRequests = 0;
//         for (RSGlobalStats rsGlobalStats : globalStats)
//         {
//            totalRequests += rsGlobalStats.requestCount;
//         }
//
//         BigDecimal totalPercent = new BigDecimal(0).setScale(2, RoundingMode.UP);
//         BigDecimal oneHundred = new BigDecimal(100).setScale(2, RoundingMode.UP);
//         for (int i = 0; i < globalStats.size(); i++)
//         {
//            RSGlobalStats rsGlobalStats = globalStats.get(i);
//            double percentage = ((double) rsGlobalStats.requestCount / (double) totalRequests) * 100.0;
//            HashMap<String, Object> jsonGlobalStast = new HashMap<>();
//
//            // jsonGlobalStast.put("wutzWhatRegionId",
//            // rsGlobalStats.wutzwhatRegionId);
//            jsonGlobalStast.put("country", rsGlobalStats.country);
//
//            BigDecimal regionPercent = (i == globalStats.size() - 1 ? oneHundred.subtract(totalPercent) : new BigDecimal(Math.round(percentage * 100.0) / 100.0).setScale(2, RoundingMode.UP));
//            jsonGlobalStast.put("requestPercentage", regionPercent); // percentage.setScale(2,RoundingMode.UP));
//            totalPercent = totalPercent.add(regionPercent);
//            contentRequests.add(jsonGlobalStast);
//         }
//         hashMap.put("contentRequests", contentRequests);
//         response = Response.ok(hashMap).build();
//      } catch (WutzWhatException wutzwhatexception)
//      {
//         response = ResponseUtils.buildErrorResponse(wutzwhatexception);
//      }
//      return response;
//   }

    /**
     * Masks the exact userCount into a range. Suitable for UI display.
     *
     * @param userCount The total count of user in the system
     * @return A suitable string for display to the user.
     */
    @SuppressWarnings("unused")
    private String buildTotalUserString(int userCount) {
        StringBuilder result = new StringBuilder();
        if (userCount < 1000) {
            result.append(userCount);
        } else if (userCount < 10000) {
            result.append((userCount / 1000)).append("K+");
        } else if (userCount < 50000) {
            int kCount = userCount / 1000;
            int lower = (kCount / 5) * 5;
            int higher = ((kCount + 5) / 5) * 5;
            result.append(lower).append("K - ").append(higher).append("K");
        } else if (userCount < 300000) {
            int kCount = userCount / 1000;
            int lower = (kCount / 10) * 10;
            int higher = ((kCount + 10) / 10) * 10;
            result.append(lower).append("K - ").append(higher).append("K");
        } else if (userCount < 1000000) {
            int kCount = userCount / 1000;
            int lower = (kCount / 50) * 50;
            int higher = ((kCount + 50) / 50) * 50;
            result.append(lower).append("K - ").append(higher).append("K");
        } else {
            result.append("1 000 000+");
        }
        return result.toString();
    }

}
