package com.analia.common.model;

import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "ACCOUNT_USER")
public class AccountUser extends AnaliaEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "vendor_id")
    private BigInteger vendorId;
    @Column(name = "user_id")
    private BigInteger userId;
    @Column(name = "created_by")
    private BigInteger createdBy;
    @Column(name = "created_datetime")
    private Date createdDatetime;
    private boolean disabled;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public AccountUser() {
        super();
    }

    public AccountUser(BigInteger id, BigInteger vendorId, BigInteger userId, BigInteger createdBy, Date createdDatetime, boolean disabled,
                       Date timestamp) {
        super();
        this.id = id;
        this.vendorId = vendorId;
        this.userId = userId;
        this.createdBy = createdBy;
        this.createdDatetime = createdDatetime;
        this.disabled = disabled;
        this.timestamp = timestamp;
    }


    public BigInteger getVendorId() {
        return vendorId;
    }

    public void setVendorId(BigInteger vendorId) {
        this.vendorId = vendorId;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BigInteger createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }
}
