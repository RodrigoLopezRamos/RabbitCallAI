package com.analia.trade.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.TradeAdditionalData;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

import java.math.BigInteger;
import java.util.List;

@ApplicationScoped
public class TradeAdditionalDataFacade extends JPAPersistenceFacade<TradeAdditionalData> implements TradeAdditionalDataFacadeLocal {
    public static final String QUERY_GET_TRADE_ADDITIONAL_DATA = "getTradeAdditionalData";
    public static final String PARAM_TRADE_ID = "tradeId";

    @Inject
    private EntityManager entityManager;

    public TradeAdditionalDataFacade() {
        super(TradeAdditionalData.class);
    }


    public List<TradeAdditionalData> geTradeAdditionalDataByTradeId(BigInteger tradeId) throws AnaliaException {
        return getListForNamedQuery(QUERY_GET_TRADE_ADDITIONAL_DATA, new JpqlParameter(PARAM_TRADE_ID, tradeId));
    }




}
