package com.analia.common.model;

import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the HIDDEN_NOTIFICATION database table.
 */
@Entity
@Table(name = "HIDDEN_NOTIFICATION")
public class HiddenNotification extends AnaliaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_datetime")
    private Date createdDatetime;

    @Column(name = "notificationtype_id")
    private BigInteger notificationtypeId;

    private Timestamp timestamp;

    @Column(name = "user_id")
    private BigInteger userId;

    public HiddenNotification() {
    }


 

    public Date getCreatedDatetime() {
        return this.createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public BigInteger getNotificationtypeId() {
        return this.notificationtypeId;
    }

    public void setNotificationtypeId(BigInteger notificationtypeId) {
        this.notificationtypeId = notificationtypeId;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public BigInteger getUserId() {
        return this.userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

}