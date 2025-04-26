package com.analia.purchase.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Purchase;
import com.analia.common.model.resultset.view.PurchaseDetailView;
import com.analia.common.model.resultset.view.VoucherVendorTaxGroupView;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.purchase.persistence.PurchaseFacadeLocal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Session Bean implementation class PurchaseFacade
 */
@ApplicationScoped
public class PurchaseFacade extends JPAPersistenceFacade<Purchase> implements PurchaseFacadeLocal {

    public static final String QUERY_GET_ACTIVE_VOUCHER_BY_VENDOR = "getActiveVoucherByVendor";
    public static final String QUERY_GET_REMAINING_ALLONWANCE_VOUCHER_BY_USER = "getRemainingAllowanceVoucherByUser";
    public static final String QUERY_GET_VOUCHER_VENDOR_TAX_GROUP_RANGE = "getVoucherDetailViewTaxGroupRange";
    public static final String QUERY_GET_PERK_MERCHANT_TAX_GROUP_RANGE_FOR_PURCHASE = "getVoucherVendorTaxGroupViewPurchase";
    public static final String QUERY_GET_PURCHASE_LIST_BY_USER_ID = "getPurchaseListByUserId";
    public static final String QUERY_GET_PURCHASE_LIST_BY_USER_AND_PURCHASE_ID = "getPurchaseDetailByUserIdAndPurchaseId";
    public static final String QUERY_GET_PURCHASE_DETAIL_BY_ID_AND_USER = "getPurchaseDetail";
    public static final String QUERY_GET_PURCHASE_LIST_BY_USER = "getPurchaseListByUser";
    public static final String PARAM_VOUCHER_ID = "voucherId";
    public static final String PARAM_USER_ID = "userId";
    public static final String PARAM_AMOUNT = "amount";
    public static final String PARAM_QUANTITY = "quantity";
    public static final String PARAM_PURCHASE_ID = "purchaseId";
    public static final String PARAM_PURCHASE_DETAIL_ID = "purchaseDetailId";
    @Inject
    private EntityManager entityManager;


    public PurchaseFacade() {
        super(Purchase.class);
    }




    /**
     *
     */

    public int getRemainingAllowanceVoucherByUser(BigInteger voucherId, BigInteger userId) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_GET_REMAINING_ALLONWANCE_VOUCHER_BY_USER);
        query.setParameter(PARAM_VOUCHER_ID, voucherId);
        query.setParameter(PARAM_USER_ID, userId);
        Number number = (Number) query.getSingleResult();
        if (number == null) {
            number = new BigDecimal(0);
        }
        return number.intValue();
    }

    @SuppressWarnings("unchecked")
    public List<VoucherVendorTaxGroupView> getVoucherVendorTaxGroupViewRange(BigInteger voucherId, BigDecimal amount) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_GET_VOUCHER_VENDOR_TAX_GROUP_RANGE, VoucherVendorTaxGroupView.class);
        query.setParameter(PARAM_VOUCHER_ID, voucherId);
        query.setParameter(PARAM_AMOUNT, amount);
        return query.getResultList();
    }


    @SuppressWarnings("unchecked")

    public List<VoucherVendorTaxGroupView> getVoucherVendorTaxGroupViewPurchase(BigInteger voucherId, BigInteger quantity) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_GET_PERK_MERCHANT_TAX_GROUP_RANGE_FOR_PURCHASE, VoucherVendorTaxGroupView.class);
        query.setParameter(PARAM_VOUCHER_ID, voucherId);
        query.setParameter(PARAM_QUANTITY, quantity);
        return query.getResultList();
    }

    /**
     *
     */
    @SuppressWarnings("unchecked")

    public List<PurchaseDetailView> getPurchaseListByUserIdWithPagination(BigInteger userId, int page, int size) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_GET_PURCHASE_LIST_BY_USER_ID, PurchaseDetailView.class);
        query.setFirstResult(page);
        query.setMaxResults(size);
        query.setParameter(PARAM_USER_ID, userId);
        return query.getResultList();
    }

    /**
     *
     */
    @SuppressWarnings("unchecked")

    public List<PurchaseDetailView> getPurchaseDetailByUserIdAndPurchaseId(BigInteger userId, BigInteger purchaseId) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_GET_PURCHASE_LIST_BY_USER_AND_PURCHASE_ID, PurchaseDetailView.class);
        query.setParameter(PARAM_USER_ID, userId);
        query.setParameter(PARAM_PURCHASE_ID, purchaseId);
        return query.getResultList();
    }


    public PurchaseDetailView getPurchaseDetail(BigInteger purchaseDetailId, BigInteger userId) throws AnaliaException {
        TypedQuery<PurchaseDetailView> query = entityManager.createNamedQuery(QUERY_GET_PURCHASE_DETAIL_BY_ID_AND_USER, PurchaseDetailView.class);
        query.setParameter(PARAM_USER_ID, userId);
        query.setParameter(PARAM_PURCHASE_DETAIL_ID, purchaseDetailId);
        List<PurchaseDetailView> result = query.getResultList();
        return (result != null && result.size() > 0 ? result.get(0) : null);
    }

    @SuppressWarnings("unchecked")

    public List<PurchaseDetailView> getPurchaseListByUser(BigInteger userId) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_GET_PURCHASE_LIST_BY_USER, PurchaseDetailView.class);
        query.setParameter(PARAM_USER_ID, userId);
        return query.getResultList();
    }
}
