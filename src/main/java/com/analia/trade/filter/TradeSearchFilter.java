package com.analia.trade.filter;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Filter;
import com.analia.common.model.resultset.TradeResultSet;
import com.analia.filters.handler.FilterHandler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;


import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
@Named("TradeSearchFilter")
public class TradeSearchFilter implements FilterHandler<TradeResultSet> {
    /**
     *
     */

    public List<TradeResultSet> doFilter(List<TradeResultSet> list, Filter filter) throws AnaliaException {
        String search = (String) filter.getFilterAttributes().get(0).getValue();
        List<TradeResultSet> result = new ArrayList<>();

        for (TradeResultSet tradeResultSet : list) {
            if (tradeResultSet.getName().contains(search.toLowerCase()) || tradeResultSet.getAddress1().contains(search.toLowerCase())) {
                result.add(tradeResultSet);
            }
        }
        return result;
    }

}
