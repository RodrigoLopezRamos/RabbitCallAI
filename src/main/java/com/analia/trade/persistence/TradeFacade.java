package com.analia.trade.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Trade;
import com.analia.common.model.TradeFeatured;
import com.analia.common.model.resultset.TradeResultSet;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.trade.persistence.TradeFacadeLocal;
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
public class TradeFacade extends JPAPersistenceFacade<Trade> implements TradeFacadeLocal {
    public static final String PARAM_CATEGORY_TYPE_ID = "categoryTypeId";
    public static final String PARAM_CATEGORY_ID = "categoryId";
    public static final String PARAM_ZONE_ID = "zoneId";
    public static final String PARAM_TRADE_LOCATION_ID = "tradeLocationId";
    public static final String PARAM_TRADE_ID = "tradeId";
    public static final String PARAM_SEARCH = "search";
    private static final String QUERY_GET_TRADES = "getTrades";
    private static final String QUERY_GET_TRADE = "getTrade";
    private static final String QUERY_GET_TRADE_BY_SEARCH_PARAM = "getTradesBySearchParam";
    private static final String QUERY_TOTAL_TRADE_COUNT_BY_ZONE_ID = "totalTradeCountByZoneId";
    private static final String QUERY_GET_FEATURED_TRADES = "getFeatureTrades";

    @Inject
    private EntityManager entityManager;

    public TradeFacade() {
        super(Trade.class);
    }




    @SuppressWarnings("unchecked")

    public List<TradeResultSet> getTrades(int categoryTypeId, BigInteger categoryId, BigInteger zoneId) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_GET_TRADES);
        query.setParameter(PARAM_CATEGORY_TYPE_ID, BigInteger.valueOf(categoryTypeId));
        query.setParameter(PARAM_CATEGORY_ID, categoryId);
        query.setParameter(PARAM_ZONE_ID, zoneId);


        List<Object[]> tradeResultSet = (List<Object[]>) query.getResultList();
        List<TradeResultSet> result = new ArrayList<>();
        for (Object[] objects : tradeResultSet) {
            result.add(getTradeResultSet(objects));
        }
        return result;

    }

    @SuppressWarnings("unchecked")

    public TradeResultSet getTradeResultSetByTradeLocationId(BigInteger tradeLocationId) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_GET_TRADE);
        query.setParameter(PARAM_TRADE_LOCATION_ID, tradeLocationId);
        List<Object[]> tradeResultSet = (List<Object[]>) query.getResultList();
        return getTradeResultSet(tradeResultSet.size() > 0 ? tradeResultSet.get(0) : null);
    }

    /**
     * @param objects
     * @return
     */
    private TradeResultSet getTradeResultSet(Object[] objects) {
        if (objects == null || objects.length == 0) {
            return new TradeResultSet();
        }
        TradeResultSet trade = new TradeResultSet();
        trade.setTradeId((BigInteger) objects[0]);
        trade.setDescription((String) objects[1]);
        trade.setDirectoryId((BigInteger) objects[2]);
        trade.setDisabled((boolean) objects[3]);
        trade.setName((String) objects[4]);
        trade.setNotAfter((Date) objects[5]);
        trade.setNotBefore((Date) objects[6]);
        trade.setTitle((String) objects[7]);
        trade.setWebsite((String) objects[8]);
        trade.setLocationId((BigInteger) objects[9]);
        trade.setAddress1((String) objects[10]);
        trade.setAddress2((String) objects[11]);
        trade.setCityId((BigInteger) objects[12]);
        trade.setCity((String) objects[13]);
        trade.setProvince((String) objects[14]);
        trade.setCountry((String) objects[15]);
        trade.setPostalOrZipcode((String) objects[16]);
        trade.setLatitude((Double) objects[17]);
        trade.setLongitude((Double) objects[18]);
        trade.setVendorLocationId((BigInteger) objects[19]);
        trade.setCategoryId((BigInteger) objects[20]);
        trade.setStartDate((Date) objects[21]);
        trade.setTradeLocationId((BigInteger) objects[22]);
        trade.setTotalReviews((long) objects[23]);
        return trade;

    }

    @SuppressWarnings("unchecked")

    public List<TradeResultSet> getTrades(String querySearchParam) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_GET_TRADE_BY_SEARCH_PARAM);
        query.setParameter(PARAM_SEARCH, "%" + querySearchParam + "%");
        List<Object[]> tradeResultSet = (List<Object[]>) query.getResultList();
        List<TradeResultSet> result = new ArrayList<>();
        for (Object[] objects : tradeResultSet) {
            result.add(getTradeResultSet(objects));
        }
        return result;
    }


    public int totalTradeCountByZoneId(BigInteger zoneId, BigInteger categoryId, int categoryTypeId) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_TOTAL_TRADE_COUNT_BY_ZONE_ID);
        query.setParameter(PARAM_ZONE_ID, zoneId);
        query.setParameter(PARAM_CATEGORY_TYPE_ID, BigInteger.valueOf(categoryTypeId));
        query.setParameter(PARAM_CATEGORY_ID, categoryId);
        long aLong = (long) query.getSingleResult();
        return (int) aLong;
    }


    public List<TradeFeatured> getTradesFeatured() throws AnaliaException {
        return null;
    }


}
