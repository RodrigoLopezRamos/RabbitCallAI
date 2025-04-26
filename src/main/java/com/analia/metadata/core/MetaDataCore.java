package com.analia.metadata.core;

import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.Category;
import com.analia.common.model.Review;
import com.analia.common.model.User;
import com.analia.common.model.resultset.ReviewResultSet;
import com.analia.metadata.persistence.CategoryFacade;
import com.analia.metadata.persistence.CategoryFacadeLocal;
import com.analia.metadata.persistence.ReviewFacade;
import com.analia.metadata.persistence.ReviewFacadeLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class MetaDataCore implements MetaDataCoreLocal {

    @Inject
    private ReviewFacade reviewFacadeLocal;

    @Inject
    private CategoryFacade categoryFacadeLocal;


    /**
     *
     */

    public List<ReviewResultSet> getReviewsByTradeId(BigInteger tradeId, Integer page, Integer pageSize) throws AnaliaException {
        return reviewFacadeLocal.getReviewsByTradeId(tradeId, page, pageSize);
    }

    /**
     *
     */

    public Review saveReview(Review review) throws AnaliaException {
        if (review == null) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, "Review object must be not null . Baboso!");
        }
        Review reviewFromDatabase = reviewFacadeLocal.find(review.getId());
        if (reviewFromDatabase != null) {
            review.setCreatedBy(reviewFromDatabase.getCreatedBy());
            review.setCreateDatetime(reviewFromDatabase.getCreateDatetime());
        } else {
            review.setCreatedBy(AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class).getId());
            review.setCreateDatetime(new Date());
        }
        reviewFacadeLocal.save(review);
        reviewFacadeLocal.flush();
        return review;
    }


    public Category saveCategory(Category category) throws AnaliaException {
        if (category == null) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, "Category object must be not null . Baboso!");
        }
        categoryFacadeLocal.save(category);
        categoryFacadeLocal.flush();
        return category;
    }


    public Category getCategoryByName(String name) throws AnaliaException {
        return categoryFacadeLocal.getCategoryByName(name);
    }

    /**
     *
     */

    public double getOverallScore(BigInteger tradeId, BigInteger voucherId) throws AnaliaException {
        return reviewFacadeLocal.getOverallScore(tradeId, voucherId);
    }

    /**
     *
     */

    public Review getReviewByTradeIdAndUserId(BigInteger tradeId, BigInteger userId) throws AnaliaException {
        return reviewFacadeLocal.getReviewByTradeIdAndUserId(tradeId, userId);
    }


    public List<Category> getCategoriesByCategoryTypeId(BigInteger categoryTypeId) throws AnaliaException {
        BigInteger vendorId = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.VENDOR_ID, BigInteger.class);
        return categoryFacadeLocal.getCategoriesByCategoryTypeId(vendorId, categoryTypeId);
    }

}
