package com.analia.common.model;

import com.analia.common.cache.AnaliaCacheableEntity;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "TRADE_TAG")
public class TradeTag extends AnaliaEntity implements AnaliaCacheableEntity<TradeTag> {

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

    @Column(name = "trade_id")
    private BigInteger tradeId;
    @Column(name = "tag_id")
    private BigInteger tagId;
    private boolean disabled;
    private Timestamp timestamp;

    public TradeTag() {
        super();
    }

    public TradeTag(BigInteger id, BigInteger tradeId, BigInteger tagId, boolean disabled, Timestamp timestamp) {
        super();
        this.id = id;
        this.tradeId = tradeId;
        this.tagId = tagId;
        this.disabled = disabled;
        this.timestamp = timestamp;
    }

    public BigInteger getTradeId() {
        return tradeId;
    }

    public void setTradeId(BigInteger tradeId) {
        this.tradeId = tradeId;
    }

    public BigInteger getTagId() {
        return tagId;
    }

    public void setTagId(BigInteger tagId) {
        this.tagId = tagId;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

   

 

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }


    public TradeTag clone() {
        TradeTag tradeTag = new TradeTag();
        tradeTag.setId(this.id);
        tradeTag.setTagId(this.tagId);
        tradeTag.setTradeId(this.tradeId);
        tradeTag.setTimestamp(timestamp);
        return tradeTag;
    }

}
