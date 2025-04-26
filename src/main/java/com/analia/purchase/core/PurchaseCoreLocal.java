package com.analia.purchase.core;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.*;
import com.analia.common.model.resultset.view.PurchaseDetailView;
import com.analia.common.model.resultset.view.VoucherDetailView;
import com.analia.common.model.resultset.view.VoucherVendorTaxGroupView;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;


public interface PurchaseCoreLocal {

    /**
     * @param purchaseDetailId
     * @param purchaseId
     * @param voucherVendorId
     * @param taxGroupId
     * @param voucherMediaId
     * @param digitalVoucher
     * @param relativePercentage
     * @param feesPaid
     * @param userId
     * @return
     * @throws AnaliaException
     */
    PurchaseDetail savePurchaseDetail(BigInteger purchaseDetailId, BigInteger purchaseId, BigInteger voucherVendorId, BigInteger taxGroupId, BigInteger voucherMediaId, String digitalVoucher, BigDecimal relativePercentage, BigDecimal feesPaid, BigInteger userId) throws AnaliaException;

    /**
     * @param purchaseId
     * @param sourceId
     * @param voucherDirectoryId
     * @param creditsPaid
     * @param amountPaid
     * @param totalTaxPaid
     * @param paymentReferenceNumber
     * @param processingFeePercentage
     * @return
     * @throws AnaliaException
     */
    Purchase savePurchase(BigInteger purchaseId, BigInteger sourceId, BigInteger voucherVendorId, BigInteger voucherDirectoryId, BigInteger totalVouchers, BigInteger creditsPaid, BigDecimal amountPaid, BigDecimal totalTaxPaid, String paymentReferenceNumber, BigDecimal processingFeePercentage, BigInteger userId, BigInteger clientId) throws AnaliaException;

    /**
     * @param purchase
     * @return
     * @throws AnaliaException
     */
    Purchase savePurchase(Purchase purchase) throws AnaliaException;

    /**
     * @param purchaseId
     * @return
     * @throws AnaliaException
     */
    Purchase getPurchase(BigInteger purchaseId) throws AnaliaException;
    /**
     * @param sourceId
     * @param sourceTypeId
     * @param sourceHolderName
     * @param nickName
     * @param providerToken
     * @param maskedCardNumber
     * @param expiryYear
     * @param expiryMonth
     * @param defaultSource
     * @param disabled
     * @param userId
     * @return
     * @throws AnaliaException
     */
    Source saveSource(BigInteger sourceId, BigInteger sourceTypeId, String sourceHolderName, String nickName, String providerToken, String maskedCardNumber, long expiryYear, long expiryMonth, boolean defaultSource, boolean disabled, BigInteger userId) throws AnaliaException;

    /**
     * @param source
     * @return
     * @throws AnaliaException
     */
    Source saveSource(Source source) throws AnaliaException;

    /**
     * @param name
     * @return
     * @throws AnaliaException
     */
    SourceType getSourceTypeByName(String name) throws AnaliaException;

    /**
     * @param sourceId
     * @param userId
     * @return
     * @throws AnaliaException
     */
    Source getSourceByUser(BigInteger sourceId, BigInteger userId) throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    Source getDefaultSourceByUserId(BigInteger userId) throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    List<Source> getSourceList(BigInteger userId) throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    Source getSource(BigInteger userId, BigInteger sourceId) throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    boolean setDefaultSource(BigInteger userId, BigInteger sourceId) throws AnaliaException;

    /**
     * @param voucherId
     * @param userId
     * @return
     * @throws AnaliaException
     */
    int getRemainingAllowanceVoucherByUser(BigInteger voucherId, BigInteger userId) throws AnaliaException;

    /**
     * @param voucherId
     * @param quantity
     * @return
     * @throws AnaliaException
     */
    List<VoucherVendorTaxGroupView> getTaxPercentageByVoucherAndQuantity(BigInteger voucherId, BigInteger quantity) throws AnaliaException;

    /**
     * @param userId
     * @param page
     * @param size
     * @return
     * @throws AnaliaException
     */
    List<PurchaseDetailView> getPurchaseListByUserId(BigInteger userId, int page, int size) throws AnaliaException;

    /**
     * @param userId
     * @return
     * @throws AnaliaException
     */
    List<PurchaseDetailView> getPurchaseListByUserId(BigInteger userId) throws AnaliaException;

    /**
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

    /**
     * @param voucher
     * @return
     * @throws AnaliaException
     */
    PurchaseDetail getPurchaseDetailForVoucher(String voucher) throws AnaliaException;

    /**
     * @param puchaseDetail
     * @return
     * @throws AnaliaException
     */
    PurchaseDetail getPurchaseDetailById(BigInteger puchaseDetail) throws AnaliaException;

    /**
     * @param voucherId
     * @return
     * @throws AnaliaException
     */
    List<PurchaseDetail> getListPurchaseDetailForVoucherId(BigInteger voucherId) throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    List<SourceType> getAllSourceTypes() throws AnaliaException;

    /**
     * @param vendorLocationVoucherId
     * @return
     * @throws AnaliaException
     */
    List<PurchaseDetailView> getPurchaseDetailsViewForVendorLocationVoucherId(BigInteger vendorLocationVoucherId) throws AnaliaException;

    /**
     * @param sourceTypeId
     * @return
     * @throws AnaliaException
     */
    SourceType getSourceTypeById(BigInteger sourceTypeId) throws AnaliaException;


    /**
     * @param vendorVoucherId
     * @param vendorLocationId
     * @return
     * @throws AnaliaException
     */
    VendorLocationVoucher getVendorLocationVoucherForRedeemingVoucher(BigInteger vendorVoucherId, BigInteger vendorLocationId) throws AnaliaException;

    /**
     * @param purchaseDetailId
     * @param vendorLocationId
     * @return
     * @throws AnaliaException
     */
    boolean setVoucherLocation(BigInteger purchaseDetailId, BigInteger vendorLocationId, BigInteger userId) throws AnaliaException;

    /**
     * @param purchase
     * @param currency
     * @param card
     * @param voucherDetailView
     * @return
     * @throws AnaliaException
     */
    String generatePurchaseReceipt(Purchase purchase, String currency, Source card, VoucherDetailView voucherDetailView, User user) throws AnaliaException;

    /**
     * @param voucherId
     * @param amount
     * @return
     * @throws AnaliaException
     */
    List<VoucherVendorTaxGroupView> getVoucherVendorTaxGroupViewRange(BigInteger voucherId, BigDecimal amount) throws AnaliaException;

    /**
     * @param vendorId
     * @param startDate
     * @param endDate
     * @return
     * @throws AnaliaException
     */
    List<PurchaseDetailView> getListPurchaseDetailViewForVendorId(BigInteger vendorId, Date startDate, Date endDate) throws AnaliaException;

    /**
     * @param startDate
     * @param endDate
     * @return
     * @throws AnaliaException
     */
    List<PurchaseDetailView> getListPurchaseDetailView(Date startDate, Date endDate) throws AnaliaException;


    /**
     * @param purchaseDetail
     * @param validLocation
     * @return
     * @throws AnaliaException
     */
    PurchaseDetail redeemVoucher(PurchaseDetail purchaseDetail, ValidLocation validLocation, BigInteger userId) throws AnaliaException;


}
