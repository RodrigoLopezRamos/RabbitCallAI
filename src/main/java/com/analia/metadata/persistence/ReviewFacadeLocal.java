package com.analia.metadata.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Review;
import com.analia.common.model.resultset.ReviewResultSet;
import com.analia.common.persistence.PersistenceFacade;

import java.math.BigInteger;
import java.util.List;

public interface ReviewFacadeLocal extends PersistenceFacade<Review> {

    List<ReviewResultSet> getReviewsByTradeId(BigInteger tradeId, Integer page, Integer pageSize) throws AnaliaException;


    List<Review> getReviewsByVoucherId(BigInteger voucherId, Integer page, Integer pageSize) throws AnaliaException;


    double getOverallScore(BigInteger tradeId, BigInteger voucherId) throws AnaliaException;


    Review getReviewByTradeIdAndUserId(BigInteger tradeId, BigInteger userId) throws AnaliaException;


    Review getReviewByVoucherIdAndUserId(BigInteger voucherId, BigInteger userId) throws AnaliaException;


}
