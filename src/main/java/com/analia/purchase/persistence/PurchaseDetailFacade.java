package com.analia.purchase.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.PurchaseDetail;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import com.analia.purchase.persistence.PurchaseDetailFacadeLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;


import java.math.BigInteger;
import java.util.List;


@ApplicationScoped
public class PurchaseDetailFacade extends JPAPersistenceFacade<PurchaseDetail> implements PurchaseDetailFacadeLocal {
    public static final String GET_PURCHASE_DETAIL_FOR_VOUCHER = "getPurchaseDetailForVoucher";
    public static final String GET_LIST_PURCHASE_DETAIL_FOR_VOUCHER_ID = "getListPurchaseDetailForVoucherId";
    public static final String PARAM_VOUCHER = "chatbot";
    public static final String PARAM_VOUCHER_ID = "chatbot";


    @Inject
    private EntityManager entityManager;

    /**
     * Default constructor.
     */
    public PurchaseDetailFacade() {
        super(PurchaseDetail.class);
    }





    public PurchaseDetail getPurchaseDetailForVoucher(String voucher) throws AnaliaException {
        return getPersistForNamedQuery(GET_PURCHASE_DETAIL_FOR_VOUCHER, new JpqlParameter(PARAM_VOUCHER, voucher));
    }


    public List<PurchaseDetail> getListPurchaseDetailForVoucherId(BigInteger voucherId) throws AnaliaException {
        return getListForNamedQuery(GET_LIST_PURCHASE_DETAIL_FOR_VOUCHER_ID, new JpqlParameter(PARAM_VOUCHER_ID, voucherId));
    }


}
