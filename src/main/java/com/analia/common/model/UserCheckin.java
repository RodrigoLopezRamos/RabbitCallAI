package com.analia.common.model;

import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The persistent class for the USER_CHECKIN database table.
 */
@Entity
@Table(name = "USER_CHECKIN")
public class UserCheckin extends AnaliaEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_datetime")
    private Date createdDatetime;

    @Column(name = "vouchervendorlocation_id")
    private BigInteger voucherVendorLocationId;

    private Timestamp timestamp;

    @Column(name = "user_id")
    private BigInteger userId;

    @Column(name = "trade_location_id")
    private BigInteger tradelocationId;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

   

 

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getTradelocationId() {
        return tradelocationId;
    }

    public void setTradelocationId(BigInteger tradelocationId) {
        this.tradelocationId = tradelocationId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public BigInteger getVoucherVendorLocationId() {
        return voucherVendorLocationId;
    }

    public void setVoucherVendorLocationId(BigInteger voucherVendorLocationId) {
        this.voucherVendorLocationId = voucherVendorLocationId;
    }

}