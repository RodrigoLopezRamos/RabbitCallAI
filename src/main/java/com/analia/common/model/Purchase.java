package com.analia.common.model;

import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

@NamedQuery( name = "getSourceTypeByName", query = "select st from SourceType st where(st.name = :name) and (st.active = true)")
@NamedQuery( name = "getSourcesByUserId", query = "select s from Source s , SourceType st where (s.userId =:userId) and (s.disabled = false) and (s.sourcetypeId = st.id)")
@NamedQuery( name = "getSourceByIdWithUserId", query = "select s from Source s where (s.userId =:userId) and (s.disabled = false) and (s.id = :sourceId)")
@NamedQuery( name = "getDefaultSourcesByIdAndUserId", query = "select s from Source s where (s.userId =:userId) and (s.disabled = false) and (s.defaultCard =true)")
@NamedQuery( name = "setDefaultSource", query = "update Source s set s.defaultCard = true where (s.id =:sourceId) and (s.userId =:userId)")
@NamedQuery( name = "setAllUserCardToBeUnDefault", query = "update Source s set s.defaultCard = false where (s.userId =:userId)")
@NamedQuery( name = "getRemainingAllowanceVoucherByUser", query = "select sum(p.totalVouchers) from Purchase p where (p.userId =:userId) and (p.vouchervendorId = :vouchervendorId)")
@NamedQuery( name = "getVoucherVendorTaxGroupRange", query = "select vvt from VoucherVendorTaxGroupView vvt where (vvt.voucherId =:voucherId) and (vvt.equalOrHigherThan <= :amount) and (vvt.lowerThan >:amount)")
@NamedQuery( name = "getPurchaseListByUserIdAndPurchaseId", query = "select v from PurchaseDetailView v where (v.userId = :userId) and (v.purchaseId = :purchaseId) order by v.createdDatetime DESC")
@NamedQuery( name = "getPurchaseListByUser", query = "select p from PurchaseDetailView p where (p.userId = :userId) order by p.createdDatetime DESC")
@NamedQuery( name = "getPurchaseDetailByIdAndUser", query = "select p from PurchaseDetailView p where (p.id = :purchaseDetailId) and (p.userId = :userId)")
@NamedQuery( name = "getVendorTaxGroupRangeForPurchase", query = "select vvt from VoucherVendorTaxGroupView vvt where (vvt.voucherId =:voucherId) and (vvt.disabled = false) and (vvt.statusId = 100) and (vvt.maxInventory - vvt.sold >= :quantity)")
@NamedQuery( name = "getAllSourceTypes", query = "select st from SourceType st where (st.active = true)")
@NamedQuery( name = "getAllPurchaseDetailsForVendorLocationVoucherId", query = "select pdv from PurchaseDetailView pdv where (pdv.vendorLocationVoucherId = :vendorLocationVoucherId)")
@NamedQuery( name = "getListPurchaseDetailViewForVendorId", query = "select pdv from PurchaseDetailView pdv, VoucherVendor vv where (pdv.createdDatetime between :startDate and :endDate) and (vv.id = pdv.vouchervendorId) and (vv.vendorId = :vendorId)")
@NamedQuery( name = "getAllLocationByPurchaseDetailId", query = "select vmml from VendorLocationDetailView vmml, VendorLocationVoucher mlp, PurchaseDetail pd where (pd.id = :purchaseDetailId) and (pd.voucherVendorId = mlp.voucherVendorId) and (mlp.vendorLocationId = vmml.vendorlocationId)")
@NamedQuery( name = "getListPurchaseDetailView", query = "select pdv from PurchaseDetailView pdv where (pdv.createdDatetime between :startDate and :endDate) order by pdv.createdDatetime DESC")
@NamedQuery( name = "getPurchaseDetailForVoucher", query = "select pd from PurchaseDetail pd where (pd.voucher =:voucher)")
@NamedQuery( name = "getListPurchaseDetailForVoucherId", query = "select pd from PurchaseDetail pd , Purchase p where (pd.purchaseId =p.id) and (p.vouchervendorId = :vouchervendorId)")

/**
 * The persistent class for the PURCHASE database table.
 * <p>
 * <p>
 * <p>
 * MariaDB [analia]> DESCRIBE PURCHASE;
 * +------------------------+--------------+------+-----+-------------------+-----------------------------+
 * | Field                  | Type         | Null | Key | Default           | Extra                       |
 * +------------------------+--------------+------+-----+-------------------+-----------------------------+
 * | id                     | int(11)      | NO   | PRI | NULL              | auto_increment              |
 * | user_id                | int(11)      | NO   | MUL | NULL              |                             |
 * | source_id              | int(11)      | NO   | MUL | NULL              |                             |
 * | directory_id           | int(11)      | NO   | MUL | NULL              |                             |
 * | client_id              | int(11)      | NO   | MUL | NULL              |                             |
 * | vouchervendor_id       | int(11)      | NO   | MUL | NULL              |                             |
 * | total_vouchers         | int(11)      | NO   |     | NULL              |                             |
 * | amount_paid            | decimal(6,2) | NO   |     | NULL              |                             |
 * | total_tax_paid         | decimal(6,2) | NO   |     | NULL              |                             |
 * | processing_fee_percent | decimal(6,2) | NO   |     | NULL              |                             |
 * | vouchers_redeemed      | int(11)      | NO   |     | NULL              |                             |
 * | payment_reference      | varchar(64)  | YES  |     | NULL              |                             |
 * | credits_paid           | int(11)      | NO   |     | NULL              |                             |
 * | credits_earned         | int(11)      | NO   |     | NULL              |                             |
 * | refunded_datetime      | datetime     | YES  |     | NULL              |                             |
 * | refunded_by            | int(11)      | YES  | MUL | NULL              |                             |
 * | created_datetime       | datetime     | NO   |     | NULL              |                             |
 * | timestamp              | timestamp    | NO   |     | CURRENT_TIMESTAMP | on update CURRENT_TIMESTAMP |
 * +------------------------+--------------+------+-----+-------------------+-----------------------------+
 */
@Entity
@Table(name = "PURCHASE")
@NamedQuery(name = "Purchase.findAll", query = "SELECT p FROM Purchase p")
public class Purchase extends AnaliaEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "user_id")
    private BigInteger userId;
    @Column(name = "source_id")
    private BigInteger sourceId;
    @Column(name = "directory_id")
    private BigInteger directoryId;
    @Column(name = "vouchervendor_id")
    private BigInteger vouchervendorId;
    @Column(name = "client_id")
    private BigInteger clientId;
    @Column(name = "amount_paid")
    private BigDecimal amountPaid;
    @Column(name = "total_tax_paid")
    private BigDecimal totalTaxPaid;
    @Column(name = "processing_fee_percent")
    private BigDecimal processingFeePercent;
    @Column(name = "total_vouchers")
    private BigInteger totalVouchers;
    @Column(name = "vouchers_redeemed")
    private BigInteger vouchersRedeemed;
    @Column(name = "payment_reference")
    private String paymentReference;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "refunded_datetime")
    private Date refundedCreatedatetime;
    @Column(name = "refunded_by")
    private BigInteger refundedBy;
    @Column(name = "credits_paid")
    private BigInteger creditsPaid;
    @Column(name = "credits_earned")
    private BigInteger creditsEarned;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_datetime")
    private Date createdDatetime;
    @Column(name = "timestamp")
    private Timestamp timestamp;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }



    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }
 

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getSourceId() {
        return sourceId;
    }

    public void setSourceId(BigInteger sourceId) {
        this.sourceId = sourceId;
    }

    public BigInteger getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(BigInteger directoryId) {
        this.directoryId = directoryId;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public BigDecimal getTotalTaxPaid() {
        return totalTaxPaid;
    }

    public void setTotalTaxPaid(BigDecimal totalTaxPaid) {
        this.totalTaxPaid = totalTaxPaid;
    }

    public BigDecimal getProcessingFeePercent() {
        return processingFeePercent;
    }

    public void setProcessingFeePercent(BigDecimal processingFeePercent) {
        this.processingFeePercent = processingFeePercent;
    }

    public BigInteger getTotalVouchers() {
        return totalVouchers;
    }

    public void setTotalVouchers(BigInteger totalVouchers) {
        this.totalVouchers = totalVouchers;
    }

    public BigInteger getVouchersRedeemed() {
        return vouchersRedeemed;
    }

    public void setVouchersRedeemed(BigInteger vouchersRedeemed) {
        this.vouchersRedeemed = vouchersRedeemed;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public Date getRefundedCreatedatetime() {
        return refundedCreatedatetime;
    }

    public void setRefundedCreatedatetime(Date refundedCreatedatetime) {
        this.refundedCreatedatetime = refundedCreatedatetime;
    }

    public BigInteger getRefundedBy() {
        return refundedBy;
    }

    public void setRefundedBy(BigInteger refundedBy) {
        this.refundedBy = refundedBy;
    }

    public BigInteger getCreditsPaid() {
        return creditsPaid;
    }

    public void setCreditsPaid(BigInteger creditsPaid) {
        this.creditsPaid = creditsPaid;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public BigInteger getCreditsEarned() {
        return creditsEarned;
    }

    public void setCreditsEarned(BigInteger creditsEarned) {
        this.creditsEarned = creditsEarned;
    }

    public BigInteger getVouchervendorId() {
        return vouchervendorId;
    }

    public void setVouchervendorId(BigInteger vouchervendorId) {
        this.vouchervendorId = vouchervendorId;
    }

    public BigInteger getClientId() {
        return clientId;
    }

    public void setClientId(BigInteger clientId) {
        this.clientId = clientId;
    }
}