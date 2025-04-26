package com.analia.notification.persistence;

import com.analia.common.model.Inbox;
import com.analia.common.persistence.PersistenceFacade;

import java.math.BigInteger;

public interface InboxFacadeLocal extends PersistenceFacade<Inbox> {
    /**
     * @param userId
     * @return
     */
    Inbox getInboxForUser(BigInteger userId, BigInteger inboxId);
}
