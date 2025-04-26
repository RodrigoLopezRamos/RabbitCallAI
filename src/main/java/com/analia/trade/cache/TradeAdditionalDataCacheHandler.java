package com.analia.trade.cache;

import com.analia.cache.handler.CacheHandler;
import com.analia.common.cache.CacheKey;
import com.analia.common.exception.AnaliaException;
import com.analia.common.model.TradeAdditionalData;
import com.analia.trade.persistence.TradeAdditionalDataFacadeLocal;
import com.analia.trade.persistence.TradeAdditionalDataFacade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


import java.math.BigInteger;
import java.util.List;

@ApplicationScoped
public class TradeAdditionalDataCacheHandler implements CacheHandler<TradeAdditionalData> {
    @Inject
    private TradeAdditionalDataFacadeLocal tradeAdditionalDataFacadeLocal;


    public List<TradeAdditionalData> execute(CacheKey cacheKey) throws AnaliaException {
        return tradeAdditionalDataFacadeLocal.geTradeAdditionalDataByTradeId((BigInteger) cacheKey.get(TradeAdditionalDataFacade.PARAM_TRADE_ID));
    }

}
