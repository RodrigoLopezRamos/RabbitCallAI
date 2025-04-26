package com.analia.common.model;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "TRADE_FEATURED")
public class TradeFeatured extends AnaliaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Column(name = "tradecategory_id")
    private BigInteger tradeCategoryId;

    @Column(name = "created_datetime")
    private Date createdDatetime;

    @Column(name = "deslisted_datetime")
    private Date deslistedDatetime;

    private Timestamp timestamp;

   

 

    public BigInteger getTradeCategoryId() {
        return tradeCategoryId;
    }

    public void setTradeCategoryId(BigInteger tradeCategoryId) {
        this.tradeCategoryId = tradeCategoryId;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Date getDeslistedDatetime() {
        return deslistedDatetime;
    }

    public void setDeslistedDatetime(Date deslistedDatetime) {
        this.deslistedDatetime = deslistedDatetime;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
