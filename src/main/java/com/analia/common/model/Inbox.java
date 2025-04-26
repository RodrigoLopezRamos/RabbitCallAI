package com.analia.common.model;

import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the INBOX database table.
 */
@Entity
@Table(name = "INBOX")
public class Inbox extends AnaliaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_datetime")
    private Date deletedDatetime;

    @Column(name = "notification_id")
    private BigInteger notificationId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "read_datetime")
    private Date readDatetime;

    private Timestamp timestamp;

    @Column(name = "usergrouping_id")
    private BigInteger usergroupingId;

    public Inbox() {
    }


    public Date getDeletedDatetime() {
        return this.deletedDatetime;
    }

    public void setDeletedDatetime(Date deletedDatetime) {
        this.deletedDatetime = deletedDatetime;
    }

    public BigInteger getNotificationId() {
        return this.notificationId;
    }

    public void setNotificationId(BigInteger notificationId) {
        this.notificationId = notificationId;
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

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

}