package com.analia.trade.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Trade;
import com.analia.common.model.TradeFeatured;
import com.analia.common.model.resultset.TradeResultSet;
import com.analia.common.persistence.PersistenceFacade;


import java.math.BigInteger;
import java.util.List;

public interface TradeFacadeLocal extends PersistenceFacade<Trade> {
    /**
     * @param categoryId
     * @param zoneId
     * @return
     * @throws AnaliaException
     */
    List<TradeResultSet> getTrades(int categoryTypeId, BigInteger categoryId, BigInteger zoneId) throws AnaliaException;


    /**
     * @return
     * @throws AnaliaException
     */
    TradeResultSet getTradeResultSetByTradeLocationId(BigInteger tradeLocationId) throws AnaliaException;


    /**
     * @param querySearchParam
     * @return
     * @throws AnaliaException
     */
    List<TradeResultSet> getTrades(String querySearchParam) throws AnaliaException;


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
