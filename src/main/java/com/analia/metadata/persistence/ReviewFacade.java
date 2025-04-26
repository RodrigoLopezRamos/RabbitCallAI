package com.analia.metadata.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.Review;
import com.analia.common.model.resultset.ReviewResultSet;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@NamedQuery(name = "getReviewByVoucherIdAndUserId",
        query = "select r from Review r \n" +
                "where (r.voucherId =:voucherId)\n" +
                "and   (r.createdBy =:userId)\n" +
                "order by r.createDatetime asc",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))

@NamedQuery(name = "getReviewByTradeIdAndUserId",
        query = "select r from Review r\n" +
                "where (r.tradeId   =:tradeId)\n" +
                "and   (r.createdBy =:userId)\n" +
                "order by r.createDatetime asc",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))


@NamedQuery(name = "getReviewsByVoucherId",
        query = "select r from Review r\n" +
                "where (r.voucherId =:voucherId)\n" +
                "order by r.createDatetime asc",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))


@NamedQuery(name = "getReviewsByTradeId",
        query = "select r.id,\n" +
                "\t\t\t       r.tradeId,\n" +
                "\t\t\t       r.secuenceNumber,\n" +
                "\t\t\t       r.date,\n" +
                "\t\t\t       r.scoreGiven,\n" +
                "\t\t\t       r.text,\n" +
                "\t\t\t       u.id as userId,\n" +
                "\t\t\t       p.name\n" +
                "\t\t\tfrom Review r,\n" +
                "\t\t\t\t  User u,\n" +
                "\t\t\t\t  Persona p\n" +
                "\t\t\twhere (r.tradeId =:tradeId)\n" +
                "\t\t\tand (u.persona=p)\n" +
                "\t\t\tand (r.createdBy = u.id)\n" +
                "\t\t\torder by r.createDatetime asc",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))





@ApplicationScoped
public class ReviewFacade extends JPAPersistenceFacade<Review> implements ReviewFacadeLocal {
    private final static int PAGE_DEFAULT_SIZE = 10;
    private static final String QUERY_REVIEW_BY_TRADE_ID_AND_USER_ID = "getReviewByTradeIdAndUserId";
    private static final String QUERY_REVIEW_BY_VOUCHER_ID_AND_USER_ID = "getReviewByTradeIdAndUserId";
    private static final String PARAM_TRADE_ID = "tradeId";
    private static final String PARAM_VOUCHER_ID = "voucherId";
    private static final String PARAM_USER_ID = "userId";

    @Inject
    private EntityManager entityManager;

    public ReviewFacade() {
        super(Review.class);
    }





    /**
     *
     */

    public double getOverallScore(BigInteger tradeId, BigInteger voucherId) throws AnaliaException {
        if (tradeId == null && voucherId == null) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, " Not valid arguments to determine OverrallScore for review .. Baboso!");
        }
        Query query = null;
        if (tradeId != null) {
            query = entityManager.createQuery("select avg(r.scoreGiven) from Review r where tradeId =:tradeId");
            query.setParameter(PARAM_TRADE_ID, tradeId);
        }
        if (voucherId != null) {
            query = entityManager.createQuery("select avg(r.scoreGiven) from Review r where voucherId =:voucherId");
            query.setParameter(PARAM_VOUCHER_ID, voucherId);
        }
        Double score = (Double) query.getSingleResult();
        return score == null ? 0 : score;
    }

    /**
     *
     */
    @SuppressWarnings("unchecked")

    public List<ReviewResultSet> getReviewsByTradeId(BigInteger tradeId, Integer page, Integer pageSize) throws AnaliaException {
        String QUERY_GET_REVIEWS_BY_TRADE_ID = "getReviewsByTradeId";
        Query query = entityManager.createNamedQuery(QUERY_GET_REVIEWS_BY_TRADE_ID);
        page = page == null ? 0 : page;
        pageSize = pageSize == null ? PAGE_DEFAULT_SIZE : pageSize;
        query.setFirstResult(page * pageSize);
        query.setMaxResults(pageSize);
        query.setParameter(PARAM_TRADE_ID, tradeId);
        List<Object[]> reviewList = (List<Object[]>) query.getResultList();
        List<ReviewResultSet> result = new ArrayList<>();

        for (Object[] objects : reviewList) {
            ReviewResultSet reviewResultSet = new ReviewResultSet();
            reviewResultSet.id = (int) objects[0];
            reviewResultSet.tradeId = (int) objects[1];
            reviewResultSet.sequenceNumber = (int) objects[2];
            reviewResultSet.date = (Date) objects[3];
            reviewResultSet.scoreGiven = (int) objects[4];
            reviewResultSet.text = (String) objects[5];
            reviewResultSet.userid = (int) objects[6];
            reviewResultSet.name = (String) objects[7];
            result.add(reviewResultSet);
        }
        return result;
    }

    /**
     *
     */
    @SuppressWarnings("unchecked")

    public List<Review> getReviewsByVoucherId(BigInteger voucherId, Integer page, Integer pageSize) throws AnaliaException {
        String QUERY_GET_REVIEWS_BY_VOUCHER_ID = "getReviewsByVoucherId";
        Query query = entityManager.createNamedQuery(QUERY_GET_REVIEWS_BY_VOUCHER_ID);
        page = page == null ? 0 : page;
        pageSize = pageSize == null ? PAGE_DEFAULT_SIZE : pageSize;
        query.setFirstResult(page * pageSize);
        query.setMaxResults(pageSize);
        query.setParameter(PARAM_VOUCHER_ID, voucherId);
        return query.getResultList();
    }

    /**
     *
     */

    public Review getReviewByTradeIdAndUserId(BigInteger tradeId, BigInteger userId) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_REVIEW_BY_TRADE_ID_AND_USER_ID, new JpqlParameter(PARAM_TRADE_ID, tradeId), new JpqlParameter(PARAM_USER_ID, userId));
    }


    public Review getReviewByVoucherIdAndUserId(BigInteger voucherId, BigInteger userId) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_REVIEW_BY_VOUCHER_ID_AND_USER_ID, new JpqlParameter(PARAM_VOUCHER_ID, voucherId), new JpqlParameter(PARAM_USER_ID, userId));
    }

}
