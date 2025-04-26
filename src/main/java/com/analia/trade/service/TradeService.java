package com.analia.trade.service;

import com.analia.common.constants.Constants;
import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.infrastructure.location.GeoLocation;
import com.analia.common.model.*;
import com.analia.common.model.resultset.TradeResultSet;
import com.analia.common.util.ArrayUtils;
import com.analia.common.util.PaginatedArrayList;
import com.analia.filters.core.FilterLogicLocal;
import com.analia.trade.core.TradeCoreLocal;
import com.analia.user.core.UserCore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigInteger;
import java.util.List;

@ApplicationScoped
public class TradeService implements TradeServiceLocal {
    public static final int PAGE_SIZE = 10;

    @Inject
    private TradeCoreLocal tradeCoreLocal;

    @Inject
    private FilterLogicLocal filterLogicLocal;

    @Inject
    private UserCore userCoreLocal;

    /**
     *
     */

    public List<TradeAdditionalData> getTradeDataByTradeId(BigInteger tradeId) throws AnaliaException {
        return tradeCoreLocal.getTradeDataByTradeId(tradeId);
    }

    /**
     *
     */

    public List<Tag> getTradeTagsByTradeId(BigInteger tradeId) throws AnaliaException {
        return tradeCoreLocal.getTradeTagsByTradeId(tradeId);
    }


    public List<TradeResultSet> getTrades(int categoryTypeId, int page, BigInteger categoryId, int sortTypeId, Integer pageSize, List<Filter> filters) throws AnaliaException {
        UserLocation userLocation = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_LOCATION, UserLocation.class);
        User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);

        List<TradeResultSet> trades = tradeCoreLocal.getTrades(categoryTypeId, categoryId, userLocation.getZoneId());

        if (filters != null && !filters.isEmpty()) {
            for (Filter filter : filters) {
                if (filter.getFilterTypeId() != Constants.FILTER_TYPE_ID_TRADE) ;
                {
                    throw new AnaliaException(ExceptionCode.BAD_REQUEST, "Filter is not allowed for this operation");
                }
            }
            trades = filterLogicLocal.doFilter(trades, filters);
        }

        trades = GeoLocation.getDistances(trades, userLocation.getLatitude(), userLocation.getLongitude());
        switch (sortTypeId) {
            case Constants.SORT_TYPE_DATE: // PUBLISHED DATE
                trades = ArrayUtils.sortTradesByStartDate(trades);
                break;
            case Constants.SORT_TYPE_DISTANCE: // GEOLOCATION
                trades = ArrayUtils.sortByDistance(trades);
                break;
            default:
                throw new AnaliaException(ExceptionCode.SERVER_ERROR, "SortType is not defined ! Baboso!");
        }
        PaginatedArrayList<TradeResultSet> paginatedArrayList = new PaginatedArrayList<>(trades, pageSize == null ? PAGE_SIZE : pageSize, page);
        List<TradeResultSet> result = paginatedArrayList.getCurrentPage();

        TradeResultSet[] trResultSets = new TradeResultSet[result.size()];

        this.userCoreLocal.applyUserTrending(user.getId(), result.toArray(trResultSets));

        return result;
    }


    public Trade saveTrade(Trade trade) throws AnaliaException {
        return tradeCoreLocal.saveTrade(trade);
    }


    public List<TradeFeatured> getTradesFeatured() throws AnaliaException {
        return tradeCoreLocal.getTradesFeatured();
    }


    public TradeLocation saveTradeLocation(TradeLocation tradeLocation) throws AnaliaException {
        return tradeCoreLocal.saveTradeLocation(tradeLocation);
    }


    public TradeCategory saveTradeCategory(TradeCategory tradeCategory) throws AnaliaException {
        return tradeCoreLocal.saveTradeCategory(tradeCategory);
    }


    public TradeResultSet tradeDetail(BigInteger tradeLocationId) throws AnaliaException {
        return tradeCoreLocal.getTradeResultSetByTradeLocationId(tradeLocationId);
    }


    public List<TradeResultSet> getTrades(String querySearchParam, int page, int pageSize) throws AnaliaException {
        return tradeCoreLocal.getTrades(querySearchParam);
    }


    public int totalTradeCountByZoneId(BigInteger categoryId, int categoryTypeId) throws AnaliaException {
        UserLocation userLocation = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_LOCATION, UserLocation.class);
        return tradeCoreLocal.totalTradeCountByZoneId(userLocation.getZoneId(), categoryId, categoryTypeId);
    }

}
