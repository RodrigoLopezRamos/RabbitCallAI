package com.analia.purchase.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.Source;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import com.analia.purchase.persistence.SourceFacadeLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.math.BigInteger;
import java.util.List;

@ApplicationScoped
public class SourceFacade extends JPAPersistenceFacade<Source> implements SourceFacadeLocal {
    private static final String PARAM_USER_ID = "userId";
    private static final String PARAM_SOURCE_ID = "sourceId";
    private static final String QUERY_GET_SOURCES_BY_USER_ID = "getSourcesByUserId";
    private static final String QUERY_GET_SOURCE_BY_ID_WITH_USER_ID = "getSourceByIdWithUserId";
    private static final String QUERY_GET_DEFAULT_SOURCE_BY_USER_ID = "getDefaultSourcesByIdAndUserId";
    private static final String QUERY_SET_USER_DEFAULT_SOURCE = "setDefaultUserSource";
    private static final String QUERY_SET_ALL_USER_SOURCES_UNDEFAULT = "setAllUserSourceToBeUnDefault";

    @Inject
    private EntityManager entityManager;

    /**
     * Default constructor.
     */
    public SourceFacade() {
        super(Source.class);
    }




    /**
     *
     */

    public List<Source> getSourcesByUserId(BigInteger userId) throws AnaliaException {
        JpqlParameter jpqlParameterUserId = new JpqlParameter(PARAM_USER_ID, userId);
        return getListForNamedQuery(QUERY_GET_SOURCES_BY_USER_ID, jpqlParameterUserId);
    }

    /**
     *
     */

    public boolean setDefaultSource(BigInteger userId, BigInteger sourceId) throws AnaliaException {
        Query query = getEntityManager().createNamedQuery(QUERY_SET_USER_DEFAULT_SOURCE);
        query.setParameter(PARAM_SOURCE_ID, sourceId);
        query.setParameter(PARAM_USER_ID, userId);
        return (query.executeUpdate() > 0);
    }

    /**
     *
     */

    public Source getSourceByIdAndUserId(BigInteger sourceId, BigInteger userId) throws AnaliaException {
        JpqlParameter jpqlParameterUserId = new JpqlParameter(PARAM_USER_ID, userId);
        JpqlParameter jpqlParameterSourceId = new JpqlParameter(PARAM_SOURCE_ID, sourceId);
        return getPersistForNamedQuery(QUERY_GET_SOURCE_BY_ID_WITH_USER_ID, jpqlParameterUserId, jpqlParameterSourceId);
    }

    /**
     *
     */

    public boolean setAllUserSourceToBeUnDefault(BigInteger userId) throws AnaliaException {
        Query query = getEntityManager().createNamedQuery(QUERY_SET_ALL_USER_SOURCES_UNDEFAULT);
        query.setParameter(PARAM_USER_ID, userId);
        return (query.executeUpdate() > 0);
    }

    /**
     *
     */

    @SuppressWarnings("unchecked")
    public Source getDefaultSourceByUserId(BigInteger userId) throws AnaliaException {
        Query query = getEntityManager().createNamedQuery(QUERY_GET_DEFAULT_SOURCE_BY_USER_ID);
        query.setParameter(PARAM_USER_ID, userId);
        List<Source> cards = query.getResultList();
        if (cards.size() > 1) {
            throw new AnaliaException(ExceptionCode.PAYMENT_NOT_AUTHORIZED, "User has more than one default card!");
        }
        if (!cards.isEmpty()) {
            return cards.get(0);
        }
        return null;
    }

}
