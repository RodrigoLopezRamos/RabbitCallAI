package com.analia.trade.service;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.*;
import com.analia.common.model.resultset.TradeResultSet;


import java.math.BigInteger;
import java.util.List;

public interface TradeServiceLocal {
    /**
     * @return
     * @throws AnaliaException
     */
    List<TradeResultSet> getTrades(int categoryTypeId, int page, BigInteger categoryId, int sortTypeId, Integer pageSize, List<Filter> filters) throws AnaliaException;

    /**
     * @param tradeId
     * @return
     * @throws AnaliaException
     */
    List<TradeAdditionalData> getTradeDataByTradeId(BigInteger tradeId) throws AnaliaException;


    /**
     * Gets TradeResultSet by tradeLocationId
     *
     * @param tradeLocationId
     * @return
     * @throws AnaliaException
     */
    TradeResultSet tradeDetail(BigInteger tradeLocationId) throws AnaliaException;

    /**
     * @param tradeId
     * @return
     */
    List<Tag> getTradeTagsByTradeId(BigInteger tradeId) throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    Trade saveTrade(Trade trade) throws AnaliaException;


    /**
     * @return
     * @throws AnaliaException
     */
    List<TradeFeatured> getTradesFeatured() throws AnaliaException;

    /**
     * @param tradeLocation
     * @return
     * @throws AnaliaException
     */
    TradeLocation saveTradeLocation(TradeLocation tradeLocation) throws AnaliaException;

    /**
     * @param tradeCategory
     * @return
     * @throws AnaliaException
     */
    TradeCategory saveTradeCategory(TradeCategory tradeCategory) throws AnaliaException;

    /**
     * @param querySearchParam
     * @return
     * @throws AnaliaException
     */
    List<TradeResultSet> getTrades(String querySearchParam, int page, int pageSize) throws AnaliaException;

    /**
     * @return
     */
    int totalTradeCountByZoneId(BigInteger categoryId, int categoryTypeId) throws AnaliaException;


}
