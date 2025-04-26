package com.analia.voucher.persistence;

import com.analia.common.model.Tax;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.voucher.persistence.TaxFacadeLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;


@ApplicationScoped
public class TaxFacade extends JPAPersistenceFacade<Tax> implements TaxFacadeLocal {
    @Inject
    private EntityManager entityManager;

    public TaxFacade() {
        super(Tax.class);
    }




}
