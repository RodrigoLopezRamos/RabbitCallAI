package com.analia.trade.cache;

import com.analia.cache.handler.CacheHandler;
import com.analia.common.cache.CacheKey;
import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Tag;
import com.analia.trade.persistence.TradeTagsFacadeLocal;
import com.analia.trade.persistence.TradeTagsFacade;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

import java.math.BigInteger;
import java.util.List;

@ApplicationScoped
@Named("TradeTagsCacheHandler")
public class TradeTagsCacheHandler implements CacheHandler<Tag> {
    @Inject
    private TradeTagsFacadeLocal tradeTagsFacadeLocal;


    public List<Tag> execute(CacheKey cacheKey) throws AnaliaException {
        return tradeTagsFacadeLocal.getTradeTagsByTradeId((BigInteger) cacheKey.get(TradeTagsFacade.PARAM_TRADE_ID));
    }

}
