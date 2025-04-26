package com.analia.trade.persistence;

import com.analia.common.model.TradeCategory;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.trade.persistence.TradeCategoryFacadeLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;


@ApplicationScoped
public class TradeCategoryFacade extends JPAPersistenceFacade<TradeCategory> implements TradeCategoryFacadeLocal {

    @Inject
    private EntityManager entityManager;

    public TradeCategoryFacade() {
        super(TradeCategory.class);
    }



}
