package com.analia.trade.filter;

import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Filter;
import com.analia.common.model.User;
import com.analia.common.model.UserTrending;
import com.analia.common.model.resultset.TradeResultSet;
import com.analia.filters.handler.FilterHandler;
import com.analia.user.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;


import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
@Named("TradeLovedFilter")
public class TradeLovedFilter implements FilterHandler<TradeResultSet> {
    @Inject
    private UserService userServiceLocal;

    /**
     *
     */

    public List<TradeResultSet> doFilter(List<TradeResultSet> list, Filter filter) throws AnaliaException {
        List<TradeResultSet> result = new ArrayList<>();
        User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        List<UserTrending> userTrendings = userServiceLocal.getUsersTrending(user.getId());
        for (UserTrending userTrending : userTrendings) {
            for (TradeResultSet tradeResultSet : list) {
                if (userTrending.getTradeId() != null && userTrending.getTradeId() == tradeResultSet.getId() && userTrending.isFavourited()) {
                    result.add(tradeResultSet);
                }
            }
        }
        return result;
    }

}
