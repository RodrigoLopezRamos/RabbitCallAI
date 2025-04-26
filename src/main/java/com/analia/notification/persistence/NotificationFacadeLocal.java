package com.analia.notification.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Notification;
import com.analia.common.model.resultset.NotificationResultSet;
import com.analia.common.persistence.PersistenceFacade;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public interface NotificationFacadeLocal extends PersistenceFacade<Notification> {
    /**
     * @return
     */
    List<NotificationResultSet> getNotificationList(BigInteger userGroupingId, int page, int size) throws AnaliaException;

    /**
     * @param date
     * @return
     * @throws AnaliaException
     */
    List<Notification> getNotificationsToBePushed(Date date) throws AnaliaException;


}
