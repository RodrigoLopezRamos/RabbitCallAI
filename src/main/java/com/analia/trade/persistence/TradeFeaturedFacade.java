package com.analia.trade.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.TradeFeatured;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.trade.persistence.TradeFeaturedFacadeLocal;

import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;
import java.math.BigInteger;
import java.util.List;

public class TradeFeaturedFacade extends JPAPersistenceFacade<TradeFeatured> implements TradeFeaturedFacadeLocal {

    private EntityManager entityManager;

    public TradeFeaturedFacade() {
        super(TradeFeatured.class);
    }





    public List<TradeFeatured> getTradeFeatured(BigInteger zoneId) throws AnaliaException {
        return null;
    }
}
