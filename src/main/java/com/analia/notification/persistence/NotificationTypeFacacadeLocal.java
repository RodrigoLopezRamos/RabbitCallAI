package com.analia.notification.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.NotificationType;
import com.analia.common.persistence.PersistenceFacade;

import java.util.List;

public interface NotificationTypeFacacadeLocal extends PersistenceFacade<NotificationType> {

    List<NotificationType> getNotificationTypes() throws AnaliaException;
}
