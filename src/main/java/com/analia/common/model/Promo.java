package com.analia.common.model;

import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the PROMO database table.
 */
@Entity
@Table(name = "PROMO")
public class Promo extends AnaliaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    private String code;

    @Column(name = "created_by")
    private int createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_datetime")
    private Date createdDatetime;

    @Column(name = "credit_value")
    private int creditValue;

    private byte disabled;

    @Temporal(TemporalType.DATE)
    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "max_redeem")
    private int maxRedeem;

    @Column(name = "redeemed_count")
    private int redeemedCount;

    @Column(name = "grouping_id")
    private Integer groupingId;

    private Timestamp timestamp;

    @Column(name = "unique_promo")
    private byte uniquePromo;

    public Promo() {
    }


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDatetime() {
        return this.createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public int getCreditValue() {
        return this.creditValue;
    }

    public void setCreditValue(int creditValue) {
        this.creditValue = creditValue;
    }

    public byte getDisabled() {
        return this.disabled;
    }

    public void setDisabled(byte disabled) {
        this.disabled = disabled;
    }

    public Date getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getMaxRedeem() {
        return this.maxRedeem;
    }

    public void setMaxRedeem(int maxRedeem) {
        this.maxRedeem = maxRedeem;
    }

    public int getRedeemedCount() {
        return this.redeemedCount;
    }

    public void setRedeemedCount(int redeemedCount) {
        this.redeemedCount = redeemedCount;
    }

    public Integer getGroupingId() {
        return this.groupingId;
    }

    public void setGroupingId(Integer groupingId) {
        this.groupingId = groupingId;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public byte getUniquePromo() {
        return this.uniquePromo;
    }

    public void setUniquePromo(byte uniquePromo) {
        this.uniquePromo = uniquePromo;
    }

}