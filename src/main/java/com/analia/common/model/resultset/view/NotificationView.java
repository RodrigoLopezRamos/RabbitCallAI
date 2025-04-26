package com.analia.common.model.resultset.view;

import com.analia.common.model.AnaliaEntity;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "NOTIFICATION_VIEW")
public class NotificationView extends AnaliaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "approved_by")
    private BigInteger approvedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "approved_datetime")
    private Date approvedDatetime;

    @Column(name = "created_by")
    private int createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_datetime")
    private Date createdDatetime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_datetime")
    private Date deletedDatetime;

    @Temporal(TemporalType.DATE)
    @Column(name = "delivers_on")
    private Date deliversOn;

    @Temporal(TemporalType.DATE)
    @Column(name = "expires_on")
    private Date expiresOn;

    @Column(name = "grouping_id")
    private Integer groupingId;

    @Column(name = "hiddennotification_id")
    private Integer hiddenNotificationId;

    @Column(name = "inbox_id")
    private Integer inboxId;

    private String message;

    @Id
    @Column(name = "notification_id")
    private int notificationId;

    @Column(name = "notificationtype_disabled")
    private byte notificationtypeDisabled;

    @Column(name = "notificationtype_gallery_id")
    private int notificationtypeGalleryId;

    @Column(name = "notificationtype_id")
    private int notificationtypeId;

    @Column(name = "notificationtype_name")
    private String notificationtypeName;

    @Column(name = "voucher_id")
    private Integer voucherId;

    @Column(name = "promo_id")
    private Integer promoId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "read_datetime")
    private Date readDatetime;

    private Timestamp timestamp;


    @Column(name = "usergrouping_id")
    private BigInteger usergroupingId;

    @Column(name = "tradecategory_id")
    private Integer tradecategoryId;

    @Column(name = "user_id")
    private int userId;

    public NotificationView() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDatetime() {
        return this.createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Date getDeletedDatetime() {
        return this.deletedDatetime;
    }

    public void setDeletedDatetime(Date deletedDatetime) {
        this.deletedDatetime = deletedDatetime;
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

    public Integer getGroupingId() {
        return this.groupingId;
    }

    public void setGroupingId(Integer groupingId) {
        this.groupingId = groupingId;
    }


    public Integer getInboxId() {
        return this.inboxId;
    }

    public void setInboxId(Integer inboxId) {
        this.inboxId = inboxId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNotificationId() {
        return this.notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public byte getNotificationtypeDisabled() {
        return this.notificationtypeDisabled;
    }

    public void setNotificationtypeDisabled(byte notificationtypeDisabled) {
        this.notificationtypeDisabled = notificationtypeDisabled;
    }

    public int getNotificationtypeGalleryId() {
        return this.notificationtypeGalleryId;
    }

    public void setNotificationtypeGalleryId(int notificationtypeGalleryId) {
        this.notificationtypeGalleryId = notificationtypeGalleryId;
    }

    public int getNotificationtypeId() {
        return this.notificationtypeId;
    }

    public void setNotificationtypeId(int notificationtypeId) {
        this.notificationtypeId = notificationtypeId;
    }

    public String getNotificationtypeName() {
        return this.notificationtypeName;
    }

    public void setNotificationtypeName(String notificationtypeName) {
        this.notificationtypeName = notificationtypeName;
    }


    public Integer getPromoId() {
        return this.promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public Date getReadDatetime() {
        return this.readDatetime;
    }

    public void setReadDatetime(Date readDatetime) {
        this.readDatetime = readDatetime;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public BigInteger getUsergroupingId() {
        return this.usergroupingId;
    }

    public void setUsergroupingId(BigInteger usergroupingId) {
        this.usergroupingId = usergroupingId;
    }


    public Integer getHiddenNotificationId() {
        return this.hiddenNotificationId;
    }

    public void setHiddenNotificationId(Integer hiddenNotificationId) {
        this.hiddenNotificationId = hiddenNotificationId;
    }


    public BigInteger getId() {
        return usergroupingId;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public Integer getTradecategoryId() {
        return tradecategoryId;
    }

    public void setTradecategoryId(Integer tradecategoryId) {
        this.tradecategoryId = tradecategoryId;
    }


}