package com.analia.user.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.User;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;


import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class UserFacade extends JPAPersistenceFacade<User> {
    private static final String QUERY_NAME_GET_USER_BY_TOKEN = "getUserBySessionToken";
    private static final String QUERY_NAME_GET_USER_BY_IDENTIFIER_AND_TYPE_ID = "getUserByIdentifierAndTypeIdAndVendorId";
    private static final String QUERY_NAME_GET_USER_BY_CONFIRMATION_CODE = "getUserByConfirmationCode";
    private static final String QUERY_NAME_GET_USERS_WITH_IDS = "getUserWithIds";

    private static final String QUERY_NAME_GET_TAGS_BY_USER_ID = "getTagsByUserId";
    private static final String PARAMETER_TOKEN = "token";
    private static final String PARAMETER_IDENTIFIER = "userIdentifier";
    private static final String PARAMETER_TYPE_ID = "userTypeId";
    private static final String PARAMETER_CONFIRMATION_CODE = "confirmationCode";
    private static final String PARAMETER_IDS = "ids";
    private static final String PARAMETER_USER_ID = "userId";
    private static final String PARAMETER_GENDER = "gender";
    private static final String PARAMETER_VENDOR_ID = "vendorId";


    private static final String QUERY_GET_USER_PROFILES_FOR_MATCHING = "getUserForMatching";


    @Inject
    private EntityManager entityManager;

    public UserFacade() {
        super(User.class);
    }


    public User getUserByToken(String token) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_NAME_GET_USER_BY_TOKEN, new JpqlParameter(PARAMETER_TOKEN, token));
    }


    public User getUserByIdentifier(String identifier, BigInteger vendorId, int userTypeId) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_NAME_GET_USER_BY_IDENTIFIER_AND_TYPE_ID,
                new JpqlParameter(PARAMETER_IDENTIFIER, identifier),
                new JpqlParameter(PARAMETER_TYPE_ID, BigInteger.valueOf(userTypeId)),
                new JpqlParameter(PARAMETER_VENDOR_ID, vendorId));
    }


    public User getUserByConfirmationCode(String emailCode) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_NAME_GET_USER_BY_CONFIRMATION_CODE, new JpqlParameter(PARAMETER_CONFIRMATION_CODE, emailCode));
    }


    public List<User> getUserByIds(BigInteger[] ids) throws AnaliaException {
        return getListForNamedQuery(QUERY_NAME_GET_USERS_WITH_IDS, new JpqlParameter(PARAMETER_IDS, Arrays.asList(ids)));
    }


    public List<User> getUserProfileMatchingTags(BigInteger userId, String gender) throws AnaliaException {
        return getListForNamedQuery(QUERY_GET_USER_PROFILES_FOR_MATCHING, new JpqlParameter(PARAMETER_USER_ID, userId), new JpqlParameter(PARAMETER_GENDER, gender));
    }


    public User getUserByExternalToken(String token) throws AnaliaException {
        Query query = entityManager.createQuery("select u from UserToken ut , User u where ut.token =?1 and u.id = ut.id");
        query.setParameter(1, token);
        List list = query.getResultList();
        if (!list.isEmpty()) {
            return (User) list.get(0);
        }
        return null;
    }

}
