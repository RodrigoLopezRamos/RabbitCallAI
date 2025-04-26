package com.analia.common.model;

import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "VOUCHER_CATEGORY")
public class VoucherCategory extends AnaliaEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    @Column(name = "voucher_id")
    private BigInteger voucherId;
    @Column(name = "category_id")
    private BigInteger categoryId;
    @Column(name = "created_datetime")
    private Date createdDatetime;
    private Date timestamp;

    public VoucherCategory() {
        super();
    }

    public VoucherCategory(BigInteger id, BigInteger voucherId, BigInteger categoryId, Date createdDatetime, Date timestamp) {
        super();
        this.id = id;
        this.voucherId = voucherId;
        this.categoryId = categoryId;
        this.createdDatetime = createdDatetime;
        this.timestamp = timestamp;
    }

   

 

    public BigInteger getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(BigInteger voucherId) {
        this.voucherId = voucherId;
    }

    public BigInteger getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(BigInteger categoryId) {
        this.categoryId = categoryId;
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
}
