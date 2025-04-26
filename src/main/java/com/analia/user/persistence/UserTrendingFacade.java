package com.analia.user.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.UserTrending;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;


import java.math.BigInteger;
import java.util.List;

@ApplicationScoped
public class UserTrendingFacade extends JPAPersistenceFacade<UserTrending>  {
    private static final String QUERY_GET_TRENDING_BY_VOUCHER_ID = "getUserTrendingByUserAndVoucherId";
    private static final String QUERY_GET_TRENDING_BY_TRADE_ID = "getUserTrendingByUserAndTradeId";
    private static final String QUERY_GET_TRENDING_BY_STORY_ID = "getUserTrendingByUserAndStoryId";
    private static final String QUERY_GET_USER_TRENDING = "getUserTrending";
    private static final String PARAM_VOUCHER_ID = "voucherId";
    private static final String PARAM_TRADE_ID = "tradeId";
    private static final String PARAM_USER_ID = "userId";
    private static final String PARAM_STORY_ID = "storyId";

    @Inject
    private EntityManager entityManager;

    public UserTrendingFacade() {
        super(UserTrending.class);
    }





    public UserTrending getUserTrendingByUserAndVoucherId(BigInteger userId, BigInteger voucherId) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_GET_TRENDING_BY_VOUCHER_ID, new JpqlParameter(PARAM_VOUCHER_ID, voucherId), new JpqlParameter(PARAM_USER_ID, userId));
    }


    public UserTrending getUserTrendingByUserAndTradeId(BigInteger userId, BigInteger tradeId) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_GET_TRENDING_BY_TRADE_ID, new JpqlParameter(PARAM_TRADE_ID, tradeId), new JpqlParameter(PARAM_USER_ID, userId));
    }


    public UserTrending getUserTrendingByUserAndStoryId(BigInteger userId, BigInteger storyId) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_GET_TRENDING_BY_STORY_ID, new JpqlParameter(PARAM_STORY_ID, storyId), new JpqlParameter(PARAM_USER_ID, userId));
    }


    public List<UserTrending> getUserTrending(BigInteger userId) throws AnaliaException {
        return getListForNamedQuery(QUERY_GET_USER_TRENDING, new JpqlParameter(PARAM_USER_ID, userId));
    }
}
