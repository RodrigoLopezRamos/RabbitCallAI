package com.analia.purchase.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Purchase;
import com.analia.common.model.resultset.view.PurchaseDetailView;
import com.analia.common.model.resultset.view.VoucherVendorTaxGroupView;
import com.analia.common.persistence.PersistenceFacade;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public interface PurchaseFacadeLocal extends PersistenceFacade<Purchase> {

    /**
     * @param voucherId
     * @param userId
     * @return
     * @throws AnaliaException
     */
    int getRemainingAllowanceVoucherByUser(BigInteger voucherId, BigInteger userId) throws AnaliaException;

    /**
     * @param voucherId
     * @param amount
     * @return
     * @throws AnaliaException
     */
    List<VoucherVendorTaxGroupView> getVoucherVendorTaxGroupViewRange(BigInteger voucherId, BigDecimal amount) throws AnaliaException;

    /**
     * @param voucherId
     * @param quantity
     * @return
     * @throws AnaliaException
     */
    List<VoucherVendorTaxGroupView> getVoucherVendorTaxGroupViewPurchase(BigInteger voucherId, BigInteger quantity) throws AnaliaException;

    /**
     * @param userId
     * @return
     * @throws AnaliaException
     */
    List<PurchaseDetailView> getPurchaseListByUserIdWithPagination(BigInteger userId, int page, int size) throws AnaliaException;

    /**
     * @param userId
     * @return
     * @throws AnaliaException
     */
    List<PurchaseDetailView> getPurchaseListByUser(BigInteger userId) throws AnaliaException;

    /**
     * @param userId
     * @param purchaseId
     * @return
     * @throws AnaliaException
     */
    List<PurchaseDetailView> getPurchaseDetailByUserIdAndPurchaseId(BigInteger userId, BigInteger purchaseId) throws AnaliaException;

    /**
     * @param purchaseDetailId
     * @return
     * @throws AnaliaException
     */
    PurchaseDetailView getPurchaseDetail(BigInteger purchaseDetailId, BigInteger userId) throws AnaliaException;

//   
//   /**
//    * @param merchantId
//    * @return totalSold , vouchersRedeemed
//    * @throws AnaliaException
//    */
//   List<RSPerkStats>  getActivePerkByMerchant(int merchantId)throws AnaliaException;

}
