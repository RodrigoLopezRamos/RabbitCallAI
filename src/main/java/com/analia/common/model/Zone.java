package com.analia.common.model;

import com.analia.common.cache.AnaliaCacheableEntity;
import com.analia.common.infrastructure.location.GeoLocation;

import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "ZONE")
public class Zone extends GeoLocation implements AnaliaCacheableEntity<Zone> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private String timezone;
    @Column(name = "currency_type")
    private String currencyType;
    private String country;
    @Column(name = "max_radius")
    private int maxRadius;
    @Column(name = "center_latitude")
    private double centerLatitude;
    @Column(name = "center_longitude")
    private double centerLongitude;
    private boolean active;
    private Date timestamp;

    public Zone() {
        super();
    }

    public Zone(BigInteger id, String name, String description, String timezone, String currencyType, String country,
                int maxRadius, double centerLatitude, double centerLongitude, boolean active, Date timestamp) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.timezone = timezone;
        this.currencyType = currencyType;
        this.country = country;
        this.maxRadius = maxRadius;
        this.centerLatitude = centerLatitude;
        this.centerLongitude = centerLongitude;
        this.active = active;
        this.timestamp = timestamp;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getMaxRadius() {
        return maxRadius;
    }

    public void setMaxRadius(int maxRadius) {
        this.maxRadius = maxRadius;
    }

    public double getCenterLatitude() {
        return centerLatitude;
    }

    public void setCenterLatitude(double centerLatitude) {
        this.centerLatitude = centerLatitude;
    }

    public double getCenterLongitude() {
        return centerLongitude;
    }

    public void setCenterLongitude(double centerLongitude) {
        this.centerLongitude = centerLongitude;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }


    public double getLongitude() {
        return centerLongitude;
    }


    public double getLatitude() {
        return centerLatitude;
    }

    public Zone clone() throws CloneNotSupportedException {
        return this;
    }

}
