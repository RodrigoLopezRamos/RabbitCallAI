package com.analia.trade.persistence;

import com.analia.common.model.TradeLocation;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.trade.persistence.TradeLocationFacadeLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class TradeLocationFacade extends JPAPersistenceFacade<TradeLocation> implements TradeLocationFacadeLocal {

    @Inject
    private EntityManager entityManager;

    public TradeLocationFacade() {
        super(TradeLocation.class);
    }
}
