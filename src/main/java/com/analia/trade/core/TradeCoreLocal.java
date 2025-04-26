package com.analia.trade.core;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.*;
import com.analia.common.model.resultset.TradeResultSet;


import java.math.BigInteger;
import java.util.List;

public interface TradeCoreLocal {


    /**
     * @param tradeLocationId
     * @return
     * @throws AnaliaException
     */
    TradeResultSet getTradeResultSetByTradeLocationId(BigInteger tradeLocationId) throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    List<TradeResultSet> getTrades(int categoryTypeId, BigInteger categoryId, BigInteger zoneId) throws AnaliaException;

    /**
     * @param querySearchParam
     * @return
     * @throws AnaliaException
     */
    List<TradeResultSet> getTrades(String querySearchParam) throws AnaliaException;

    /**
     * @param tradeId
     * @return
     * @throws AnaliaException
     */
    List<TradeAdditionalData> getTradeDataByTradeId(BigInteger tradeId) throws AnaliaException;

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
     * @param zoneId
     * @return
     * @throws AnaliaException
     */
    int totalTradeCountByZoneId(BigInteger zoneId, BigInteger categoryId, int categoryTypeId) throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    List<TradeFeatured> getTradesFeatured() throws AnaliaException;

}
