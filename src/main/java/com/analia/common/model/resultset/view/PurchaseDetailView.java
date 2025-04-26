package com.analia.common.model.resultset.view;

import com.analia.common.model.AnaliaEntity;

import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;


@Entity
@Table(name = "PURCHASE_DETAIL_VIEW")
public class PurchaseDetailView extends AnaliaEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @Column(name = "amount_paid")
    private BigDecimal amountPaid;

    @Column(name = "voucher_percent")
    private BigDecimal voucherPercent;

    @Column(name = "source_id")
    private int cardId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_datetime")
    private Date createdDatetime;

    @Column(name = "credits_earned")
    private int creditsEarned;

    @Column(name = "credits_paid")
    private int creditsPaid;

    @Column(name = "voucher")
    private String digitalVoucher;

    @Column(name = "redeemable_by")
    private Integer redeemableBy;

    @Column(name = "path")
    private String path;

    @Column(name = "terms_url")
    private String terms;

    @Column(name = "instructions")
    private String instructions;

    @Column(name = "fine_print")
    private String finePrint;

    @Column(name = "title")
    private String voucherTitle;

    @Column(name = "trade_id")
    private int tradeId;

    @Column(name = "processing_fee_percent")
    private BigDecimal processingFeePercent;

    @Column(name = "payment_reference")
    private String paymentReferenceNumber;

    @Column(name = "voucherfile_id")
    private int voucherfileId;


    @Column(name = "sold")
    private int sold;

    @Column(name = "vouchervendor_id")
    private int vouchervendorId;

    private BigDecimal price;

    @Column(name = "purchase_id")
    private BigInteger purchaseId;

    @Column(name = "redeemed_by")
    private Integer redeemedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "redeemed_datetime")
    private Date redeemedDatetime;

    @Column(name = "redeemedvendorlocation_id")
    private Integer redeemedLocationvoucherId;

//    @Column(name = "address1")
//    private String address1;

//    @Column(name = "city")
//    private String city;
//
//    @Column(name = "postal_code")
//    private String postalCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "refunded_datetime")
    private Date refundedDatetime;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "taxgroup_id")
    private int taxgroupId;

    @Column(name = "total_tax_paid")
    private BigDecimal totalTaxPaid;

    @Column(name = "total_vouchers")
    private int totalVouchers;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "voucher_directory_id")
    private BigInteger voucherDirectoryId;


    @Column(name = "vouchers_redeemed")
    private int vouchersRedeemed;

    @Column(name = "relative_percentage")
    private BigDecimal relativePercentage;

    @Column(name = "vendorlocation_id")
    private int vendorLocationId;

    @Id
    @Column(name = "voucher_vendor_location_id")
    private BigInteger vendorLocationVoucherId;


    @Column(name = "voucher_provider")
    private String voucherProvider;

    @Column(name = "vouchertype_id")
    private int voucherTypeId;

//    @Column(name = "zone_id")
//    private int zoneId;

//    @Column(name = "timezone")
//    private String timezone;

    @Transient
    private VendorLocationDetailView vendorLocationDetailView;

    @Column(name = "fees_paid")
    private BigDecimal feesPaid;



    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public BigDecimal getVoucherPercent() {
        return voucherPercent;
    }

    public void setVoucherPercent(BigDecimal voucherPercent) {
        this.voucherPercent = voucherPercent;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public int getCreditsEarned() {
        return creditsEarned;
    }

    public void setCreditsEarned(int creditsEarned) {
        this.creditsEarned = creditsEarned;
    }

    public int getCreditsPaid() {
        return creditsPaid;
    }

    public void setCreditsPaid(int creditsPaid) {
        this.creditsPaid = creditsPaid;
    }

    public String getDigitalVoucher() {
        return digitalVoucher;
    }

    public void setDigitalVoucher(String digitalVoucher) {
        this.digitalVoucher = digitalVoucher;
    }

    public Integer getRedeemableBy() {
        return redeemableBy;
    }

    public void setRedeemableBy(Integer redeemableBy) {
        this.redeemableBy = redeemableBy;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getFinePrint() {
        return finePrint;
    }

    public void setFinePrint(String finePrint) {
        this.finePrint = finePrint;
    }

    public String getVoucherTitle() {
        return voucherTitle;
    }

    public void setVoucherTitle(String voucherTitle) {
        this.voucherTitle = voucherTitle;
    }

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public BigDecimal getProcessingFeePercent() {
        return processingFeePercent;
    }

    public void setProcessingFeePercent(BigDecimal processingFeePercent) {
        this.processingFeePercent = processingFeePercent;
    }

    public String getPaymentReferenceNumber() {
        return paymentReferenceNumber;
    }

    public void setPaymentReferenceNumber(String paymentReferenceNumber) {
        this.paymentReferenceNumber = paymentReferenceNumber;
    }

    public int getVoucherfileId() {
        return voucherfileId;
    }

    public void setVoucherfileId(int voucherfileId) {
        this.voucherfileId = voucherfileId;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getVouchervendorId() {
        return vouchervendorId;
    }

    public void setVouchervendorId(int vouchervendorId) {
        this.vouchervendorId = vouchervendorId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigInteger getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(BigInteger purchaseId) {
        this.purchaseId = purchaseId;
    }


    public Integer getRedeemedBy() {
        return redeemedBy;
    }

    public void setRedeemedBy(Integer redeemedBy) {
        this.redeemedBy = redeemedBy;
    }

    public Date getRedeemedDatetime() {
        return redeemedDatetime;
    }

    public void setRedeemedDatetime(Date redeemedDatetime) {
        this.redeemedDatetime = redeemedDatetime;
    }

    public Integer getRedeemedLocationvoucherId() {
        return redeemedLocationvoucherId;
    }

    public void setRedeemedLocationvoucherId(Integer redeemedLocationvoucherId) {
        this.redeemedLocationvoucherId = redeemedLocationvoucherId;
    }

//    public String getAddress1() {
//        return address1;
//    }
//
//    public void setAddress1(String address1) {
//        this.address1 = address1;
//    }

//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getPostalCode() {
//        return postalCode;
//    }
//
//    public void setPostalCode(String postalCode) {
//        this.postalCode = postalCode;
//    }

    public Date getRefundedDatetime() {
        return refundedDatetime;
    }

    public void setRefundedDatetime(Date refundedDatetime) {
        this.refundedDatetime = refundedDatetime;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public int getTaxgroupId() {
        return taxgroupId;
    }

    public void setTaxgroupId(int taxgroupId) {
        this.taxgroupId = taxgroupId;
    }

    public BigDecimal getTotalTaxPaid() {
        return totalTaxPaid;
    }

    public void setTotalTaxPaid(BigDecimal totalTaxPaid) {
        this.totalTaxPaid = totalTaxPaid;
    }

    public int getTotalVouchers() {
        return totalVouchers;
    }

    public void setTotalVouchers(int totalVouchers) {
        this.totalVouchers = totalVouchers;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigInteger getVoucherDirectoryId() {
        return voucherDirectoryId;
    }

    public void setVoucherDirectoryId(BigInteger voucherDirectoryId) {
        this.voucherDirectoryId = voucherDirectoryId;
    }


    public int getVouchersRedeemed() {
        return vouchersRedeemed;
    }

    public void setVouchersRedeemed(int vouchersRedeemed) {
        this.vouchersRedeemed = vouchersRedeemed;
    }

    public BigDecimal getRelativePercentage() {
        return relativePercentage;
    }

    public void setRelativePercentage(BigDecimal relativePercentage) {
        this.relativePercentage = relativePercentage;
    }

    public int getVendorLocationId() {
        return vendorLocationId;
    }

    public void setVendorLocationId(int vendorLocationId) {
        this.vendorLocationId = vendorLocationId;
    }


    public int getVoucherTypeId() {
        return voucherTypeId;
    }

    public void setVoucherTypeId(int voucherTypeId) {
        this.voucherTypeId = voucherTypeId;
    }

//    public int getZoneId() {
//        return zoneId;
//    }
//
//    public void setZoneId(int zoneId) {
//        this.zoneId = zoneId;
//    }

//    public String getTimezone() {
//        return timezone;
//    }
//
//    public void setTimezone(String timezone) {
//        this.timezone = timezone;
//    }

    public VendorLocationDetailView getVendorLocationDetailView() {
        return vendorLocationDetailView;
    }

    public void setVendorLocationDetailView(VendorLocationDetailView vendorLocationDetailView) {
        this.vendorLocationDetailView = vendorLocationDetailView;
    }

    public BigDecimal getFeesPaid() {
        return feesPaid;
    }

    public void setFeesPaid(BigDecimal feesPaid) {
        this.feesPaid = feesPaid;
    }

    public String getFullNameOfUser() {
        return ((this.firstName == null) ? "" : this.firstName) + " " + ((this.lastName == null) ? "" : this.lastName);
    }
//
//   // public String getAddressForRedeemedLocation() {
//        return this.address1 + ", " + this.city;
//    }

    public BigInteger getVendorLocationVoucherId() {
        return vendorLocationVoucherId;
    }

    public void setVendorLocationVoucherId(BigInteger vendorLocationVoucherId) {
        this.vendorLocationVoucherId = vendorLocationVoucherId;
    }


}