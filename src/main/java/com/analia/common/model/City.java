package com.analia.common.model;

import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * The persistent class for the CITY database table.
 */
@Entity
@Table(name = "CITY")
@NamedQuery(name = "City.findAll", query = "SELECT c FROM City c")
public class City extends AnaliaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "zone_id")
    private BigInteger zoneId;
    private String name;
    private String province;
    private boolean active;
    private Timestamp timestamp;

    public City() {
        super();
    }

    public City(BigInteger id, String name, String province, boolean active, Timestamp timestamp, BigInteger zoneId) {
        super();
        this.id = id;
        this.active = active;
        this.name = name;
        this.province = province;
        this.active = active;
        this.timestamp = timestamp;
        this.zoneId = zoneId;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BigInteger getZoneId() {
        return this.zoneId;
    }

    public void setZoneId(BigInteger zoneId) {
        this.zoneId = zoneId;
    }

}