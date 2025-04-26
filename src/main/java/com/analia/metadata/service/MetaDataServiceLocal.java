package com.analia.metadata.service;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Category;
import com.analia.common.model.Review;
import com.analia.common.model.resultset.ReviewResultSet;


import java.math.BigInteger;
import java.util.List;

public interface MetaDataServiceLocal {

    /**
     * @param tradeId
     * @param voucherId
     * @return
     * @throws AnaliaException
     */
    List<ReviewResultSet> getReviewsByTradeOrVoucherId(BigInteger tradeId, BigInteger voucherId, Integer page, Integer pageSize) throws AnaliaException;

    /**
     * @param tradeId
     * @param voucherId
     * @param scoreGiven
     * @param text
     * @return
     * @throws AnaliaException
     */
    Review saveReview(BigInteger tradeId, BigInteger voucherId, int scoreGiven, String text) throws AnaliaException;

    /**
     * @param tradeId
     * @param voucherId
     * @return
     * @throws AnaliaException
     */
    double getOverallScore(BigInteger tradeId, BigInteger voucherId) throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    Review getReviewByTradeIdOrVoucherId(BigInteger tradeId, BigInteger voucherId) throws AnaliaException;


    /**
     * @param categoryTypeId
     * @return
     * @throws AnaliaException
     */
    List<Category> getCategoriesByCategoryTypeId(BigInteger categoryTypeId) throws AnaliaException;

    /**
     *
     * @param name
     * @return
     * @throws AnaliaException
     */
    Category getCategoryByName(String name)throws AnaliaException;

    /**
     *
     * @param category
     * @return
     * @throws AnaliaException
     */
    Category saveCategory(Category category)throws  AnaliaException;


}
