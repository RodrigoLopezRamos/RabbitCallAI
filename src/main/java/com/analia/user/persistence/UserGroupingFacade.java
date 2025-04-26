package com.analia.user.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.User;
import com.analia.common.model.UserGrouping;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

import java.math.BigInteger;
import java.util.List;

/**
 * Session Bean implementation class UserGroupingFacade
 */
@ApplicationScoped
public class UserGroupingFacade extends JPAPersistenceFacade<UserGrouping> {
    private static final String QUERY_GET_USERS_GROPING_BY_GROUP_ID_AND_USER_ID = "getUsersGropingByGroupIdAndUserId";
    private static final String QUERY_GET_USER_GROPING_BY_GROUP_ID_AND_USER_ID = "getUserGropingByGroupIdAndUserId";
    private static final String QUERY_REMOVE_USER_FROM_GROUPING = "removeUserFromGrouping";
    private static final String QUERY_GET_USER_PENDINGS_FOR_APPROVAL_IN_GROUP = "getUserPendingsForApprovalInGroup";

    private static final String PARAM_GROUPING_ID = "groupingId";
    private static final String PARAM_USER_GROUPING_ID = "userGroupingId";
    private static final String PARAM_GROUPING_NAME = "groupName";


    private static final String PARAM_USER_ID = "userId";


    @Inject
    private EntityManager entityManager;

    public UserGroupingFacade() {
        super(UserGrouping.class);
    }




    /**
     *
     */

    public UserGrouping getUserGropingByGroupIdAndUserId(BigInteger groupId, BigInteger userId) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_GET_USER_GROPING_BY_GROUP_ID_AND_USER_ID, new JpqlParameter(PARAM_GROUPING_ID, groupId), new JpqlParameter(PARAM_USER_ID, userId));
    }

    /**
     *
     */

    public void removeUserFromGrouping(BigInteger groupingId, BigInteger userId) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_REMOVE_USER_FROM_GROUPING);
        query.setParameter(PARAM_GROUPING_ID, groupingId);
        query.setParameter(PARAM_USER_ID, userId);
        query.executeUpdate();
    }


    @SuppressWarnings("unchecked")

    public List<User> getUserPendingsForApprovalInGroup(BigInteger userId, String groupName) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_GET_USER_PENDINGS_FOR_APPROVAL_IN_GROUP);
        query.setParameter(PARAM_GROUPING_NAME, groupName);
        query.setParameter(PARAM_USER_ID, userId);
        return query.getResultList();
    }

}
