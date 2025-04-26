package com.analia.common.model;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "VALID_LOCATION")
public class ValidLocation extends AnaliaEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    @Column(name = "accountuser_id")
    private BigInteger accountUserId;

    @Column(name = "vendorlocation_id")
    private BigInteger vendorLocationId;

    @Column(name = "default_location")
    private boolean defaultLocation;

    @Column(name = "created_by")
    private BigInteger createdBy;

    @Column(name = "created_datetime")
    private Date createdDatetime;

    @Column(name = "timestamp")
    private Date timestamp;


   

 

    public BigInteger getAccountUserId() {
        return accountUserId;
    }

    public void setAccountUserId(BigInteger accountUserId) {
        this.accountUserId = accountUserId;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public BigInteger getVendorLocationId() {
        return vendorLocationId;
    }

    public void setVendorLocationId(BigInteger vendorLocationId) {
        this.vendorLocationId = vendorLocationId;
    }

    public boolean isDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultLocation(boolean defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

}
