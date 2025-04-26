package com.analia.common.model;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "TRADE_LOCATION")
public class TradeLocation extends AnaliaEntity {

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

    @Column(name = "tradecategory_id")
    private BigInteger tradeCategoryId;
    @Column(name = "vendorlocation_id")
    private BigInteger vendorLocationId;
    @Column(name = "created_datetime")
    private Date createdDatetime;
    private Timestamp timestamp;

    public TradeLocation() {
        super();
    }

    public TradeLocation(BigInteger id, BigInteger tradeCategoryId, BigInteger vendorLocationId) {
        super();
        this.id = id;
        this.tradeCategoryId = tradeCategoryId;
        this.vendorLocationId = vendorLocationId;
    }

   

 

    public BigInteger getTradeCategoryId() {
        return tradeCategoryId;
    }

    public void setTradeCategoryId(BigInteger tradeCategoryId) {
        this.tradeCategoryId = tradeCategoryId;
    }

    public BigInteger getVendorLocationId() {
        return vendorLocationId;
    }

    public void setVendorLocationId(BigInteger vendorLocationId) {
        this.vendorLocationId = vendorLocationId;
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

}
