package com.analia.voucher.persistence;

import com.analia.common.model.resultset.view.VoucherDetailView;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.voucher.persistence.VoucherDetailViewFacadeLocal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

@ApplicationScoped
public class VoucherDetailViewFacade extends JPAPersistenceFacade<VoucherDetailView> implements VoucherDetailViewFacadeLocal {
    @Inject
    private EntityManager entityManager;

    public VoucherDetailViewFacade() {
        super(VoucherDetailView.class);
    }




}
