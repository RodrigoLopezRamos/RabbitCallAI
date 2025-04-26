package com.analia.common.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@NamedQuery( name = "getVouchers", query = "select vvl.id, v.zoneId, v.directoryId, v.title, v.notBefore, v.notAfter, v.voucherTypeId, l.latitude, l.longitude, v.id as voucherId, c.name, c.id as categoryId from Voucher v, VoucherCategory vc, VoucherVendor vv, VendorLocationVoucher vvl, VendorLocation vl, Location l, Category c where(v.id = vc.voucherId) and (vc.categoryId =c.id) and (v.voucherTypeId = :voucherTypeId) and (vc.categoryId = :categoryId) and (c.categoryTypeId = 2) and (vc.id = vv.voucherCategoryId) and (vv.id = vvl.voucherVendorId) and (vvl.vendorLocationId = vl.id) and (vl.locationId = l.id) and (l.cityId = :cityId) and (v.disabled = false)")
@NamedQuery( name = "getVouchersOrderByDate", query = "select vvl.id, v.zoneId, v.directoryId, v.title, v.notBefore, v.notAfter, v.voucherTypeId, l.latitude, l.longitude, v.id as voucherId, c.name, c.id as categoryId from Voucher v, VoucherCategory vc, VoucherVendor vv, VendorLocationVoucher vvl, VendorLocation vl, Location l, Category c where (v.id = vc.voucherId) and (vc.categoryId =c.id) and (v.voucherTypeId = :voucherTypeId) and (c.categoryTypeId = 2) and (vc.id = vv.voucherCategoryId) and (vv.id = vvl.voucherVendorId) and (vvl.vendorLocationId = vl.id) and (vl.locationId = l.id) and (l.cityId = :cityId) and (v.disabled = false) order by v.createdDatetime desc")
@NamedQuery( name = "totalVoucherCountByZoneId", query = "select count(vvl.id) from Voucher v, VoucherCategory vc, VoucherVendor vv, VendorLocationVoucher vvl, VendorLocation vl, Location l where (v.id = vc.voucherId) and (v.voucherTypeId = :voucherTypeId) and (vc.categoryId = :categoryId) and (vc.id = vv.voucherCategoryId) and (vv.id = vvl.voucherVendorId) and (vvl.vendorLocationId = vl.id) and (vl.locationId = l.id) and (l.cityId = :cityId) and (v.disabled = false)")
@NamedQuery( name = "getVouchersByVendorLocationId", query = "select vvl.id, v.zoneId, v.directoryId, v.title, v.notBefore, v.notAfter, v.voucherTypeId, l.latitude, l.longitude from Voucher v, VoucherCategory vc, VoucherVendor vv, VendorLocationVoucher vvl, VendorLocation vl, Location l where (v.id = vc.voucherId) and (v.voucherTypeId = :voucherTypeId) and (vc.categoryId = :categoryId) and (vc.id = vv.voucherCategoryId) and (vv.id = vvl.voucherVendorId) and (vvl.vendorLocationId = vl.id) and (vl.id = :vendorLocationId) and (vl.locationId = l.id) and (v.disabled = false)")
@NamedQuery( name = "getVouchersBoughtInPurchaseForVoucherId", query = "select v from PurchaseDetail pd, Purchase p, Voucher v, VoucherVendor vv where (pd.purchaseId = p.id) and (p.vouchervendorId = vv.id) and (vv.voucherId = :voucherId)")
@NamedQuery( name = "getVoucherBySearchParam", query = "select vvl.id, v.zoneId, v.directoryId, v.title, v.notBefore, v.notAfter, v.voucherTypeId, l.latitude, l.longitude, v.id as voucherId, c.name, c.id as categoryId from Voucher v, VoucherCategory vc, VoucherVendor vv, VendorLocationVoucher vvl, VendorLocation vl, Location l, Category c where ((v.title like :search) or (v.description like :search) or (v.shortDescription like :search) or (c.name like :search)) and (vc.categoryId =c.id) and (c.categoryTypeId = 2) and (v.id = vc.voucherId) and (vc.id = vv.voucherCategoryId) and (vv.id = vvl.voucherVendorId) and (vvl.vendorLocationId = vl.id) and (vl.locationId = l.id) and (v.disabled = false)")
@NamedQuery(name = "getVoucher",
        query = "select v from Voucher v where (v.id = :id)",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "false"))
@Entity
@Table(name = "VOUCHER")
public class Voucher extends AnaliaEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    @Column(name = "vouchertype_id")
    private BigInteger voucherTypeId;
    @Column(name = "zone_id")
    private BigInteger zoneId;
    @Column(name = "directory_id")
    private BigInteger directoryId;
    @Column(name = "voucher_provider")
    private String voucherProvider;
    private String title;
    @Column(name = "short_description")
    private String shortDescription;
    private String description;
    private String website;
    @Column(name = "retail_value")
    private BigDecimal retailValue;
    private BigDecimal price;
    @Column(name = "processing_fee_percent")
    private BigDecimal processingFeePercent;
    @Column(name = "not_before")
    private Date notBefore;
    @Column(name = "not_after")
    private Date notAfter;
    @Column(name = "max_inventory")
    private BigInteger maxInventory;
    @Column(name = "maximum_per_user")
    private BigInteger maximumPerUser;
    private BigInteger sold;
    @Column(name = "status_id")
    private BigInteger statusId;
    @Column(name = "statuschanged_by")
    private BigInteger statusChangedBy;
    @Column(name = "statuschanged_date")
    private Date statusChangedDate;
    @Column(name = "terms_url")
    private String termsUrl;
    @Column(name = "fine_print")
    private String finePrint;
    private boolean disabled;
    @Column(name = "created_by")
    private BigInteger createdBy;
    @Column(name = "created_datetime")
    private Date createdDatetime;
    private Date timestamp;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "purchase_credits_reward")
    private BigInteger purchaseCreditsReward;

    @Column(name = "unlocking_credits")
    private BigInteger unlockingCredits;

    @Column(name = "external_id")
    private BigInteger externalId;



    public Voucher() {
        super();
    }

    public Voucher(BigInteger id, BigInteger voucherTypeId, BigInteger zoneId, BigInteger directoryId, String voucherProvider, String title,
                   String shortDescription, String description, String website, BigDecimal retailValue,
                   BigDecimal processingFeePercent, Date notBefore, Date notAfter, BigInteger maxInventory, BigInteger maximunPerUser,
                   BigInteger sold, BigInteger statusId, BigInteger statusChangedBy, Date statusChangedDate, String termsUrl, String finePrint,
                   boolean disabled, BigInteger createdBy, Date createdDatetime, Date timestamp) {
        super();
        this.id = id;
        this.voucherTypeId = voucherTypeId;
        this.zoneId = zoneId;
        this.directoryId = directoryId;
        this.voucherProvider = voucherProvider;
        this.title = title;
        this.shortDescription = shortDescription;
        this.description = description;
        this.website = website;
        this.retailValue = retailValue;
        this.processingFeePercent = processingFeePercent;
        this.notBefore = notBefore;
        this.notAfter = notAfter;
        this.maxInventory = maxInventory;
        this.maximumPerUser = maximunPerUser;
        this.sold = sold;
        this.statusId = statusId;
        this.statusChangedBy = statusChangedBy;
        this.statusChangedDate = statusChangedDate;
        this.termsUrl = termsUrl;
        this.finePrint = finePrint;
        this.disabled = disabled;
        this.createdBy = createdBy;
        this.createdDatetime = createdDatetime;
        this.timestamp = timestamp;
    }

    public BigInteger getMaximumPerUser() {
        return maximumPerUser;
    }

    public void setMaximumPerUser(BigInteger maximumPerUser) {
        this.maximumPerUser = maximumPerUser;
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

    public BigInteger getVoucherTypeId() {
        return voucherTypeId;
    }

    public void setVoucherTypeId(BigInteger voucherTypeId) {
        this.voucherTypeId = voucherTypeId;
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

    public String getVoucherProvider() {
        return voucherProvider;
    }

    public void setVoucherProvider(String voucherProvider) {
        this.voucherProvider = voucherProvider;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
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

    public BigDecimal getRetailValue() {
        return retailValue;
    }

    public void setRetailValue(BigDecimal retailValue) {
        this.retailValue = retailValue;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getProcessingFeePercent() {
        return processingFeePercent;
    }

    public void setProcessingFeePercent(BigDecimal processingFeePercent) {
        this.processingFeePercent = processingFeePercent;
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

    public BigInteger getMaxInventory() {
        return maxInventory;
    }

    public void setMaxInventory(BigInteger maxInventory) {
        this.maxInventory = maxInventory;
    }

    public BigInteger getMaximunPerUser() {
        return maximumPerUser;
    }

    public void setMaximunPerUser(BigInteger maximunPerUser) {
        this.maximumPerUser = maximunPerUser;
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

    public BigInteger getStatusChangedBy() {
        return statusChangedBy;
    }

    public void setStatusChangedBy(BigInteger statusChangedBy) {
        this.statusChangedBy = statusChangedBy;
    }

    public Date getStatusChangedDate() {
        return statusChangedDate;
    }

    public void setStatusChangedDate(Date statusChangedDate) {
        this.statusChangedDate = statusChangedDate;
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

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public BigInteger getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BigInteger createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public BigInteger getPurchaseCreditsReward() {
        return purchaseCreditsReward;
    }

    public void setPurchaseCreditsReward(BigInteger purchaseCreditsReward) {
        this.purchaseCreditsReward = purchaseCreditsReward;
    }

    public BigInteger getUnlockingCredits() {
        return unlockingCredits;
    }

    public void setUnlockingCredits(BigInteger unlockingCredits) {
        this.unlockingCredits = unlockingCredits;
    }

    public BigInteger getExternalId() {
        return externalId;
    }

    public void setExternalId(BigInteger externalId) {
        this.externalId = externalId;
    }

}
