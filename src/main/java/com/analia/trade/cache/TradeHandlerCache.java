package com.analia.trade.cache;

import com.analia.cache.handler.CacheHandler;
import com.analia.common.cache.CacheKey;
import com.analia.common.exception.AnaliaException;
import com.analia.common.model.resultset.TradeResultSet;
import com.analia.trade.persistence.TradeFacadeLocal;
import com.analia.trade.persistence.TradeFacade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;


import java.math.BigInteger;
import java.util.List;

@ApplicationScoped
@Named("TradeHandlerCache")
public class TradeHandlerCache implements CacheHandler<TradeResultSet> {
    @Inject
    private TradeFacadeLocal tradeFacadeLocal;


    public List<TradeResultSet> execute(CacheKey cacheKey) throws AnaliaException {
        int categoryTypeId = (Integer) cacheKey.get(TradeFacade.PARAM_CATEGORY_TYPE_ID);
        BigInteger categoryId = (BigInteger) cacheKey.get(TradeFacade.PARAM_CATEGORY_ID);
        BigInteger zoneId = (BigInteger) cacheKey.get(TradeFacade.PARAM_ZONE_ID);

        List<TradeResultSet> listTrades = tradeFacadeLocal.getTrades(categoryTypeId, categoryId, zoneId);
        return listTrades;
    }

}
