package com.analia.user.persistence;

import com.analia.common.model.UserToken;
import com.analia.common.persistence.JPAPersistenceFacade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;


import java.util.List;


@ApplicationScoped
public class UserTokenFacade extends JPAPersistenceFacade<UserToken> {

    @Inject
    private EntityManager entityManager;

    public UserTokenFacade() {
        super(UserToken.class);
    }


    /**
     * @param token
     * @return
     */
    public UserToken getUserByToken(String token) {
        Query query = entityManager.createQuery("select ut from UserToken ut, User us where ut.token =?1 and ut.id = us.id");
        query.setParameter(1, token);
        List list = query.getResultList();
        if (!list.isEmpty()) {
            return (UserToken) list.get(0);
        }
        return null;
    }
}
