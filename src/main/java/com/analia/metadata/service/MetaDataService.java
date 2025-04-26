package com.analia.metadata.service;

import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.Category;
import com.analia.common.model.Review;
import com.analia.common.model.User;
import com.analia.common.model.resultset.ReviewResultSet;
import com.analia.metadata.core.MetaDataCoreLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class MetaDataService implements MetaDataServiceLocal {

    @Inject
    private MetaDataCoreLocal metaDataCoreLocal;


    public Review saveReview(BigInteger tradeId, BigInteger voucherId, int scoreGiven, String text) throws AnaliaException {
        Review review = new Review();
        review.setDate(AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_DATE_TIME, Date.class));
        review.setSecuenceNumber(0);//TODO fix
        review.setScoreGiven(scoreGiven);
        review.setText(text);
        review.setTradeId(tradeId);
        review.setVoucherId(voucherId);
        return metaDataCoreLocal.saveReview(review);
    }


    public List<ReviewResultSet> getReviewsByTradeOrVoucherId(BigInteger tradeId, BigInteger voucherId, Integer page, Integer pageSize) throws AnaliaException {
        if (tradeId != null) {
            return metaDataCoreLocal.getReviewsByTradeId(tradeId, page, pageSize);
        }
        if (voucherId != null) {
            return null;//metaDataCoreLocal.getRe(tradeId, page, pageSize); //TODO complete for vouchers
        }
        throw new AnaliaException(ExceptionCode.BAD_REQUEST, "Trending entity Id must be not null");
    }

    /**
     *
     */

    public double getOverallScore(BigInteger tradeId, BigInteger voucherId) throws AnaliaException {
        return metaDataCoreLocal.getOverallScore(tradeId, voucherId);
    }

    /**
     *
     */

    public Review getReviewByTradeIdOrVoucherId(BigInteger tradeId, BigInteger voucherId) throws AnaliaException {
        User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        if (tradeId != null) {
            return metaDataCoreLocal.getReviewByTradeIdAndUserId(tradeId, user.getId());
        }
        if (voucherId != null) {
            return null;//metaDataCoreLocal.getRe(tradeId, page, pageSize); //TODO complete for vouchers
        }
        throw new AnaliaException(ExceptionCode.BAD_REQUEST, "Trending entity Id must be not null");
    }


    public List<Category> getCategoriesByCategoryTypeId(BigInteger categoryTypeId) throws AnaliaException {
        return metaDataCoreLocal.getCategoriesByCategoryTypeId(categoryTypeId);
    }


    public Category getCategoryByName(String name) throws AnaliaException {
        return metaDataCoreLocal.getCategoryByName(name);
    }


    @Transactional
    public Category saveCategory(Category category) throws AnaliaException {
        return metaDataCoreLocal.saveCategory(category);
    }

}
