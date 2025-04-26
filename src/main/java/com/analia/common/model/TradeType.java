package com.analia.common.model;

import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "TRADE_TYPE")
public class TradeType extends AnaliaEntity {

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

    @Column(name = "name")
    private String name;
    @Column(name = "disabled")
    private boolean disabled;
    @Column(name = "timestamp")
    private Date timestamp;

    public TradeType() {
        super();
    }

    public TradeType(BigInteger id, String name, boolean disabled, Date timestamp) {
        super();
        this.id = id;
        this.name = name;
        this.disabled = disabled;
        this.timestamp = timestamp;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
