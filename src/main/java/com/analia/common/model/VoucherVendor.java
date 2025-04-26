package com.analia.common.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "VOUCHER_VENDOR")
public class VoucherVendor extends AnaliaEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }


    @Column(name = "vouchercategory_id")
    private BigInteger voucherCategoryId;
    @Column(name = "voucher_id")
    private BigInteger voucherId;
    @Column(name = "vendor_id")
    private BigInteger vendorId;
    @Column(name = "taxgroup_id")
    private BigInteger taxGroupId;
    @Column(name = "voucher_percent")
    private BigDecimal voucherPercent;
    @Column(name = "relative_percentage")
    private BigDecimal relativePercentage;
    private String instructions;
    private boolean verification;
    @Column(name = "status_id")
    private BigInteger statusId;
    @Column(name = "statuschanged_by")
    private BigInteger statusChangedBy;
    @Column(name = "created_by")
    private BigInteger createdBy;
    @Column(name = "created_datetime")
    private Date createdDatetime;
    private Date timestamp;

    public VoucherVendor() {
        super();
    }

   

 


    public BigInteger getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(BigInteger voucherId) {
        this.voucherId = voucherId;
    }

    public BigInteger getVendorId() {
        return vendorId;
    }

    public void setVendorId(BigInteger vendorId) {
        this.vendorId = vendorId;
    }

    public BigInteger getTaxGroupId() {
        return taxGroupId;
    }

    public void setTaxGroupId(BigInteger taxGroupId) {
        this.taxGroupId = taxGroupId;
    }

    public BigDecimal getVoucherPercent() {
        return voucherPercent;
    }

    public void setVoucherPercent(BigDecimal voucherPercent) {
        this.voucherPercent = voucherPercent;
    }

    public BigDecimal getRelativePercentage() {
        return relativePercentage;
    }

    public void setRelativePercentage(BigDecimal relativePercentage) {
        this.relativePercentage = relativePercentage;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public boolean isVerification() {
        return verification;
    }

    public void setVerification(boolean verification) {
        this.verification = verification;
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

    public BigInteger getVoucherCategoryId() {
        return voucherCategoryId;
    }

    public void setVoucherCategoryId(BigInteger voucherCategoryId) {
        this.voucherCategoryId = voucherCategoryId;
    }


}
