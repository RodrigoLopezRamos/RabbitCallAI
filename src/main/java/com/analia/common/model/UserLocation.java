package com.analia.common.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "USER_LOCATION")
public class UserLocation extends AnaliaEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "zone_id")
    private BigInteger zoneId;
    @Column(name = "user_id")
    private BigInteger userId;

    private double latitude;
    private double longitude;
    @Column(name = "default_location")
    private boolean defaultLocation;

    @Transient
    private boolean gpsIsEnable = false;
    private Timestamp timestamp;

    public boolean isGpsIsEnable() {
        return gpsIsEnable;
    }

    public void setGpsIsEnable(boolean gpsIsEnable) {
        this.gpsIsEnable = gpsIsEnable;
    }

    public BigInteger getZoneId() {
        return zoneId;
    }

    public void setZoneId(BigInteger zoneId) {
        this.zoneId = zoneId;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }


   

 

    public boolean isDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultLocation(boolean defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

}
