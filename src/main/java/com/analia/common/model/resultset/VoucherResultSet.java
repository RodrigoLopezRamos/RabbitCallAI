package com.analia.common.model.resultset;

import com.analia.common.cache.AnaliaCacheableEntity;
import com.analia.common.infrastructure.Trending;
import com.analia.common.infrastructure.location.GeoLocation;

import java.math.BigInteger;
import java.util.Date;

public class VoucherResultSet extends GeoLocation implements AnaliaCacheableEntity<VoucherResultSet>, Trending {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private BigInteger voucherVendorLocationId;
    private BigInteger voucherTypeId;
    private BigInteger zoneId;
    private BigInteger directoryId;
    private String title;
    private Date notBefore;
    private Date notAfter;
    private boolean disabled;
    private double latitude;
    private double longitude;
    private int vendorLocationId;
    private Date startDate;
    private Date endDate;
    private BigInteger voucherId;
    private boolean liked;
    private boolean favourited;
    private String categoryName;
    private BigInteger categoryId;



    public VoucherResultSet clone() throws CloneNotSupportedException {
        VoucherResultSet resultSet = new VoucherResultSet();
        resultSet.voucherVendorLocationId = voucherVendorLocationId;
        resultSet.voucherTypeId = this.voucherTypeId;
        resultSet.zoneId = this.zoneId;
        resultSet.directoryId = this.directoryId;
        resultSet.title = this.title;
        resultSet.notBefore = this.notBefore;
        resultSet.notAfter = this.notAfter;
        resultSet.disabled = this.disabled;
        resultSet.latitude = this.latitude;
        resultSet.longitude = this.longitude;
        resultSet.vendorLocationId = this.vendorLocationId;
        resultSet.setStartDate(this.startDate);
        resultSet.setEndDate(this.endDate);
        resultSet.voucherId = this.voucherId;
        resultSet.setCategoryId(this.categoryId);
        resultSet.setCategoryName(this.categoryName);
        return resultSet;
    }

    public BigInteger getVoucherTypeId() {
        return voucherTypeId;
    }

    public void setVoucherTypeId(BigInteger voucherTypeId) {
        this.voucherTypeId = voucherTypeId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigInteger getZoneId() {
        return zoneId;
    }

    public void setZoneId(BigInteger zoneId) {
        this.zoneId = zoneId;
    }

    public BigInteger getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(BigInteger directoryId) {
        this.directoryId = directoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public double getVendorLocationId() {
        return vendorLocationId;
    }

    public void setVendorLocationId(int vendorLocationId) {
        this.vendorLocationId = vendorLocationId;
    }


    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    public BigInteger getId() {
        return voucherVendorLocationId;
    }

    public BigInteger getVoucherVendorLocationId() {
        return voucherVendorLocationId;
    }

    public void setVoucherVendorLocationId(BigInteger voucherVendorLocationId) {
        this.voucherVendorLocationId = voucherVendorLocationId;
    }


    public BigInteger getTrendingId() {
        return getVoucherId();
    }

    public boolean isLiked() {
        return liked;
    }


    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public boolean isFavourited() {
        return favourited;
    }


    public void setFavourited(boolean favourited) {
        this.favourited = favourited;
    }

    public BigInteger getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(BigInteger voucherId) {
        this.voucherId = voucherId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public BigInteger getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(BigInteger categoryId) {
        this.categoryId = categoryId;
    }
}
