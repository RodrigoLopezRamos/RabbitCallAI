package com.analia.voucher.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.VoucherVendor;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.voucher.persistence.VoucherVendorFacadeLocal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

import java.math.BigInteger;

@ApplicationScoped
public class VoucherVendorFacade extends JPAPersistenceFacade<VoucherVendor> implements VoucherVendorFacadeLocal {

    @Inject
    private EntityManager entityManager;


    public VoucherVendorFacade() {
        super(VoucherVendor.class);
    }





    public VoucherVendor getVoucherVendor(BigInteger vendorId, BigInteger voucherId) throws AnaliaException {
        // TODO Auto-generated method stub
        return null;
    }

}
