package com.analia.purchase.core;

import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.*;
import com.analia.common.model.resultset.view.PurchaseDetailView;
import com.analia.common.model.resultset.view.VoucherDetailView;
import com.analia.common.model.resultset.view.VoucherVendorTaxGroupView;
import com.analia.common.util.Base26;
import com.analia.common.util.DateUtils;
import com.analia.purchase.persistence.*;
import com.analia.purchase.util.ReceiptUtils;
import com.analia.setttings.core.impl.SettingsCore;
import com.analia.setttings.service.impl.SettingsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;


/**
 * @author Rodrigo Lopez
 */
@ApplicationScoped
public class PurchaseCore implements PurchaseCoreLocal {

    @Inject
    private SourceFacade sourceFacadeLocal;
    @Inject
    private SourceTypeFacade sourceTypeFacadeLocal;
    @Inject
    private PurchaseFacade purchaseFacadeLocal;
    @Inject
    private PurchaseDetailFacade purchaseDetailFacadeLocal;
    @Inject
    private PurchaseDetailViewFacade purchaseDetailViewFacadeLocal;
    @Inject
    private VendorLocationVoucherFacade vendorLocationVoucherFacadeLocal;
    @Inject
    private SettingsService settingsServiceLocal;

    /**
     *
     */

    public Purchase savePurchase(BigInteger purchaseId, BigInteger sourceId, BigInteger voucherVendorId, BigInteger voucherDirectoryId, BigInteger totalVouchers, BigInteger creditsPaid, BigDecimal amountPaid, BigDecimal totalTaxPaid, String paymentReferenceNumber, BigDecimal processingFeePercentage, BigInteger userId, BigInteger clientId) throws AnaliaException {
        Purchase purchase = null;
        try {
            if ((purchase = purchaseFacadeLocal.find(purchaseId)) == null) {
                purchase = new Purchase();
                purchase.setUserId(userId);
                purchase.setCreatedDatetime(new Date());
                purchase.setClientId(clientId);
            }
            purchase.setId(purchaseId);
            purchase.setSourceId(sourceId);
            purchase.setVouchervendorId(voucherVendorId);
            purchase.setDirectoryId(voucherDirectoryId);
            purchase.setTotalVouchers(totalVouchers);
            purchase.setCreditsPaid(creditsPaid);
            purchase.setAmountPaid(amountPaid);
            purchase.setTotalTaxPaid(totalTaxPaid);
            purchase.setPaymentReference(paymentReferenceNumber);
            purchase.setProcessingFeePercent(processingFeePercentage);
            purchaseFacadeLocal.save(purchase);
            purchaseFacadeLocal.flush();
            return purchase;
        } catch (AnaliaException baseException) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, baseException.getMessage());
        }
    }

    /**
     *
     */

    public PurchaseDetail savePurchaseDetail(BigInteger purchaseDetailId, BigInteger purchaseId, BigInteger voucherVendorId, BigInteger taxGroupId, BigInteger voucherFileId, String digitalVoucher, BigDecimal relativePercentage, BigDecimal feesPaid, BigInteger userId) throws AnaliaException {
        PurchaseDetail purchaseDetail = null;
        try {
            if ((purchaseDetail = purchaseDetailFacadeLocal.find(purchaseDetailId)) == null) {
                purchaseDetail = new PurchaseDetail();
                purchaseDetail.setCreateDatetime(new Date());
            }
            purchaseDetail.setId(purchaseDetailId);
            purchaseDetail.setPurchaseId(purchaseId);
            purchaseDetail.setVoucherVendorId(voucherVendorId);
            purchaseDetail.setTaxGroupId(taxGroupId);
            purchaseDetail.setVoucherfileId(voucherFileId);
            purchaseDetail.setVoucher(digitalVoucher);
            purchaseDetail.setRelativePercentage(relativePercentage);
            purchaseDetail.setFeesPaid(feesPaid);
            purchaseDetailFacadeLocal.save(purchaseDetail);
            purchaseDetailFacadeLocal.flush();
            return purchaseDetail;
        } catch (AnaliaException baseException) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, baseException.getMessage());
        }
    }

    /**
     *
     */

    public Source saveSource(BigInteger cardId, BigInteger sourcetypeId, String cardHolderName, String nickName, String providerToken, String maskedCardNumber, long expiryYear, long expiryMonth, boolean defaultCard, boolean disabled, BigInteger userId) throws AnaliaException {
        Source source = null;
        try {
            if ((source = sourceFacadeLocal.find(cardId)) == null) {
                source = new Source();
                source.setUserId(userId);
            }
            source.setId(cardId);
            source.setSourcetypeId(sourcetypeId);
            source.setHolderName(cardHolderName);
            source.setNickname(nickName);
            source.setToken(providerToken);
            source.setMaskedCardNumber(maskedCardNumber);
            source.setExpiryMonth(Long.valueOf(expiryMonth).intValue());
            source.setExpiryYear(Long.valueOf(expiryYear).intValue());
            source.setDisabled(disabled);
            source.setDefaultCard(defaultCard);
            sourceFacadeLocal.save(source);
            return source;

        } catch (AnaliaException baseException) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, baseException.getMessage());
        }
    }

    /**
     *
     */

    public Source getSourceByUser(BigInteger sourceId, BigInteger userId) throws AnaliaException {
        Source card = sourceFacadeLocal.getSourceByIdAndUserId(sourceId, userId);
        return card;
    }

    /**
     *
     */

    public List<Source> getSourceList(BigInteger userId) throws AnaliaException {
        return sourceFacadeLocal.getSourcesByUserId(userId);
    }

    /**
     *
     */

    public boolean setDefaultSource(BigInteger userId, BigInteger sourceId) throws AnaliaException {
        sourceFacadeLocal.setAllUserSourceToBeUnDefault(userId);
        return sourceFacadeLocal.setDefaultSource(userId, sourceId);
    }

    /**
     *
     */

    public int getRemainingAllowanceVoucherByUser(BigInteger voucherId, BigInteger userId) throws AnaliaException {
        return purchaseFacadeLocal.getRemainingAllowanceVoucherByUser(voucherId, userId);
    }

    /**
     *
     */

    public List<VoucherVendorTaxGroupView> getTaxPercentageByVoucherAndQuantity(BigInteger voucherId, BigInteger quantity) throws AnaliaException {
        if (quantity.intValue() <= 0) {
            throw new AnaliaException(ExceptionCode.PARAMETER_IS_MANDATORY, "Quantity has to be greather than 0");
        }
        List<VoucherVendorTaxGroupView> voucherVendorTaxGroupViews = purchaseFacadeLocal.getVoucherVendorTaxGroupViewPurchase(voucherId, quantity);
        if (voucherVendorTaxGroupViews == null || voucherVendorTaxGroupViews.size() == 0) {
            throw new AnaliaException(ExceptionCode.ENTITY_NOT_FOUND, "VOUCHER DOES NOT EXIST ON DATABASE !" + voucherId);
        }
        return voucherVendorTaxGroupViews;
    }


    /**
     *
     */

    public VendorLocationVoucher getVendorLocationVoucherForRedeemingVoucher(BigInteger voucherVendorId, BigInteger vendorLocationId) throws AnaliaException {
        return vendorLocationVoucherFacadeLocal.getVendorLocationVoucher(voucherVendorId, vendorLocationId);
    }

    /**
     *
     */

    public List<PurchaseDetailView> getPurchaseListByUserId(BigInteger userId, int page, int size) throws AnaliaException {
        return purchaseFacadeLocal.getPurchaseListByUserIdWithPagination(userId, page, size);
    }

    /**
     *
     */

    public List<PurchaseDetailView> getPurchaseListByUserId(BigInteger userId) throws AnaliaException {
        return purchaseFacadeLocal.getPurchaseListByUser(userId);
    }

    /**
     *
     */

    public boolean setVoucherLocation(BigInteger purchaseDetailId, BigInteger vendorLocationId, BigInteger userId) throws AnaliaException {
        PurchaseDetailView purchaseDetailView = getPurchaseDetail(purchaseDetailId, userId);
        PurchaseDetail purchaseDetail = purchaseDetailFacadeLocal.find(purchaseDetailView.getId());
        VendorLocationVoucher vendorLocationVoucher = vendorLocationVoucherFacadeLocal.getVendorLocationVoucher(purchaseDetail.getVoucherVendorId(), vendorLocationId);
        if (vendorLocationVoucher == null) {
            throw new AnaliaException(ExceptionCode.ENTITY_NOT_FOUND, "Location is not found");
        }
        purchaseDetail.setDefaultLocation(vendorLocationVoucher.getId());
        purchaseDetailFacadeLocal.save(purchaseDetail);
        return true;
    }


    /**
     *
     */

    public Purchase savePurchase(Purchase purchase) throws AnaliaException {
        purchaseFacadeLocal.save(purchase);
        return purchase;
    }

    /**
     *
     */

    public Purchase getPurchase(BigInteger purchaseId) throws AnaliaException {
        return purchaseFacadeLocal.find(purchaseId);
    }

    /**
     *
     */

    public String generatePurchaseReceipt(Purchase purchase, String currency, Source card, VoucherDetailView voucherDetailView, User user) throws AnaliaException {
        String receiptTemplate = settingsServiceLocal.getStringValueForSettingKey(SettingsCore.SYSTEM_RECEIPT_TEMPLATE);
        receiptTemplate = receiptTemplate.replace(ReceiptUtils.RECEIPT_USER_EMAIL, user.getPersona().getEmail());
        receiptTemplate = receiptTemplate.replace(ReceiptUtils.RECEIPT_USER_NAME, user.getPersona().getFirstName() == null ? user.getPersona().getEmail() : user.getPersona().getFirstName() + " " + user.getPersona().getLastName() == null ? "" : user.getPersona().getLastName());
        receiptTemplate = receiptTemplate.replace(ReceiptUtils.RECEIPT_ORDER_CODE, Base26.encode(purchase.getId()));
        receiptTemplate = receiptTemplate.replace(ReceiptUtils.RECEIPT_DATE, DateUtils.formatDate(purchase.getCreatedDatetime()));
        receiptTemplate = receiptTemplate.replace(ReceiptUtils.RECEIPT_TOTAL, purchase.getAmountPaid().toPlainString());
        receiptTemplate = receiptTemplate.replace(ReceiptUtils.RECEIPT_CARD_NAME, card.getMaskedCardNumber());
        receiptTemplate = receiptTemplate.replace(ReceiptUtils.RECEIPT_PHONE, user.getPersona().getPhoneNumber() == null ? "" : user.getPersona().getPhoneNumber());
        receiptTemplate = receiptTemplate.replace(ReceiptUtils.RECEIPT_WEBSITE, voucherDetailView.getWebsite());
        receiptTemplate = receiptTemplate.replace(ReceiptUtils.RECEIPT_SUBTOTAL, purchase.getAmountPaid().subtract(purchase.getTotalTaxPaid()).toPlainString());
        receiptTemplate = receiptTemplate.replace(ReceiptUtils.RECEIPT_TAX, purchase.getTotalTaxPaid().toPlainString());
        receiptTemplate = receiptTemplate.replace(ReceiptUtils.RECEIPT_TERMS_OF_USE, voucherDetailView.getTermsUrl());
        receiptTemplate = receiptTemplate.replace(ReceiptUtils.RECEIPT_VENDOR_NAME, voucherDetailView.getVoucherProvider());
        receiptTemplate = receiptTemplate.replace(ReceiptUtils.RECEIPT_VOUCHER_TITLE, voucherDetailView.getTitle());
        receiptTemplate = receiptTemplate.replace(ReceiptUtils.RECEIPT_VOUCHER_DESCRIPTION, voucherDetailView.getDescription());
        receiptTemplate = receiptTemplate.replace(ReceiptUtils.RECEIPT_VOUCHER_QUANTITY, purchase.getTotalVouchers().toString());
        receiptTemplate = receiptTemplate.replace(ReceiptUtils.RECEIPT_VOUCHER_PRICE, voucherDetailView.getPrice().toPlainString());
        return receiptTemplate;
    }

    /**
     *
     */

    public List<VoucherVendorTaxGroupView> getVoucherVendorTaxGroupViewRange(BigInteger voucherId, BigDecimal amount) throws AnaliaException {
        return purchaseFacadeLocal.getVoucherVendorTaxGroupViewRange(voucherId, amount);
    }

    /**
     *
     */

    public List<PurchaseDetailView> getListPurchaseDetailViewForVendorId(BigInteger vendorId, Date startDate, Date endDate) throws AnaliaException {
        return purchaseDetailViewFacadeLocal.getListPurchaseDetailViewForVendorId(vendorId, startDate, endDate);
    }


    public Source saveSource(Source source) throws AnaliaException {
        sourceFacadeLocal.save(source);
        return source;
    }


    public List<PurchaseDetailView> getListPurchaseDetailView(Date startDate, Date endDate) throws AnaliaException {
        return purchaseDetailViewFacadeLocal.getListPurchaseDetailView(startDate, endDate);
    }

    /**
     *
     */

    public List<PurchaseDetailView> getPurchaseDetailByUserIdAndPurchaseId(BigInteger userId, BigInteger purchaseId) throws AnaliaException {
        return purchaseFacadeLocal.getPurchaseDetailByUserIdAndPurchaseId(userId, purchaseId);
    }

    /**
     *
     */

    public PurchaseDetailView getPurchaseDetail(BigInteger purchaseDetailId, BigInteger userId) throws AnaliaException {
        PurchaseDetailView purchaseDetail = purchaseFacadeLocal.getPurchaseDetail(purchaseDetailId, userId);
        return purchaseDetail;
    }

    /**
     *
     */

    public Source getSource(BigInteger userId, BigInteger sourceId) throws AnaliaException {
        Source source = sourceFacadeLocal.getSourceByIdAndUserId(sourceId, userId);
        if (source == null) {
            throw new AnaliaException(ExceptionCode.ENTITY_NOT_FOUND, "There is not source with Id :" + sourceId);
        }
        return source;
    }

    /**
     *
     */

    public PurchaseDetail getPurchaseDetailForVoucher(String voucher) throws AnaliaException {
        return purchaseDetailFacadeLocal.getPurchaseDetailForVoucher(voucher);
    }

    /**
     *
     */

    public List<PurchaseDetail> getListPurchaseDetailForVoucherId(BigInteger voucherId) throws AnaliaException {
        return purchaseDetailFacadeLocal.getListPurchaseDetailForVoucherId(voucherId);
    }

    /**
     *
     */
    public List<SourceType> getAllSourceTypes() throws AnaliaException {
        return sourceTypeFacadeLocal.findAllJPa();
    }

    /**
     *
     */

    public List<PurchaseDetailView> getPurchaseDetailsViewForVendorLocationVoucherId(BigInteger vendorLocationVoucherId) throws AnaliaException {
        return purchaseDetailViewFacadeLocal.getAllPurchaseDetailsForVendorLocationVoucherId(vendorLocationVoucherId);
    }


    /**
     *
     */

    public Source getDefaultSourceByUserId(BigInteger userId) throws AnaliaException {
        return sourceFacadeLocal.getDefaultSourceByUserId(userId);
    }

    /**
     *
     */

    public PurchaseDetail getPurchaseDetailById(BigInteger puchaseDetail) throws AnaliaException {
        return purchaseDetailFacadeLocal.find(puchaseDetail);
    }

    /**
     *
     */

    public PurchaseDetail redeemVoucher(PurchaseDetail purchaseDetail, ValidLocation validLocation, BigInteger userId) throws AnaliaException {
        VendorLocationVoucher vendorLocationVoucher = vendorLocationVoucherFacadeLocal.getVendorLocationVoucher(purchaseDetail.getVoucherVendorId(), validLocation.getVendorLocationId());
        purchaseDetail.setRedeemedBy(userId);
        purchaseDetail.setRedeemedDatetime(new Date());
        purchaseDetail.setRedeemedVendorLocationId(vendorLocationVoucher.getVendorLocationId());
        purchaseDetail.setVerified(true);
        purchaseDetailFacadeLocal.save(purchaseDetail);
        return purchaseDetail;
    }


    /**
     *
     */

    public SourceType getSourceTypeById(BigInteger sourceTypeId) throws AnaliaException {
        List<SourceType> listCardTypes = getAllSourceTypes();
        for (SourceType cT : listCardTypes) {
            if (cT.getId().compareTo(sourceTypeId) == 0) {
                return cT;
            }
        }
        throw new AnaliaException(ExceptionCode.ENTITY_NOT_FOUND, "The source type was not found in the database");
    }

    /**
     *
     */

    public SourceType getSourceTypeByName(String cardTypeName) throws AnaliaException {
        List<SourceType> listCardTypes = getAllSourceTypes();
        for (SourceType cT : listCardTypes) {
            if (cT.getName().equalsIgnoreCase(cardTypeName)) {
                return cT;
            }
        }
        throw new AnaliaException(ExceptionCode.ENTITY_NOT_FOUND, "The source type not supported");
    }
}
