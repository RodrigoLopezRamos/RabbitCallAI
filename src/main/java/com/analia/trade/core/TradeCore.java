package com.analia.trade.core;

import com.analia.cache.core.CacheCore;
import com.analia.cache.util.CacheConfigEnum;
import com.analia.common.cache.CacheKey;
import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.*;
import com.analia.common.model.resultset.TradeResultSet;
import com.analia.trade.persistence.TradeCategoryFacadeLocal;
import com.analia.trade.persistence.TradeFacadeLocal;
import com.analia.trade.persistence.TradeLocationFacadeLocal;
import com.analia.trade.persistence.TradeFacade;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class TradeCore implements TradeCoreLocal {
    @Inject
    private CacheCore cacheCore;
    @Inject
    private TradeFacadeLocal tradeFacadeLocal;
    @Inject
    private TradeLocationFacadeLocal tradeLocationFacadeLocal;
    @Inject
    private TradeCategoryFacadeLocal tradeCategoryFacadeLocal;

    /**
     * @param tradeId
     * @return
     * @throws AnaliaException
     */

    public List<TradeAdditionalData> getTradeDataByTradeId(BigInteger tradeId) throws AnaliaException {
        CacheKey cacheKey = new CacheKey();
        cacheKey.put(TradeFacade.PARAM_TRADE_ID, tradeId);
        return cacheCore.getData(CacheConfigEnum.TRADE_ADDITIONAL_DATA, cacheKey);
    }

    /**
     * @param tradeId
     * @return
     * @throws AnaliaException
     */

    public List<Tag> getTradeTagsByTradeId(BigInteger tradeId) throws AnaliaException {
        CacheKey cacheKey = new CacheKey();
        cacheKey.put(TradeFacade.PARAM_TRADE_ID, tradeId);
        return cacheCore.getData(CacheConfigEnum.TRADE_TAGS, cacheKey);
    }

    /**
     *
     */

    public List<TradeResultSet> getTrades(int categoryTypeId, BigInteger categoryId, BigInteger zoneId) throws AnaliaException {
        CacheKey cacheKey = new CacheKey();
        cacheKey.put(TradeFacade.PARAM_CATEGORY_ID, categoryId);
        cacheKey.put(TradeFacade.PARAM_ZONE_ID, zoneId);
        cacheKey.put(TradeFacade.PARAM_CATEGORY_TYPE_ID, categoryTypeId);
        List<TradeResultSet> trades = cacheCore.getData(CacheConfigEnum.TRADES, cacheKey);
        trades = filterTradesByCurrentUserDateTime(trades);
        return trades;
    }

    /**
     * @param data
     * @return
     * @throws AnaliaException
     */
    private List<TradeResultSet> filterTradesByCurrentUserDateTime(List<TradeResultSet> data) throws AnaliaException {
        Date currentTime = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_DATE_TIME, Date.class);
        List<TradeResultSet> result = new ArrayList<>();
        for (TradeResultSet trade : data) {
            if (currentTime.after(trade.getNotBefore()) && currentTime.before(trade.getNotAfter())) {
                result.add(trade);
            }
        }
        return data;
    }

    /**
     * @param trade
     * @return
     * @throws AnaliaException
     */

    public Trade saveTrade(Trade trade) throws AnaliaException {
        if (trade == null) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, "Trade object must be not null . Baboso!");
        }
        Trade tradeFromDatase = tradeFacadeLocal.find(trade.getId());
        if (tradeFromDatase == null) {
            trade.setCreateDatetime(new Date());
            trade.setCreatedBy(BigInteger.valueOf(1));
        }
        tradeFacadeLocal.save(trade);
        tradeFacadeLocal.flush();
        return trade;
    }

    /**
     * @param tradeLocation
     * @return
     * @throws AnaliaException
     */

    public TradeLocation saveTradeLocation(TradeLocation tradeLocation) throws AnaliaException {
        if (tradeLocation == null) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, "TradeLocation object must be not null . Baboso!");
        }
        tradeLocation.setCreatedDatetime(new Date());
        tradeLocationFacadeLocal.save(tradeLocation);
        tradeLocationFacadeLocal.flush();
        return tradeLocation;
    }

    /**
     * @param tradeCategory
     * @return
     * @throws AnaliaException
     */

    public TradeCategory saveTradeCategory(TradeCategory tradeCategory) throws AnaliaException {
        if (tradeCategory == null) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, "TradeCategory object must be not null . Baboso!");
        }
        tradeCategory.setCreateDatetime(new Date());
        tradeCategoryFacadeLocal.save(tradeCategory);
        return tradeCategory;
    }

    /**
     * @param zoneId
     * @param categoryId
     * @param categoryTypeId
     * @return
     * @throws AnaliaException
     */

    public int totalTradeCountByZoneId(BigInteger zoneId, BigInteger categoryId, int categoryTypeId) throws AnaliaException {
        return tradeFacadeLocal.totalTradeCountByZoneId(zoneId, categoryId, categoryTypeId);
    }

    /**
     * @return
     * @throws AnaliaException
     */

    public List<TradeFeatured> getTradesFeatured() throws AnaliaException {
        return tradeFacadeLocal.getTradesFeatured();
    }

    /**
     * @param tradeLocationId
     * @return
     * @throws AnaliaException
     */

    public TradeResultSet getTradeResultSetByTradeLocationId(BigInteger tradeLocationId) throws AnaliaException {
        return tradeFacadeLocal.getTradeResultSetByTradeLocationId(tradeLocationId);
    }

    /**
     * @param querySearchParam
     * @return
     * @throws AnaliaException
     */

    public List<TradeResultSet> getTrades(String querySearchParam) throws AnaliaException {
        return tradeFacadeLocal.getTrades(querySearchParam);
    }
}
