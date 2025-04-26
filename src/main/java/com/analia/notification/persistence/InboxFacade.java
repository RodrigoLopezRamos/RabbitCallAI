package com.analia.notification.persistence;


import com.analia.common.model.Inbox;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import com.analia.notification.persistence.InboxFacadeLocal;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

import java.math.BigInteger;

/**
 * Session Bean implementation class InboxFacade
 */
@ApplicationScoped
public class InboxFacade extends JPAPersistenceFacade<Inbox> implements InboxFacadeLocal {
    public static final String QUERY_GET_INBOX_FOR_USER = "getInboxForUser";
    public static String PARAM_INBOX_ID = "inboxId";
    public static String PARAM_USER_ID = "userId";

    @Inject
    private EntityManager entityManager;

    public InboxFacade() {
        super(Inbox.class);
    }

    /**
     *
     */



    /**
     *
     */

    public Inbox getInboxForUser(BigInteger userId, BigInteger inboxId) {
        return getPersistForNamedQuery(QUERY_GET_INBOX_FOR_USER, new JpqlParameter(PARAM_USER_ID, userId), new JpqlParameter(PARAM_INBOX_ID, inboxId));
    }
}
