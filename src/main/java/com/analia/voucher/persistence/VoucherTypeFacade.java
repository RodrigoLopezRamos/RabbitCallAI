package com.analia.voucher.persistence;

import com.analia.common.model.VoucherType;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.voucher.persistence.VoucherTypeFacadeLocal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;


@ApplicationScoped
public class VoucherTypeFacade extends JPAPersistenceFacade<VoucherType> implements VoucherTypeFacadeLocal {
    @Inject
    private EntityManager entityManager;

    public VoucherTypeFacade() {
        super(VoucherType.class);
    }





}
