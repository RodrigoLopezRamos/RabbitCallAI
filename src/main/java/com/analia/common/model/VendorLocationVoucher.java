package com.analia.common.model;

import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "VENDOR_LOCATION_VOUCHER")
public class VendorLocationVoucher extends AnaliaEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    @Column(name = "vouchervendor_id")
    private BigInteger voucherVendorId;
    @Column(name = "vendorlocation_id")
    private BigInteger vendorLocationId;
    @Column(name = "created_by")
    private BigInteger createdBy;
    private boolean disabled;
    private Date timestamp;

    public VendorLocationVoucher() {
        super();
    }

   

 

    public BigInteger getVoucherVendorId() {
        return voucherVendorId;
    }

    public void setVoucherVendorId(BigInteger voucherVendorId) {
        this.voucherVendorId = voucherVendorId;
    }

    public BigInteger getVendorLocationId() {
        return vendorLocationId;
    }

    public void setVendorLocationId(BigInteger vendorLocationId) {
        this.vendorLocationId = vendorLocationId;
    }

    public BigInteger getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BigInteger createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
