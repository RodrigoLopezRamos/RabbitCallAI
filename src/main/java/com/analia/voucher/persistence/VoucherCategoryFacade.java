package com.analia.voucher.persistence;

import com.analia.common.model.VoucherCategory;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.voucher.persistence.VoucherCategoryFacadeLocal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;


@ApplicationScoped
public class VoucherCategoryFacade extends JPAPersistenceFacade<VoucherCategory> implements VoucherCategoryFacadeLocal {


    @Inject
    private EntityManager entityManager;

    public VoucherCategoryFacade() {
        super(VoucherCategory.class);
    }




}
