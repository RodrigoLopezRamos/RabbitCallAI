package com.analia.common.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The persistent class for the TAX_GROUP database table.
 */
@Entity
@Table(name = "TAX_GROUP")
@NamedQuery(name = "TaxGroup.findAll", query = "SELECT t FROM TaxGroup t")
public class TaxGroup extends AnaliaEntity implements Serializable {

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

    @Column(name = "city_id")
    private BigInteger cityId;
    @Column(name = "client_id")
    private BigInteger clientId;
    private String name;
    private String description;
    private boolean locked;
    private boolean disabled;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_datetime")
    private Date createdDatetime;
    private Timestamp timestamp;

    public TaxGroup() {
        super();
    }

    public TaxGroup(BigInteger id, BigInteger cityId, Date createdDatetime, String description, boolean disabled, boolean locked,
                    String name, Timestamp timestamp) {
        super();
        this.id = id;
        this.cityId = cityId;
        this.createdDatetime = createdDatetime;
        this.description = description;
        this.disabled = disabled;
        this.locked = locked;
        this.name = name;
        this.timestamp = timestamp;
    }


 

    public BigInteger getCityId() {
        return this.cityId;
    }

    public void setCityId(BigInteger cityId) {
        this.cityId = cityId;
    }

    public Date getCreatedDatetime() {
        return this.createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean getLocked() {
        return this.locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public BigInteger getClientId() {
        return clientId;
    }

    public void setClientId(BigInteger clientId) {
        this.clientId = clientId;
    }
}