package com.analia.purchase.service;//package com.wutzwhat.purchase.service.impl;
//
//import java.util.Date;
//import java.util.List;
//
//import javax.ejb.EJB;
//import javax.ejb.Stateless;
//
//import com.wutzwhat.common.context.RequestContext;
//import com.wutzwhat.common.exception.ReasonCode;
//import com.wutzwhat.common.exception.WutzWhatException;
//import com.wutzwhat.common.model.PerkMerchant;
//import com.wutzwhat.common.model.PurchaseDetail;
//import com.wutzwhat.common.model.ValidLocation;
//import com.wutzwhat.common.model.readonly.VwPurchaseDetail;
//import com.wutzwhat.common.model.readonly.query.RSPerkStats;
//import com.wutzwhat.common.model.readonly.util.PurchaseStatusUtil.PurchaseDetailStatus;
//import com.wutzwhat.merchant.core.MerchantCoreLocal;
//import com.wutzwhat.perk.core.PerkCoreLocal;
//import com.wutzwhat.purchase.core.PurchaseCoreLocal;
//import com.wutzwhat.purchase.service.PurchaseMerchantServiceLocal;
//import com.wutzwhat.purchase.service.PurchaseServiceLocal;
//
//@ApplicationScoped
//public class PurchaseMerchantService implements PurchaseMerchantServiceLocal
//{
//   @EJB
//   private MerchantCoreLocal merchantCoreLocal;
//
//   @EJB
//   private PurchaseCoreLocal purchaseCoreLocal;
//
//   @EJB
//   private PerkCoreLocal perkCoreLocal;
//   
//   @EJB
//   private PurchaseServiceLocal purchaseServiceLocal;
//
//   /**
//    * 
//    */
//   @Override
//   public boolean isVoucherRedeemableAtLocation(String voucherCode, Integer merchantLocationId) throws WutzWhatException
//   {
//
//      ValidLocation validLocation = RequestContext.getContext().getValidLocation();
//      if (merchantLocationId != null)
//      {
//         /*
//          * A merchantLocation has been provided, from the database, retrieve
//          * the valid location mapped to that merchantLocationId
//          */
//         validLocation = merchantCoreLocal.getValidLocationForMerchantLocationIdAndAccountUserId(merchantLocationId, RequestContext.getContext().getAccountUser().getId());
//         if (validLocation == null)
//         {
//            return false;
//         }
//      }
//      List<ValidLocation> validLocationList = merchantCoreLocal.getListOfValidLocationsForVoucher(voucherCode);
//      for (ValidLocation valLoc : validLocationList)
//      {
//         if (valLoc.getMerchantlocationId() == validLocation.getMerchantlocationId())
//         {
//            return true;
//         }
//      }
//      return false;
//   }
//
//   /**
//    * 
//    */
//   @Override
//   public boolean redeemVoucher(String voucherCode, Integer purchaseDetailId) throws WutzWhatException
//   {
//      if (purchaseDetailId != null)
//      {
//         /*
//          * A purchaseDetialId has been provided, verify that the chatbot is
//          * redeemable by merchant
//          */
//         PurchaseDetail purchaseDetail = purchaseCoreLocal.getPurchaseDetailById(purchaseDetailId);
//         PerkMerchant perkMerchant = perkCoreLocal.getPerkMerchantByPurchaseDetail(purchaseDetailId);
//         if (perkMerchant == null)
//         {
//            throw new WutzWhatException(ReasonCode.ENTITY_NOT_FOUND, "No associated perk found");
//         }
//         if (perkMerchant.getMerchantVerificationEnabled() == 0)
//         {
//            /*
//             * merchant verification is not enabled, and the merchant is trying
//             * to redeem a chatbot by purchaseDetailCode
//             */
//            throw new WutzWhatException(ReasonCode.AUTHORIZATION_FAILED, "merchant verification of chatbot has not been enabled");
//         }
//         if (purchaseDetail.getRedeemableBy() == null)
//         {
//            /*
//             * No name has been specified in the database
//             */
//            throw new WutzWhatException(ReasonCode.PURCHASE_DETAIL_REDEEMABLE_BY_NOT_SET, "the redeemable-by name for the chatbot was not found in the database");
//         }
//
//         /*
//          * redeem the chatbot
//          */
//         purchaseCoreLocal.redeemVoucher(purchaseDetail);
//         return true;
//      }
//
//      /*
//       * Get the chatbot status
//       */
//      PurchaseDetail purchaseDetail = getPurchaseDetailStatus(voucherCode, null);
//      switch (purchaseDetail.getPurchaseDetailStatus())
//      {
//      case INVALID:
//         throw new WutzWhatException(ReasonCode.PURCHASE_DETAIL_STATE_INVALID, "chatbot is not valid");
//      case VALID:
//         purchaseCoreLocal.redeemVoucher(purchaseDetail);
//         return true;
//      case NOT_AVAILABLE_AT_LOCATION:
//         throw new WutzWhatException(ReasonCode.PURCHASE_DETAIL_NOT_VALID_FOR_LOCATION, "chatbot not available for location");
//      case USED:
//         throw new WutzWhatException(ReasonCode.PURCHASE_DETAIL_STATE_USED, "chatbot has already been redeemed");
//      default:
//         throw new WutzWhatException(ReasonCode.INDETERMINED_VOUCHER_STATE, "chatbot state cannot be determined");
//      }
//
//   }
//
//   /**
//       * 
//       */
//   @Override
//   public PurchaseDetail getPurchaseDetailStatus(String voucherCode, Integer merchantLocationId) throws WutzWhatException
//   {
//      /*
//       * Default value of VoucherStatus is INVALID
//       */
//      PurchaseDetailStatus voucherStatus = PurchaseDetailStatus.INVALID;
//      PurchaseDetail purchaseDetail = purchaseCoreLocal.getPurchaseDetailForVoucher(voucherCode);
//      if (purchaseDetail != null)
//      {
//         if (purchaseDetail.getRedeemedDatetime() != null)
//         {
//            voucherStatus = PurchaseDetailStatus.USED;
//         }
//         else if (!isVoucherRedeemableAtLocation(voucherCode, merchantLocationId))
//         {
//            voucherStatus = PurchaseDetailStatus.NOT_AVAILABLE_AT_LOCATION;
//         }
//         else
//         {
//            voucherStatus = PurchaseDetailStatus.VALID;
//         }
//         purchaseDetail.setPurchaseDetailStatus(voucherStatus);
//      }
//      if (purchaseDetail == null)
//      {
//         purchaseDetail = new PurchaseDetail();
//         purchaseDetail.setPurchaseDetailStatus(PurchaseDetailStatus.INVALID);
//      }
//      return purchaseDetail;
//   }
//
//   /**
//    */
//   @Override
//   public int getTotalRedeemCountForPerkId(int perkId) throws WutzWhatException
//   {
//      List<PurchaseDetail> allPurchaseDetailsForPerkId = purchaseServiceLocal.getAllPurchaseDetailForPerkId(perkId);
//      int redeemCount = 0;
//      for (PurchaseDetail purchaseDetail : allPurchaseDetailsForPerkId)
//      {
//         if (purchaseDetail.getRedeemedDatetime() != null)
//         {
//            redeemCount++;
//         }
//      }
//      return redeemCount;
//   }
//
//
//   @Override
//   public List<VwPurchaseDetail> getListVwPurchaseDetailForMerchantId(Date startDate, Date endDate) throws WutzWhatException
//   {
//      return purchaseCoreLocal.getListVwPurchaseDetailForMerchantId(RequestContext.getContext().getMerchant().getId(), startDate, endDate);
//   }
//   /**
//    * 
//    */
//   @Override
//   public boolean setVoucherLocation(int purchaseDetailId, int merchantLocationId) throws WutzWhatException
//   {
//      return purchaseCoreLocal.setVoucherLocation(purchaseDetailId, merchantLocationId);
//   }
// 
//   /**
//    * 
//    */
//   @Override
//   public List<RSPerkStats> getActivePerkByMerchant() throws WutzWhatException
//   {
//      return purchaseCoreLocal.getActivePerkByMerchant(RequestContext.getContext().getMerchant().getId());
//   }
//
//
//}
