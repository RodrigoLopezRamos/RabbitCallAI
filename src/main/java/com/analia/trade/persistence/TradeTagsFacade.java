package com.analia.trade.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Tag;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import com.analia.trade.persistence.TradeTagsFacadeLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.math.BigInteger;
import java.util.List;

@ApplicationScoped
public class TradeTagsFacade extends JPAPersistenceFacade<Tag> implements TradeTagsFacadeLocal {
    public static final String QUERY_GET_TRADE_TAG_BY_ID = "getTradeTagsByTradeId";
    public static final String PARAM_TRADE_ID = "tradeId";

    @Inject
    private EntityManager entityManager;

    public TradeTagsFacade() {
        super(Tag.class);
    }


    public List<Tag> getTradeTagsByTradeId(BigInteger tradeId) throws AnaliaException {
        return getListForNamedQuery(QUERY_GET_TRADE_TAG_BY_ID, new JpqlParameter(PARAM_TRADE_ID, tradeId));
    }



}
