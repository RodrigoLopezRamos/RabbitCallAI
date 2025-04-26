package com.analia.common.model;

import com.analia.common.cache.AnaliaCacheableEntity;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "TRADE_ADDITIONAL_DATA")
public class TradeAdditionalData extends AnaliaEntity implements AnaliaCacheableEntity<TradeAdditionalData> {

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
    private String key;
    private String value;
    private boolean disabled;
    private Timestamp timestamp;

    public TradeAdditionalData() {
        super();
    }

    public TradeAdditionalData(BigInteger id, BigInteger tradeId, String key, String value, boolean disabled, Timestamp timestamp) {
        super();
        this.id = id;
        this.tradeId = tradeId;
        this.key = key;
        this.value = value;
        this.disabled = disabled;
        this.timestamp = timestamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public BigInteger getTradeId() {
        return tradeId;
    }

    public void setTradeId(BigInteger tradeId) {
        this.tradeId = tradeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

   

 

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public TradeAdditionalData clone() throws CloneNotSupportedException {
        TradeAdditionalData tradeAdditionalData = new TradeAdditionalData();
        tradeAdditionalData.setId(this.id);
        tradeAdditionalData.setKey(this.key);
        tradeAdditionalData.setValue(this.value);
        tradeAdditionalData.setTradeId(this.tradeId);
        tradeAdditionalData.setTimestamp(this.timestamp);
        tradeAdditionalData.setDisabled(this.disabled);
        return tradeAdditionalData;
    }
}
