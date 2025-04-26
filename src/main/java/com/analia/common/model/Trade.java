package com.analia.common.model;

import jakarta.persistence.*;


import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;


@NamedQuery( name = "getTrades", query = "select t.id as tradeId, t.description, t.directoryId, t.disabled, t.name, t.notAfter, t.notBefore, t.title, t.website, l.id as locationId, l.address1, l.address2, l.cityId, l.city, l.province, l.country, l.postalOrZipcode, l.latitude, l.longitude, tl.vendorLocationId, c.id as categoryId, t.startDate, tl.id as tradeLocationId,(select count(r.id) from Review r where r.tradeId = tc.tradeId) as totalReviews from Trade t , TradeCategory tc, Category c, TradeLocation tl, VendorLocation vl, Location l, City ct where (t.id = tc.tradeId) and (tc.categoryId = c.id) and (c.id = :categoryId) and (c.categoryTypeId = :categoryTypeId) and (tc.categoryId = c.id) and (tc.tradeId =t.id) and (tl.tradeCategoryId = tc.id) and (vl.id = tl.vendorLocationId) and (vl.locationId = l.id) and (ct.id = l.cityId) and (ct.zoneId = :zoneId) and (t.disabled = false)")
@NamedQuery( name = "totalTradeCountByZoneId", query = "select count(t.id) from Trade t , TradeCategory tc, Category c, TradeLocation tl, VendorLocation vl, Location l, City ct where (t.id = tc.tradeId) and (tc.categoryId = c.id) and (c.id = :categoryId) and (c.categoryTypeId = :categoryTypeId) and (tc.categoryId = c.id) and (tc.tradeId =t.id) and (tl.tradeCategoryId = tc.id) and (vl.id = tl.vendorLocationId) and (vl.locationId = l.id) and (ct.id = l.cityId) and (ct.zoneId = :zoneId) and (t.disabled = false)")
@NamedQuery( name = "getTradesBySearchParam", query = "select t.id as tradeId, t.description, t.directoryId, t.disabled, t.name, t.notAfter, t.notBefore, t.title, t.website, l.id as locationId, l.address1, l.address2, l.cityId, l.city, l.province, l.country, l.postalOrZipcode, l.latitude, l.longitude, tl.vendorLocationId, c.id as categoryId, t.startDate, tl.id as tradeLocationId, (select count(r.id) from Review r where r.tradeId = tc.tradeId) as totalReviews from Trade t , TradeCategory tc, Category c, TradeLocation tl, VendorLocation vl, Location l where ((t.name like :search) or (t.description like :search)) and (t.id = tc.tradeId) and (tc.categoryId = c.id) and (tc.tradeId =t.id) and (tl.tradeCategoryId = tc.id) and (vl.id = tl.vendorLocationId) and (vl.locationId = l.id) and (t.disabled = false)")
@NamedQuery( name = "getTrade", query = "select t.id as tradeId, t.description, t.directoryId, t.disabled, t.name, t.notAfter, t.notBefore, t.title, t.website, l.id as locationId, l.address1, l.address2, l.cityId, l.city, l.province, l.country, l.postalOrZipcode, l.latitude, l.longitude, tl.vendorLocationId, c.id as categoryId, t.startDate, tl.id as tradeLocationId, (select count(r.id) from Review r where r.tradeId = tc.tradeId) as totalReviews from Trade t , TradeCategory tc, Category c, TradeLocation tl, VendorLocation vl, Location l where (tl.id =:tradeLocationId) and (t.id = tc.tradeId) and (tc.categoryId = c.id) and (tc.tradeId =t.id) and (tl.tradeCategoryId = tc.id) and (vl.id = tl.vendorLocationId) and (vl.locationId = l.id) and (t.disabled = false)")
@NamedQuery( name = "getTradeAdditionalData", query = "select ta from TradeAdditionalData ta where (ta.tradeId = :tradeId) and (ta.disabled = false)")
@NamedQuery( name = "getTradeTagsByTradeId", query = "select t from Tag t , TradeTag tg where (tg.tradeId = :tradeId) and (t.id = tg.tagId) and (t.disabled = false) and (tg.disabled = false)")

/**
 * The persistent class for the TRADE database table.
 */
@Entity
@Table(name = "TRADE")
public class Trade extends AnaliaEntity implements Serializable {

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

    @Column(name = "directory_id")
    private BigInteger directoryId;
    @Column(name = "vendor_id")
    private BigInteger vendorId;
    @Column(name = "zone_id")
    private BigInteger zoneId;

    private String name;
    private String title;
    private String description;
    private String website;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "not_before")
    private Date notBefore;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "not_after")
    private Date notAfter;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Date startDate;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private Date endDate;


    @Column(name = "created_by")
    private BigInteger createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_datetime")
    private Date createDatetime;
    private boolean disabled;
    private Timestamp timestamp;

    public Trade() {
        super();
    }

    public Trade(BigInteger id, BigInteger directoryId, BigInteger vendorId, String name, String title, String description, String website, Date notBefore, Date notAfter, Date startDate, boolean disabled) {
        super();
        this.id = id;
        this.directoryId = directoryId;
        this.vendorId = vendorId;
        this.name = name;
        this.title = title;
        this.description = description;
        this.website = website;
        this.notBefore = notBefore;
        this.notAfter = notAfter;
        this.startDate = startDate;
        this.disabled = disabled;
    }

   

 

    public BigInteger getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(BigInteger directoryId) {
        this.directoryId = directoryId;
    }

    public BigInteger getVendorId() {
        return vendorId;
    }

    public void setVendorId(BigInteger vendorId) {
        this.vendorId = vendorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Date getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(Date notBefore) {
        this.notBefore = notBefore;
    }

    public Date getNotAfter() {
        return notAfter;
    }

    public void setNotAfter(Date notAfter) {
        this.notAfter = notAfter;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public BigInteger getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BigInteger createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public BigInteger getZoneId() {
        return zoneId;
    }

    public void setZoneId(BigInteger id2) {
        this.zoneId = id2;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}