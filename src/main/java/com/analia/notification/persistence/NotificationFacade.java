package com.analia.notification.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Notification;
import com.analia.common.model.resultset.NotificationResultSet;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.notification.persistence.NotificationFacadeLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Query;
import jakarta.persistence.QueryHint;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Session Bean implementation class NotificationFacade
 */

@NamedQuery(name = "unReadNotificationCount",
        query = "select COUNT(ibx.id) " +
                "from " +
                "Inbox ibx," +
                "UserGrouping ug," +
                "Notification n " +
                "where  (n.id  = ibx.notificationId)" +
                "and    (ug.id = ibx.usergroupingId and ug.userId = :userId)" +
                "and    (ibx.readDatetime is null)" +
                "and    (n.notificationtypeId not in" +
                "(select hd.notificationtypeId from HiddenNotification hd where hd.notificationtypeId = n.notificationtypeId and hd.userId =:userId))",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))


@NamedQuery(name = "getNotificationTypes",
        query = "select nt " +
                "from NotificationType nt " +
                "where (nt.disabled=0)  ",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))

@NamedQuery(name = "getInboxForUser",
        query = "select ibx " +
                "from Inbox ibx, " +
                "UserGrouping ug " +
                "where (ibx.id=:inboxId) " +
                "and (ug.id = ibx.usergroupingId and ug.userId = :userId)" +
                "order by ibx.timestamp ASC",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))


@NamedQuery(name = "getMaxTimeStampForNotification",
        query = "select n from Notification n where n.timestamp = (SELECT MAX(n.timestamp) FROM  Notification n)",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))


@NamedQuery(name = "getNotificationsToBePushed",
        query = "select n from Notification n where (:date >= n.deliversOn) and (:date < n.expiresOn) ",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))

@NamedQuery(name = "getPendingNotificationsForUser",
        query = "\tselect nv \n" +
                "\t\tfrom NotificationView nv\n" +
                "\t\twhere (nv.userId = :userId) \n" +
                "\t\tand (:date >= nv.deliversOn) \n" +
                "\t\tand (:date < nv.expiresOn) \n" +
                "\t\tand (nv.inboxId is null)\n" +
                "\t\torder by nv.deliversOn DESC ",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))


@NamedQuery(name = "getUserNotifications",
        query = "\t\tselect  ibx.id,\n" +
                "\t\t        ibx.readDatetime,\n" +
                "\t\t        ibx.usergroupingId,\n" +
                "\t\t        n.id,\n" +
                "\t\t\t\tn.notificationtypeId,\n" +
                "\t\t\t\tn.createdDatetime,\n" +
                "\t\t\t\tn.message, \n" +
                "\t\t\t\tn.promoId,\n" +
                "\t\t\t\tn.storyId,\n" +
                "\t\t\t\tnt.name,\n" +
                "\t\t\t\tnt.directoryId\n" +
                "\t\tfrom \n" +
                "\t\t\t Inbox ibx,\n" +
                "\t\t\t UserGrouping ug,\n" +
                "\t\t     Notification n ,\n" +
                "\t\t\t NotificationType nt \n" +
                "\t\twhere\n" +
                "\t\t     ibx.notificationId = n.id \n" +
                "\t    and\t (n.notificationtypeId = nt.id)\n" +
                "\t    and  (ug.id = :userGroupingId)\n" +
                "\t\tand  (ug.id = ibx.usergroupingId)\n" +
                "\t\tand  (ibx.deletedDatetime is null)\n" +
                "\t\torder by n.createdDatetime DESC",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))





@ApplicationScoped
public class NotificationFacade extends JPAPersistenceFacade<Notification> implements NotificationFacadeLocal {
    public static final String PARAM_DATE = "date";
    public static final String PARAM_USER_GROUGPING_ID = "userGroupingId";

    public static final String QUERY_GET_USER_NOTIFICATIONS = "getUserNotifications";
    public static final String QUERY_GET_PENDING_NOTIFICATIONS = "getPendingNotificationsForUser";
    public static final String QUERY_GET_NOTIFICATIONS_TO_BE_PUSHED = "getNotificationsToBePushed";
    public static final String QUERY_GET_MAX_TIME_STAMP_FOR_NOTIFICATION = "getMaxTimeStampForNotification";

    @Inject
    private EntityManager entityManager;

    public NotificationFacade() {
        super(Notification.class);
    }





    @SuppressWarnings("unchecked")

    public List<NotificationResultSet> getNotificationList(BigInteger userGroupingId, int page, int size) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_GET_USER_NOTIFICATIONS);
        query.setParameter(PARAM_USER_GROUGPING_ID, userGroupingId);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<Object[]> notificationList = (List<Object[]>) query.getResultList();
        List<NotificationResultSet> result = new ArrayList<>();
        for (Object[] objects : notificationList) {
            NotificationResultSet rsUserNotification = new NotificationResultSet();
            rsUserNotification.inboxId = (int) objects[0];
            rsUserNotification.readDateTime = (Date) objects[1];
            rsUserNotification.usergroupingId = (int) objects[2];
            rsUserNotification.notificationId = (int) objects[3];
            rsUserNotification.notificationTypeId = (int) objects[4];
            rsUserNotification.createdDateTime = (Date) objects[5];
            rsUserNotification.message = (String) objects[6];
            rsUserNotification.promoId = (Integer) objects[7];
            rsUserNotification.storyId = (Integer) objects[8];
            rsUserNotification.notificationName = (String) objects[9];
            rsUserNotification.notificationTypeDirectoryId = (int) objects[10];
            result.add(rsUserNotification);
        }
        return result;
    }


    /**
     *
     */
    @SuppressWarnings("unchecked")

    public List<Notification> getNotificationsToBePushed(Date date) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_GET_NOTIFICATIONS_TO_BE_PUSHED, Notification.class);
        query.setParameter(PARAM_DATE, date);
        return query.getResultList();
    }

}
