package com.analia.common.model.resultset.view;

import com.analia.common.model.AnaliaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 */
@Entity
@Table(name = "VOUCHER_VENDOR_TAX_GROUP_VIEW")
public class VoucherVendorTaxGroupView extends AnaliaEntity implements Serializable {
    @Id
    @Column(name = "view_id")
    private String viewId;

    @Column(name = "voucher_vendor_id")
    private BigInteger voucherVendorId;

    @Column(name = "voucher_id")
    private BigInteger voucherId;

    @Column(name = "zone_id")
    private BigInteger zoneId;

    @Column(name = "max_inventory")
    private BigInteger maxInventory;

    private BigInteger sold;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "retail_value")
    private BigDecimal retailValue;

    @Column(name = "not_before")
    private Date notBefore;

    @Column(name = "not_after")
    private Date notAfter;

    @Column(name = "status_id")
    private BigInteger statusId;

    private boolean disabled;

    @Column(name = "maximum_per_user")
    private BigInteger maximumPerUser;

    @Column(name = "tax_group_id")
    private BigInteger taxGroupId;

    @Column(name = "tax_id")
    private BigInteger taxId;

    @Column(name = "tax_range_id")
    private BigInteger taxRangeId;

    private String description;

    @Column(name = "vendor_id")
    private BigInteger vendorId;

    @Column(name = "voucher_percent")
    private BigDecimal voucherPercent;

    @Column(name = "relative_percentage")
    private BigDecimal relativepercentage;

    @Column(name = "tax_group_name")
    private String taxGroupName;

    @Column(name = "equal_or_higher_than")
    private BigDecimal equalOrHigherThan;

    @Column(name = "lower_than")
    private BigDecimal lowerThan;

    @Column(name = "percentage")
    private BigDecimal percentage;

    @Column(name = "credit_enabled")
    private boolean creditEnabled;

    @Column(name = "cash_enabled")
    private boolean cash_enabled;

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public BigInteger getVoucherVendorId() {
        return voucherVendorId;
    }

    public void setVoucherVendorId(BigInteger voucherVendorId) {
        this.voucherVendorId = voucherVendorId;
    }

    public BigInteger getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(BigInteger voucherId) {
        this.voucherId = voucherId;
    }

    public BigInteger getZoneId() {
        return zoneId;
    }

    public void setZoneId(BigInteger zoneId) {
        this.zoneId = zoneId;
    }

    public BigInteger getMaxInventory() {
        return maxInventory;
    }

    public void setMaxInventory(BigInteger maxInventory) {
        this.maxInventory = maxInventory;
    }

    public BigInteger getSold() {
        return sold;
    }

    public void setSold(BigInteger sold) {
        this.sold = sold;
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

    public BigInteger getStatusId() {
        return statusId;
    }

    public void setStatusId(BigInteger statusId) {
        this.statusId = statusId;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public BigInteger getMaximumPerUser() {
        return maximumPerUser;
    }

    public void setMaximumPerUser(BigInteger maximumPerUser) {
        this.maximumPerUser = maximumPerUser;
    }

    public BigInteger getTaxGroupId() {
        return taxGroupId;
    }

    public void setTaxGroupId(BigInteger taxGroupId) {
        this.taxGroupId = taxGroupId;
    }

    public BigInteger getTaxId() {
        return taxId;
    }

    public void setTaxId(BigInteger taxId) {
        this.taxId = taxId;
    }

    public BigInteger getTaxRangeId() {
        return taxRangeId;
    }

    public void setTaxRangeId(BigInteger taxRangeId) {
        this.taxRangeId = taxRangeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigInteger getVendorId() {
        return vendorId;
    }

    public void setVendorId(BigInteger vendorId) {
        this.vendorId = vendorId;
    }

    public BigDecimal getVoucherPercent() {
        return voucherPercent;
    }

    public void setVoucherPercent(BigDecimal voucherPercent) {
        this.voucherPercent = voucherPercent;
    }

    public BigDecimal getRelativepercentage() {
        return relativepercentage;
    }

    public void setRelativepercentage(BigDecimal relativepercentage) {
        this.relativepercentage = relativepercentage;
    }

    public String getTaxGroupName() {
        return taxGroupName;
    }

    public void setTaxGroupName(String taxGroupName) {
        this.taxGroupName = taxGroupName;
    }

    public BigDecimal getEqualOrHigherThan() {
        return equalOrHigherThan;
    }

    public void setEqualOrHigherThan(BigDecimal equalOrHigherThan) {
        this.equalOrHigherThan = equalOrHigherThan;
    }

    public BigDecimal getLowerThan() {
        return lowerThan;
    }

    public void setLowerThan(BigDecimal lowerThan) {
        this.lowerThan = lowerThan;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public boolean isCreditEnabled() {
        return creditEnabled;
    }

    public void setCreditEnabled(boolean creditEnabled) {
        this.creditEnabled = creditEnabled;
    }

    public boolean isCash_enabled() {
        return cash_enabled;
    }

    public void setCash_enabled(boolean cash_enabled) {
        this.cash_enabled = cash_enabled;
    }
}