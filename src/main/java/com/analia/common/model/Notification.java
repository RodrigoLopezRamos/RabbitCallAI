package com.analia.common.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

@NamedQuery( name = "getUserNotifications", query = "select  ibx.id, ibx.readDatetime, ibx.usergroupingId, n.id, n.notificationtypeId, n.createdDatetime, n.message,  n.promoId, n.storyId, nt.name, nt.directoryId from  Inbox ibx, UserGrouping ug, Notification n , NotificationType nt  where ibx.notificationId = n.id  and	 (n.notificationtypeId = nt.id) and  (ug.id = :userGroupingId) and  (ug.id = ibx.usergroupingId) and  (ibx.deletedDatetime is null) order by n.createdDatetime DESC")
@NamedQuery( name = "getPendingNotificationsForUser", query = "select nv  from NotificationView nv where (nv.userId = :userId)  and (:date >= nv.deliversOn)  and (:date < nv.expiresOn)  and (nv.inboxId is null) order by nv.deliversOn DESC")
@NamedQuery( name = "getNotificationsToBePushed", query = "select n  from Notification n where (:date >= n.deliversOn)  and (:date < n.expiresOn)")
@NamedQuery( name = "getMaxTimeStampForNotification", query = "select n from Notification n where n.timestamp = (SELECT MAX(n.timestamp) FROM  Notification n)")
@NamedQuery( name = "getInboxForUser", query = "select ibx  from Inbox ibx,  UserGrouping ug  where (ibx.id=:inboxId)   and (ug.id = ibx.usergroupingId and ug.userId = :userId) order by ibx.timestamp ASC")
@NamedQuery( name = "getNotificationTypes", query = "select nt from NotificationType nt where (nt.disabled=0)")
@NamedQuery( name = "unReadNotificationCount", query = "select COUNT(ibx.id)  from  Inbox ibx, UserGrouping ug, 	 Notification n  where  (n.id  = ibx.notificationId) and    (ug.id = ibx.usergroupingId and ug.userId = :userId)    and    (ibx.readDatetime is null) and    (n.notificationtypeId not in  (select hd.notificationtypeId from HiddenNotification hd where hd.notificationtypeId = n.notificationtypeId and hd.userId =:userId))")

/**
 * The persistent class for the NOTIFICATION database table.
 */
@Entity
@Table(name = "NOTIFICATION")
public class Notification extends AnaliaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;


    @Column(name = "approved_by")
    private BigInteger approvedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "approved_datetime")
    private Date approvedDatetime;

    @Column(name = "created_by")
    private BigInteger createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_datetime")
    private Date createdDatetime;

    @Temporal(TemporalType.DATE)
    @Column(name = "delivers_on")
    private Date deliversOn;

    @Temporal(TemporalType.DATE)
    @Column(name = "expires_on")
    private Date expiresOn;

    @Lob
    private String message;

    @Column(name = "notification_type_id")
    private BigInteger notificationtypeId;

    @Column(name = "grouping_id")
    private BigInteger groupingId;

    @Column(name = "story_id")
    private BigInteger storyId;

    @Column(name = "voucher_id")
    private BigInteger voucherId;

    @Column(name = "promo_id")
    private BigInteger promoId;

    private Timestamp timestamp;

    @Column(name = "tradecategory_id")
    private BigInteger tradecategoryId;

    public Notification() {
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }


 

    public BigInteger getApprovedBy() {
        return this.approvedBy;
    }

    public void setApprovedBy(BigInteger approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovedDatetime() {
        return this.approvedDatetime;
    }

    public void setApprovedDatetime(Date approvedDatetime) {
        this.approvedDatetime = approvedDatetime;
    }

    public BigInteger getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(BigInteger createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDatetime() {
        return this.createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Date getDeliversOn() {
        return this.deliversOn;
    }

    public void setDeliversOn(Date deliversOn) {
        this.deliversOn = deliversOn;
    }

    public Date getExpiresOn() {
        return this.expiresOn;
    }

    public void setExpiresOn(Date expiresOn) {
        this.expiresOn = expiresOn;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BigInteger getNotificationtypeId() {
        return this.notificationtypeId;
    }

    public void setNotificationtypeId(BigInteger notificationtypeId) {
        this.notificationtypeId = notificationtypeId;
    }

    public BigInteger getGroupingId() {
        return this.groupingId;
    }

    public void setGroupingId(BigInteger groupingId) {
        this.groupingId = groupingId;
    }

    public BigInteger getPromoId() {
        return this.promoId;
    }

    public void setPromoId(BigInteger promoId) {
        this.promoId = promoId;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    //   public Date getPushedOn()
//   {
//      return this.pushedOn;
//   }
//
//   public void setPushedOn(Date pushedOn)
//   {
//      this.pushedOn = pushedOn;
//   }
//
    public BigInteger getStoryId() {
        return storyId;
    }

    public void setStoryId(BigInteger storyId) {
        this.storyId = storyId;
    }

    public BigInteger getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(BigInteger voucherId) {
        this.voucherId = voucherId;
    }

    public BigInteger getTradecategoryId() {
        return tradecategoryId;
    }

    public void setTradecategoryId(BigInteger tradecategoryId) {
        this.tradecategoryId = tradecategoryId;
    }



    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }
}