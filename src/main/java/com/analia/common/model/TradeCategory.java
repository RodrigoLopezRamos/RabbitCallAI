package com.analia.common.model;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "TRADE_CATEGORY")
public class TradeCategory extends AnaliaEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Column(name = "category_id")
    private BigInteger categoryId;
    @Column(name = "trade_id")
    private BigInteger tradeId;
    @Column(name = "created_datetime")
    private Date createDatetime;
    private Date timestamp;

    public TradeCategory() {
        super();
    }

    public TradeCategory(BigInteger id, BigInteger categoryId, BigInteger tradeIdxs) {
        super();
        this.id = id;
        this.categoryId = categoryId;
        this.tradeId = tradeId;
    }



    public BigInteger getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(BigInteger categoryId) {
        this.categoryId = categoryId;
    }

    public BigInteger getTradeId() {
        return tradeId;
    }

    public void setTradeId(BigInteger tradeId) {
        this.tradeId = tradeId;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}
