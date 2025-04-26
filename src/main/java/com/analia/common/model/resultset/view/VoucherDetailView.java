package com.analia.common.model.resultset.view;

import com.analia.common.infrastructure.Trending;
import com.analia.common.infrastructure.location.GeoLocation;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author Rodrigo Lopez
 */
@Entity
@Table(name = "VOUCHER_DETAIL_VIEW")
public class VoucherDetailView extends GeoLocation implements Serializable, Trending {

    private static final long serialVersionUID = 1L;

    private String description;

    @Column(name = "favourite_count")
    private int favouriteCount;

    @Transient
    private boolean favourited;

    @Id
    @Column(name = "vendor_location_voucher_id")
    private BigInteger vendorLocationVoucherId;

    @Transient
    private boolean liked;

    @Column(name = "location_count")
    private BigInteger locationCount;

    @Column(name = "liked_count")
    private BigInteger likedCount;

    @Column(name = "max_inventory")
    private BigInteger maxInventory;

    @Column(name = "maximum_per_user")
    private BigInteger maximumPerUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "not_after")
    private Date notAfter;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "not_before")
    private Date notBefore;

    @Column(name = "voucher_directory_id")
    private BigInteger voucherDirectoryId;

    @Column(name = "voucher_directory_video_count")
    private BigInteger voucherDirectoryVideoCount;

    @Column(name = "voucher_id")
    private BigInteger voucherId;

    @Column(name = "vouchervendor_id")
    private BigInteger vouchervendorId;


    @Column(name = "vouchertype_id")
    private BigInteger vouchertypeId;

    private BigDecimal price;

    @Column(name = "retail_value")
    private BigDecimal retailValue;

    @Column(name = "short_description")
    private String shortDescription;

    private boolean disabled;

    @Column(name = "status_id")
    private BigInteger statusId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "statuschanged_date")
    private Date statuschangedDate;

    private String title;

    @Column(name = "unlocking_credits")
    private BigInteger unlockingCredits;

    private String website;

    @Column(name = "vouchercategory_id")
    private BigInteger voucherCategoryId;

    @Column(name = "zone_id")
    private BigInteger zoneId;


    @Column(name = "processing_fee_percent")
    private BigDecimal processingFeePercent;


    @Column(name = "voucher_provider")
    private String voucherProvider;

    @Column(name = "cash_enabled")
    private boolean cashEnabled;

    @Column(name = "credit_enabled")
    private boolean creditEnabled;

    @Column(name = "vendorlocation_id")
    private BigInteger vendorLocationId;

    @Column(name = "trade_vendor_location_id")
    private BigInteger tradeVendorLocationId;

    @Column(name = "terms_url")
    private String termsUrl;


    @Column(name = "fine_print")
    private String finePrint;

    @Column(name = "instructions")
    private String instructions;

    @Column(name = "purchase_credits_reward")
    private BigInteger purchaseCreditsReward;

    private BigInteger sold;

    @Transient
    private VendorLocationDetailView vendorLocationDetailView;

    public VoucherDetailView() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFavouriteCount() {
        return favouriteCount;
    }

    public void setFavouriteCount(int favouriteCount) {
        this.favouriteCount = favouriteCount;
    }

    public boolean getFavourited() {
        return favourited;
    }

    public void setFavourited(boolean favourited) {
        this.favourited = favourited;
    }

    public BigInteger getVendorLocationVoucherId() {
        return vendorLocationVoucherId;
    }

    public void setVendorLocationVoucherId(BigInteger vendorLocationVoucherId) {
        this.vendorLocationVoucherId = vendorLocationVoucherId;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public BigInteger getLocationCount() {
        return locationCount;
    }

    public void setLocationCount(BigInteger locationCount) {
        this.locationCount = locationCount;
    }

    public BigInteger getLikedCount() {
        return likedCount;
    }

    public void setLikedCount(BigInteger likedCount) {
        this.likedCount = likedCount;
    }

    public BigInteger getMaxInventory() {
        return maxInventory;
    }

    public void setMaxInventory(BigInteger maxInventory) {
        this.maxInventory = maxInventory;
    }

    public BigInteger getMaximumPerUser() {
        return maximumPerUser;
    }

    public void setMaximumPerUser(BigInteger maximumPerUser) {
        this.maximumPerUser = maximumPerUser;
    }

    public Date getNotAfter() {
        return notAfter;
    }

    public void setNotAfter(Date notAfter) {
        this.notAfter = notAfter;
    }

    public Date getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(Date notBefore) {
        this.notBefore = notBefore;
    }

    public BigInteger getVoucherDirectoryId() {
        return voucherDirectoryId;
    }

    public void setVoucherDirectoryId(BigInteger voucherDirectoryId) {
        this.voucherDirectoryId = voucherDirectoryId;
    }

    public BigInteger getVoucherDirectoryVideoCount() {
        return voucherDirectoryVideoCount;
    }

    public void setVoucherDirectoryVideoCount(BigInteger voucherDirectoryVideoCount) {
        this.voucherDirectoryVideoCount = voucherDirectoryVideoCount;
    }

    public BigInteger getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(BigInteger voucherId) {
        this.voucherId = voucherId;
    }

    public BigInteger getVouchertypeId() {
        return vouchertypeId;
    }

    public void setVouchertypeId(BigInteger vouchertypeId) {
        this.vouchertypeId = vouchertypeId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getRetailValue() {
        return retailValue;
    }

    public void setRetailValue(BigDecimal retailValue) {
        this.retailValue = retailValue;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public BigInteger getSold() {
        return sold;
    }

    public void setSold(BigInteger sold) {
        this.sold = sold;
    }

    public BigInteger getStatusId() {
        return statusId;
    }

    public void setStatusId(BigInteger statusId) {
        this.statusId = statusId;
    }

    public Date getStatuschangedDate() {
        return statuschangedDate;
    }

    public void setStatuschangedDate(Date statuschangedDate) {
        this.statuschangedDate = statuschangedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigInteger getUnlockingCredits() {
        return unlockingCredits;
    }

    public void setUnlockingCredits(BigInteger unlockingCredits) {
        this.unlockingCredits = unlockingCredits;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


    public BigInteger getVoucherCategoryId() {
        return voucherCategoryId;
    }

    public void setVoucherCategoryId(BigInteger voucherCategoryId) {
        this.voucherCategoryId = voucherCategoryId;
    }

    public BigInteger getZoneId() {
        return zoneId;
    }

    public void setZoneId(BigInteger zoneId) {
        this.zoneId = zoneId;
    }

    public String getVoucherProvider() {
        return voucherProvider;
    }

    public void setVoucherProvider(String voucherProvider) {
        this.voucherProvider = voucherProvider;
    }

    public boolean isCashEnabled() {
        return cashEnabled;
    }

    public void setCashEnabled(boolean cashEnabled) {
        this.cashEnabled = cashEnabled;
    }

    public boolean isCreditEnabled() {
        return creditEnabled;
    }

    public void setCreditEnabled(boolean creditEnabled) {
        this.creditEnabled = creditEnabled;
    }

    public BigInteger getVendorLocationId() {
        return vendorLocationId;
    }

    public void setVendorLocationId(BigInteger vendorLocationId) {
        this.vendorLocationId = vendorLocationId;
    }

    public BigInteger getTradeVendorLocationId() {
        return tradeVendorLocationId;
    }

    public void setTradeVendorLocationId(BigInteger tradeVendorLocationId) {
        this.tradeVendorLocationId = tradeVendorLocationId;
    }

    public String getTermsUrl() {
        return termsUrl;
    }

    public void setTermsUrl(String termsUrl) {
        this.termsUrl = termsUrl;
    }


    public String getFinePrint() {
        return finePrint;
    }

    public void setFinePrint(String finePrint) {
        this.finePrint = finePrint;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public BigInteger getPurchaseCreditsReward() {
        return purchaseCreditsReward;
    }

    public void setPurchaseCreditsReward(BigInteger purchaseCreditsReward) {
        this.purchaseCreditsReward = purchaseCreditsReward;
    }

    public VendorLocationDetailView getVendorLocationDetailView() {
        return vendorLocationDetailView;
    }

    public void setVendorLocationDetailView(VendorLocationDetailView vendorLocationDetailView) {
        this.vendorLocationDetailView = vendorLocationDetailView;
    }


    public BigInteger getTrendingId() {
        return voucherId;
    }



    public double getLongitude() {
        return vendorLocationDetailView != null ? vendorLocationDetailView.getLongitude() : 0;
    }


    public double getLatitude() {
        return vendorLocationDetailView != null ? vendorLocationDetailView.getLatitude() : 0;
    }


    public BigInteger getId() {
        return vendorLocationVoucherId;
    }

    public BigDecimal getProcessingFeePercent() {
        return processingFeePercent;
    }

    public void setProcessingFeePercent(BigDecimal processingFeePercent) {
        this.processingFeePercent = processingFeePercent;
    }

    public BigInteger getVouchervendorId() {
        return vouchervendorId;
    }

    public void setVouchervendorId(BigInteger vouchervendorId) {
        this.vouchervendorId = vouchervendorId;
    }
}