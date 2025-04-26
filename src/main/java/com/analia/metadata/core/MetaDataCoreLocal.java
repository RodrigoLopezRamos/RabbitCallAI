package com.analia.metadata.core;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Category;
import com.analia.common.model.Review;
import com.analia.common.model.resultset.ReviewResultSet;


import java.math.BigInteger;
import java.util.List;

public interface MetaDataCoreLocal {

    List<ReviewResultSet> getReviewsByTradeId(BigInteger tradeId, Integer page, Integer pageSize) throws AnaliaException;

    Review saveReview(Review review) throws AnaliaException;

    double getOverallScore(BigInteger tradeId, BigInteger voucherId) throws AnaliaException;

    Review getReviewByTradeIdAndUserId(BigInteger tradeId, BigInteger userId) throws AnaliaException;

    List<Category> getCategoriesByCategoryTypeId(BigInteger categoryTypeId) throws AnaliaException;

    Category saveCategory(Category category) throws AnaliaException ;

    Category getCategoryByName(String name)throws AnaliaException;

}
