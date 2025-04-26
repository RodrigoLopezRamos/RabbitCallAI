package com.analia.common.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * The persistent class for the CARD database table.
 */
@Entity
@Table(name = "SOURCE")
@NamedQuery(name = "Source.findAll", query = "SELECT s FROM Source s")
public class Source extends AnaliaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "sourcetype_id")
    private BigInteger sourcetypeId;
    @Column(name = "user_id")
    private BigInteger userId;
    @Column(name = "holder_name")
    private String holderName;
    private String nickname;
    private String token;
    @Column(name = "masked_card_number")
    private String maskedCardNumber;
    @Column(name = "expiry_year")
    private int expiryYear;
    @Column(name = "default_card")
    private boolean defaultCard;
    @Column(name = "expiry_month")
    private int expiryMonth;
    private boolean disabled;
    private Timestamp timestamp;



    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }
 

    public BigInteger getSourcetypeId() {
        return sourcetypeId;
    }

    public void setSourcetypeId(BigInteger sourcetypeId) {
        this.sourcetypeId = sourcetypeId;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMaskedCardNumber() {
        return maskedCardNumber;
    }

    public void setMaskedCardNumber(String maskedCardNumber) {
        this.maskedCardNumber = maskedCardNumber;
    }

    public int getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(int expiryYear) {
        this.expiryYear = expiryYear;
    }

    public boolean isDefaultCard() {
        return defaultCard;
    }

    public void setDefaultCard(boolean defaultCard) {
        this.defaultCard = defaultCard;
    }

    public int getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(int expiryMonth) {
        this.expiryMonth = expiryMonth;
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
}