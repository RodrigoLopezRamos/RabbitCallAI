package com.analia.common.model;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the SHARING database table.
 */
@Entity
@Table(name = "SHARING")
public class Sharing extends AnaliaEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "created_by")
    private BigInteger createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_datetime")
    private Date createdDatetime;

    @Column(name = "device_id")
    private BigInteger deviceId;

    @Column(name = "entity_id")
    private BigInteger entityId;

    @Column(name = "entity_type")
    private BigInteger entityType;

    @Column(name = "sharing_details")
    private String sharingDetails;

    @Column(name = "sharing_type")
    private String sharingType;

    private Timestamp timestamp;

    @Column(name = "use_count")
    private BigInteger useCount;

    @Column(name = "user_agent")
    private String userAgent;

    public Sharing() {
    }


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
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

    public BigInteger getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(BigInteger deviceId) {
        this.deviceId = deviceId;
    }

    public BigInteger getEntityId() {
        return this.entityId;
    }

    public void setEntityId(BigInteger entityId) {
        this.entityId = entityId;
    }

    public BigInteger getEntityType() {
        return this.entityType;
    }

    public void setEntityType(BigInteger entityType) {
        this.entityType = entityType;
    }

    public String getSharingDetails() {
        return this.sharingDetails;
    }

    public void setSharingDetails(String sharingDetails) {
        this.sharingDetails = sharingDetails;
    }

    public String getSharingType() {
        return this.sharingType;
    }

    public void setSharingType(String sharingType) {
        this.sharingType = sharingType;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public BigInteger getUseCount() {
        return this.useCount;
    }

    public void setUseCount(BigInteger useCount) {
        this.useCount = useCount;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

}