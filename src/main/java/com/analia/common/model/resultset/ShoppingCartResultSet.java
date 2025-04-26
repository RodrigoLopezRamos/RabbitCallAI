package com.analia.common.model.resultset;

import com.analia.common.model.resultset.view.VoucherDetailView;
import com.analia.common.model.resultset.view.VoucherVendorTaxGroupView;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ShoppingCartResultSet {
    private VoucherDetailView voucherDetailView;
    private VoucherVendorTaxGroupView voucherVendorTaxGroupView;
    private BigInteger quantity;
    private BigInteger remainingInventory;
    private BigInteger remainingAllowance;
    private BigInteger creditsToPay;

    private BigDecimal price = new BigDecimal(0);
    private BigDecimal subTotal = new BigDecimal(0);
    private BigDecimal taxToPaid = new BigDecimal(0);
    private BigDecimal total = new BigDecimal(0);
    private BigDecimal grandSubTotal = new BigDecimal(0);
    private BigDecimal grandTaxToPaid = new BigDecimal(0);
    private BigDecimal grandTotal = new BigDecimal(0);


    public VoucherVendorTaxGroupView getVoucherVendorTaxGroupView() {
        return voucherVendorTaxGroupView;
    }

    public void setVoucherVendorTaxGroupView(VoucherVendorTaxGroupView voucherVendorTaxGroupView) {
        this.voucherVendorTaxGroupView = voucherVendorTaxGroupView;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    public VoucherDetailView getVoucherDetailView() {
        return voucherDetailView;
    }

    public void setVoucherDetailView(VoucherDetailView voucherDetailView) {
        this.voucherDetailView = voucherDetailView;
    }

    public BigInteger getRemainingInventory() {
        return remainingInventory;
    }

    public void setRemainingInventory(BigInteger remainingInventory) {
        this.remainingInventory = remainingInventory;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getTaxToPaid() {
        return taxToPaid;
    }

    public void setTaxToPaid(BigDecimal taxToPaid) {
        this.taxToPaid = taxToPaid;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getGrandSubTotal() {
        return grandSubTotal;
    }

    public void setGrandSubTotal(BigDecimal grandSubTotal) {
        this.grandSubTotal = grandSubTotal;
    }

    public BigDecimal getGrandTaxToPaid() {
        return grandTaxToPaid;
    }

    public void setGrandTaxToPaid(BigDecimal grandTaxToPaid) {
        this.grandTaxToPaid = grandTaxToPaid;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public BigInteger getRemainingAllowance() {
        return remainingAllowance;
    }

    public void setRemainingAllowance(BigInteger remainingAllowance) {
        this.remainingAllowance = remainingAllowance;
    }

    public BigInteger getCreditsToPay() {
        return creditsToPay;
    }

    public void setCreditsToPay(BigInteger creditsToPay) {
        this.creditsToPay = creditsToPay;
    }


}
