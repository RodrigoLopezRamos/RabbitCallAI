package com.analia.common.model;

import com.analia.common.enumeration.PurchaseDetailStatus;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "PURCHASE_DETAIL")
@NamedQuery(name = "PurchaseDetail.findAll", query = "SELECT p FROM PurchaseDetail p")
public class PurchaseDetail extends AnaliaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "purchase_id")
    private BigInteger purchaseId;

    @Column(name = "vouchervendor_id")
    private BigInteger voucherVendorId;

    @Column(name = "taxgroup_id")
    private BigInteger taxGroupId;

    @Column(name = "voucherfile_id")
    private BigInteger voucherfileId;

    private String voucher;

    @Column(name = "relative_percentage")
    private BigDecimal relativePercentage;

    @Column(name = "fees_paid")
    private BigDecimal feesPaid;

    private boolean verified;
    @Temporal(TemporalType.TIMESTAMP)

    @Column(name = "created_datetime")
    private Date createDatetime;

    @Column(name = "redeemedvendorlocation_id")
    private BigInteger redeemedVendorLocationId;

    @Column(name = "redeemed_by")
    private BigInteger redeemedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "redeemed_datetime")

    private Date redeemedDatetime;
    private Timestamp timestamp;

    @Transient
    private PurchaseDetailStatus purchaseDetailStatus;


    public PurchaseDetail() {
        super();
    }

    public PurchaseDetail(BigInteger id, Date createDatetime, BigInteger voucherfileId, BigDecimal feesPaid, BigInteger purchaseId,
                          BigInteger redeemedBy, Date redeemedDatetime, BigDecimal relativePercentage, BigInteger taxGroupId, Timestamp timestamp,
                          BigInteger voucherVendorId, boolean verified, String voucher) {
        super();
        this.id = id;
        this.createDatetime = createDatetime;
        this.voucherfileId = voucherfileId;
        this.feesPaid = feesPaid;
        this.purchaseId = purchaseId;
        this.redeemedBy = redeemedBy;
        this.redeemedDatetime = redeemedDatetime;
        this.relativePercentage = relativePercentage;
        this.taxGroupId = taxGroupId;
        this.timestamp = timestamp;
        this.voucherVendorId = voucherVendorId;
        this.verified = verified;
        this.voucher = voucher;
    }



    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Date getCreateDatetime() {
        return this.createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public BigDecimal getFeesPaid() {
        return this.feesPaid;
    }

    public void setFeesPaid(BigDecimal feesPaid) {
        this.feesPaid = feesPaid;
    }

    public BigInteger getPurchaseId() {
        return this.purchaseId;
    }

    public void setPurchaseId(BigInteger purchaseId) {
        this.purchaseId = purchaseId;
    }

    public BigInteger getRedeemedBy() {
        return this.redeemedBy;
    }

    public void setRedeemedBy(BigInteger redeemedBy) {
        this.redeemedBy = redeemedBy;
    }

    public Date getRedeemedDatetime() {
        return this.redeemedDatetime;
    }

    public void setRedeemedDatetime(Date redeemedDatetime) {
        this.redeemedDatetime = redeemedDatetime;
    }

    public BigDecimal getRelativePercentage() {
        return this.relativePercentage;
    }

    public void setRelativePercentage(BigDecimal relativePercentage) {
        this.relativePercentage = relativePercentage;
    }


    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public BigInteger getVoucherVendorId() {
        return this.voucherVendorId;
    }

    public void setVoucherVendorId(BigInteger tradeVendorId) {
        this.voucherVendorId = tradeVendorId;
    }

    public boolean getVerified() {
        return this.verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getVoucher() {
        return this.voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public BigInteger getRedeemedVendorLocationId() {
        return redeemedVendorLocationId;
    }

    public void setRedeemedVendorLocationId(BigInteger redeemedVendorLocationId) {
        this.redeemedVendorLocationId = redeemedVendorLocationId;
    }

    public PurchaseDetailStatus getPurchaseDetailStatus() {
        return purchaseDetailStatus;
    }

    public void setPurchaseDetailStatus(PurchaseDetailStatus purchaseDetailStatus) {
        this.purchaseDetailStatus = purchaseDetailStatus;
    }

    public BigInteger getTaxGroupId() {
        return taxGroupId;
    }

    public void setTaxGroupId(BigInteger taxGroupId) {
        this.taxGroupId = taxGroupId;
    }

    public BigInteger getVoucherfileId() {
        return voucherfileId;
    }

    public void setVoucherfileId(BigInteger voucherfileId) {
        this.voucherfileId = voucherfileId;
    }

    public void setDefaultLocation(BigInteger id2) {

    }

}