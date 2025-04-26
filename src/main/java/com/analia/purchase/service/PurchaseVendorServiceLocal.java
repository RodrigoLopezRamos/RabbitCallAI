package com.analia.purchase.service;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.PurchaseDetail;
import com.analia.common.model.resultset.view.PurchaseDetailView;


import java.util.Date;
import java.util.List;


public interface PurchaseVendorServiceLocal {

    /**
     * @param voucherCode
     * @param vendorLocationId
     * @return
     * @throws AnaliaException
     */
    PurchaseDetail getPurchaseDetailStatus(String voucherCode, Integer vendorLocationId) throws AnaliaException;

    /**
     * @param voucherCode
     * @param vendorLocationId
     * @return
     * @throws AnaliaException
     */
    boolean isVoucherRedeemableAtLocation(String voucherCode, Integer vendorLocationId) throws AnaliaException;

    /**
     * @param voucherCode
     * @param purchaseDetailId
     * @return
     * @throws AnaliaException
     */
    boolean redeemVoucher(String voucherCode, Integer purchaseDetailId) throws AnaliaException;

    /**
     * @param voucherId
     * @return
     * @throws AnaliaException
     */
    int getTotalRedeemCountForVoucherId(int voucherId) throws AnaliaException;

    /**
     * @param startDate
     * @param endDate
     * @return
     * @throws AnaliaException
     */
    List<PurchaseDetailView> getListPurchaseDetailForVoucherId(Date startDate, Date endDate) throws AnaliaException;


    /**
     * @param purchaseDetailId
     * @param vendorLocationId
     * @return
     * @throws AnaliaException
     */
    boolean setVoucherLocation(int purchaseDetailId, int vendorLocationId) throws AnaliaException;
}
