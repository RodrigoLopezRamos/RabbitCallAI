package com.analia.purchase.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.resultset.view.PurchaseDetailView;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class PurchaseDetailViewFacade extends JPAPersistenceFacade<PurchaseDetailView> implements PurchaseDetailViewFacadeLocal {
    public static final String QUERY_GET_PURCHASE_DETAIL_VIEW_FOR_VENDOR_LOCATION_VOUCHER_ID = "getPurchaseDetailViewForVendorLocationVoucherId";
    public static final String QUERY_GET_PURCHASE_DETAIL_VIEW_FOR_VENDOR_ID = "getListPurchaseDetailViewForVendorId";
    public static final String QUERY_GET_LIST_PURCHASE_DETAIL_VIEW = "getListPurchaseDetailView";
    public static final String QUERY_GET_LIST_PURCHASE_DETAIL_VIEW_FOR_VENDOR_LOCATION_VOUCHER_ID = "getAllPurchaseDetailsForVendorLocationVoucherId";
    public static final String PARAM_VENDOR_LOCATION_VOUCHER_ID = "vendorLocationVoucherId";
    public static final String PARAM_VENDOR_ID = "vendorId";
    public static final String PARAM_START_DATE = "startDate";
    public static final String PARAM_END_DATE = "endDate";

    @Inject
    private EntityManager entityManager;

    public PurchaseDetailViewFacade() {
        super(PurchaseDetailView.class);
    }


    public List<PurchaseDetailView> getListOfPurchaseDetailViewForVendorLocationVoucherId(BigInteger vendorLocationVoucherId) throws AnaliaException {
        return getListForNamedQuery(QUERY_GET_PURCHASE_DETAIL_VIEW_FOR_VENDOR_LOCATION_VOUCHER_ID, new JpqlParameter(PARAM_VENDOR_LOCATION_VOUCHER_ID, vendorLocationVoucherId));
    }


    public List<PurchaseDetailView> getListPurchaseDetailViewForVendorId(BigInteger vendorId, Date startDate, Date endDate) throws AnaliaException {
        return getListForNamedQuery(QUERY_GET_PURCHASE_DETAIL_VIEW_FOR_VENDOR_ID, new JpqlParameter(PARAM_VENDOR_ID, vendorId), new JpqlParameter(PARAM_START_DATE, startDate), new JpqlParameter(PARAM_END_DATE, endDate));
    }


    public List<PurchaseDetailView> getListPurchaseDetailView(Date startDate, Date endDate) throws AnaliaException {
        return getListForNamedQuery(QUERY_GET_LIST_PURCHASE_DETAIL_VIEW, new JpqlParameter(PARAM_START_DATE, startDate), new JpqlParameter(PARAM_END_DATE, endDate));
    }


    public List<PurchaseDetailView> getAllPurchaseDetailsForVendorLocationVoucherId(BigInteger vendorLocationVoucherId) throws AnaliaException {
        return getListForNamedQuery(QUERY_GET_LIST_PURCHASE_DETAIL_VIEW_FOR_VENDOR_LOCATION_VOUCHER_ID, new JpqlParameter(PARAM_VENDOR_LOCATION_VOUCHER_ID, vendorLocationVoucherId));
    }




}
