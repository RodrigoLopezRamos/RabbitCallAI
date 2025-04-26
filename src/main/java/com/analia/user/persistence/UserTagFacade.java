package com.analia.user.persistence;

import com.analia.common.model.UserTag;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;


import java.math.BigInteger;
import java.util.List;

@ApplicationScoped
public class UserTagFacade extends JPAPersistenceFacade<UserTag> {

    private static final String GET_USER_TAG = "getUserTag";
    private static final String USER_ID = "userId";
    private static final String TAG_ID = "tagId";

    @Inject
    private EntityManager entityManager;

    public UserTagFacade() {
        super(UserTag.class);
    }





    public UserTag getUserTag(BigInteger userId, BigInteger tagId) {
        List<UserTag> userTags = getListForNamedQuery(GET_USER_TAG, new JpqlParameter(USER_ID, userId), new JpqlParameter(TAG_ID, tagId));
        if (userTags.isEmpty()) {
            return null;
        }
        return userTags.get(0);
    }

}
