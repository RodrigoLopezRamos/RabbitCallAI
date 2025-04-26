package com.analia.notification.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.resultset.view.NotificationView;
import com.analia.common.persistence.PersistenceFacade;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public interface NotificationViewFacadeLocal extends PersistenceFacade<NotificationView> {

    int unreadNotificationCount(BigInteger userId) throws AnaliaException;

    List<NotificationView> getNotificationsToBeDelivered(BigInteger userId, Date date) throws AnaliaException;

}
