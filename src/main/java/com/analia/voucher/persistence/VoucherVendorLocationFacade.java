package com.analia.voucher.persistence;

import com.analia.common.model.VendorLocationVoucher;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.voucher.persistence.VoucherVendorLocationFacadeLocal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;


@ApplicationScoped
public class VoucherVendorLocationFacade extends JPAPersistenceFacade<VendorLocationVoucher> implements VoucherVendorLocationFacadeLocal {
    @Inject
    private EntityManager entityManager;

    public VoucherVendorLocationFacade() {
        super(VendorLocationVoucher.class);
    }




}
