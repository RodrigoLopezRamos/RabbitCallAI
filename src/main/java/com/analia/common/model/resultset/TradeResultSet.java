package com.analia.common.model.resultset;

import com.analia.common.cache.AnaliaCacheableEntity;
import com.analia.common.infrastructure.Trending;
import com.analia.common.infrastructure.location.GeoLocation;

import java.math.BigInteger;
import java.util.Date;

public class TradeResultSet extends GeoLocation implements AnaliaCacheableEntity<TradeResultSet>, Trending {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private BigInteger tradeId;
    private String description;
    private BigInteger directoryId;
    private boolean disabled;
    private String name;
    private Date notAfter;
    private Date notBefore;
    private Date startDate;
    private String title;
    private String website;
    private String tradeEmail;
    private String tradePhone;
    private BigInteger locationId;
    private String address1;
    private String address2;
    private BigInteger cityId;
    private String city;
    private String province;
    private String country;
    private String postalOrZipcode;
    private double latitude;
    private double longitude;
    private BigInteger categoryId;
    private BigInteger tradeCategoryId;
    private boolean favourited;

    private BigInteger vendorLocationId;
    private BigInteger tradeLocationId;

    private BigInteger vendorId;
    private String vendorEmail;
    private String vendorPhone;

    private String hoursOfOperations;
    private long totalReviews; // TODO Replace with trigger

    public TradeResultSet() {
    }

    public long getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(long totalReviews) {
        this.totalReviews = totalReviews;
    }

    public String getHoursOfOperations() {
        return hoursOfOperations;
    }

    public void setHoursOfOperations(String hoursOfOperations) {
        this.hoursOfOperations = hoursOfOperations;
    }

    public String getVendorPhone() {
        return vendorPhone;
    }

    public void setVendorPhone(String vendorPhone) {
        this.vendorPhone = vendorPhone;
    }

    public String getVendorEmail() {
        return vendorEmail;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

    /**
     * Getters and setters
     *
     * @return
     */
    public BigInteger getTradeId() {
        return tradeId;
    }

    public void setTradeId(BigInteger tradeId) {
        this.tradeId = tradeId;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public BigInteger getCityId() {
        return cityId;
    }

    public void setCityId(BigInteger cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalOrZipcode() {
        return postalOrZipcode;
    }

    public void setPostalOrZipcode(String postalOrZipcode) {
        this.postalOrZipcode = postalOrZipcode;
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

    public BigInteger getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(BigInteger categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigInteger getDirectoryId() {
        return this.directoryId;
    }

    public void setDirectoryId(BigInteger directoryId) {
        this.directoryId = directoryId;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public BigInteger getLocationId() {
        return this.locationId;
    }

    public void setLocationId(BigInteger locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getNotAfter() {
        return this.notAfter;
    }

    public void setNotAfter(Date notAfter) {
        this.notAfter = notAfter;
    }

    public Date getNotBefore() {
        return this.notBefore;
    }

    public void setNotBefore(Date notBefore) {
        this.notBefore = notBefore;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public BigInteger getVendorLocationId() {
        return vendorLocationId;
    }

    public void setVendorLocationId(BigInteger vendorLocationId) {
        this.vendorLocationId = vendorLocationId;
    }

    public BigInteger getVendorId() {
        return vendorId;
    }

    public void setVendorId(BigInteger vendorId) {
        this.vendorId = vendorId;
    }

    public String getTradeEmail() {
        return tradeEmail;
    }

    public void setTradeEmail(String tradeEmail) {
        this.tradeEmail = tradeEmail;
    }

    public String getTradePhone() {
        return tradePhone;
    }

    public void setTradePhone(String tradePhone) {
        this.tradePhone = tradePhone;
    }

    public BigInteger getTradeCategoryId() {
        return tradeCategoryId;
    }

    public void setTradeCategoryId(BigInteger tradeCategoryId) {
        this.tradeCategoryId = tradeCategoryId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public BigInteger getTradeLocationId() {
        return tradeLocationId;
    }

    public void setTradeLocationId(BigInteger tradeLocationId) {
        this.tradeLocationId = tradeLocationId;
    }


    public BigInteger getId() {
        return tradeId;
    }

    public boolean isFavourited() {
        return favourited;
    }


    public void setFavourited(boolean favourited) {
        this.favourited = favourited;
    }


    public TradeResultSet clone() throws CloneNotSupportedException {
        TradeResultSet trade = new TradeResultSet();
        trade.setTradeId(this.tradeId);
        trade.setDescription(this.description);
        trade.setDirectoryId(this.directoryId);
        trade.setDisabled(this.disabled);
        trade.setLocationId(this.locationId);
        trade.setName(this.name);
        trade.setNotAfter(this.notAfter);
        trade.setNotBefore(this.notBefore);
        trade.setTitle(this.title);
        trade.setWebsite(this.website);
        trade.setLocationId(this.locationId);
        trade.setAddress1(this.address1);
        trade.setAddress2(this.address2);
        trade.setCityId(this.cityId);
        trade.setCity(this.city);
        trade.setProvince(this.province);
        trade.setCountry(this.country);
        trade.setPostalOrZipcode(this.postalOrZipcode);
        trade.setLatitude(this.latitude);
        trade.setLongitude(this.longitude);
        trade.setVendorLocationId(this.vendorLocationId);
        trade.setCategoryId(this.categoryId);
        trade.setStartDate(startDate);
        trade.setTradeLocationId(this.tradeLocationId);
        trade.setFavourited(this.favourited);
        return trade;
    }


    public BigInteger getTrendingId() {
        return tradeId;
    }


    public void setLiked(boolean liked) {
        // TODO pending liked
    }

}
