package com.analia.common.model;

import jakarta.persistence.*;

import java.math.BigInteger;

@Entity
@Table(name = "VOUCHER_ACTIVITY")
public class VoucherActivity extends AnaliaEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    @Column(name = "voucher_id")
    private BigInteger voucherId;
    @Column(name = "vouchervendor_id")
    private BigInteger voucherVendorId;
    @Column(name = "status_id")
    private BigInteger statusId;
    @Column(name = "statuschanged_by")
    private BigInteger statusChangedBy;

    public VoucherActivity() {
        super();
    }

    public VoucherActivity(BigInteger id, BigInteger voucherId, BigInteger voucherVendorId, BigInteger statusId, BigInteger statusChangedBy) {
        super();
        this.id = id;
        this.voucherId = voucherId;
        this.voucherVendorId = voucherVendorId;
        this.statusId = statusId;
        this.statusChangedBy = statusChangedBy;
    }



    public BigInteger getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(BigInteger voucherId) {
        this.voucherId = voucherId;
    }

    public BigInteger getVoucherVendorId() {
        return voucherVendorId;
    }

    public void setVoucherVendorId(BigInteger voucherVendorId) {
        this.voucherVendorId = voucherVendorId;
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

}
