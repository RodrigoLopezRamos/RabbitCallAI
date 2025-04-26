package com.analia.user.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.UserProfile;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class UserProfileFacade extends JPAPersistenceFacade<UserProfile>  {
    private static final String QUERY_GET_USER_PROFILE = "getUserProfile";
    private static final String QUERY_GET_USER_PROFILE_WITH_PREFIX = "getUserProfileWithPrefix";
    private static final String QUERY_DELETE_USER_PROFILE = "deleteUserProfile";


    private static final String QUERY_GET_USER_PROFILE_BY_KEY_AND_USER_ID = "getUserProfileByKeyAndUserId";

    private static final String QUERY_GET_USER_PROFILE_BY_TAGS = "getUserProfileByTags";

    private static final String QUERY_GET_USER_PROFILE_FOR_POSSIBLE_MATCHING_USERS = "getUserProfileForPossibleMatchingUsers";


    private static final String PARAM_USER_PROFILE_KEY = "key";
    private static final String PARAM_USER_ID = "userId";
    private static final String PARAM_USER_PROFILE_ID = "userProfileId";
    private static final String PARAM_PREFIX = "prefix";
    private static final String PARAM_TAGS = "tags";

    private static final String PARAM_USER_IDS = "ids";


    @Inject
    private EntityManager entityManager;

    public UserProfileFacade() {
        super(UserProfile.class);
    }



    public UserProfile getUserProfileByKeyAndUserId(String key, BigInteger userId) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_GET_USER_PROFILE_BY_KEY_AND_USER_ID, new JpqlParameter(PARAM_USER_PROFILE_KEY, key), new JpqlParameter(PARAM_USER_ID, userId));
    }






    public List<UserProfile> getUserProfile(BigInteger userId) throws AnaliaException {
        return getListForNamedQuery(QUERY_GET_USER_PROFILE, new JpqlParameter(PARAM_USER_ID, userId));
    }


    public List<UserProfile> getUserProfile(BigInteger userId, String prefix) throws AnaliaException {

        return getListForNamedQuery(QUERY_GET_USER_PROFILE_WITH_PREFIX, new JpqlParameter(PARAM_USER_ID, userId), new JpqlParameter(PARAM_PREFIX, "%" + prefix + "%"));
    }


    public List<UserProfile> getUserProfileForPossibleMatchingUsers(List<BigInteger> userIds) throws AnaliaException {
        try {
            return getListForNamedQuery(QUERY_GET_USER_PROFILE_FOR_POSSIBLE_MATCHING_USERS, new JpqlParameter(PARAM_USER_IDS, userIds));
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // TODO I AM TIRED FUCK NEED TO LAUNCH !
        }
    }


    public List<UserProfile> getUserProfileByTags(String[] tags) throws AnaliaException {
        return getListForNamedQuery(QUERY_GET_USER_PROFILE_BY_TAGS, new JpqlParameter(PARAM_TAGS, Arrays.asList(tags)));

    }



    public void deleteUserProfile(BigInteger userProfileId) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_DELETE_USER_PROFILE);
        query.setParameter(PARAM_USER_PROFILE_ID, userProfileId);
        query.executeUpdate();

    }

}
