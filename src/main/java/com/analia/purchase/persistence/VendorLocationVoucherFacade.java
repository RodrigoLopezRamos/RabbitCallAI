package com.analia.purchase.persistence;

import com.analia.common.model.VendorLocationVoucher;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import com.analia.purchase.persistence.VendorLocationVoucherFacadeLocal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

import java.math.BigInteger;

@ApplicationScoped
public class VendorLocationVoucherFacade extends JPAPersistenceFacade<VendorLocationVoucher> implements VendorLocationVoucherFacadeLocal {
    public static final String QUERY_GET_VENDOR_LOCATION_VOUCHER = "getVendorLocationVoucher";
    public static final String PARAM_VOUCHER_VENDOR_ID = "voucherVendorId";
    public static final String PARAM_VENDOR_LOCATION_ID = "vendorLocationId";

    @Inject
    private EntityManager entityManager;

    public VendorLocationVoucherFacade() {
        super(VendorLocationVoucher.class);
    }



    public VendorLocationVoucher getVendorLocationVoucher(BigInteger voucherVendorId, BigInteger vendorLocationId) {
        return getPersistForNamedQuery(QUERY_GET_VENDOR_LOCATION_VOUCHER, new JpqlParameter(PARAM_VOUCHER_VENDOR_ID, voucherVendorId), new JpqlParameter(PARAM_VOUCHER_VENDOR_ID, vendorLocationId));
    }




}
