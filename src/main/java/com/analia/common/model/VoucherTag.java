package com.analia.common.model;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "VOUCHER_TAG")
public class VoucherTag extends AnaliaEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    @Column(name = "tag_id")
    private int tagId;
    @Column(name = "voucher_id")
    private int voucherId;
    private Date timestamp;

    public VoucherTag() {
        super();
    }

    public VoucherTag(BigInteger id, int tagId, int voucherId, Date timestamp) {
        super();
        this.id = id;
        this.tagId = tagId;
        this.voucherId = voucherId;
        this.timestamp = timestamp;
    }

   

 

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
