package com.analia.common.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The persistent class for the REVIEW database table.
 */
@Entity
@Table(name = "REVIEW")
@NamedQuery(name = "Review.findAll", query = "SELECT r FROM Review r")
public class Review extends AnaliaEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "trade_id")
    private BigInteger tradeId;
    @Column(name = "voucher_id")
    private BigInteger voucherId;
    @Column(name = "secuence_number")
    private int secuenceNumber;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "score_given")
    private int scoreGiven;
    @Lob
    private String text;
    @Column(name = "created_by")
    private BigInteger createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_datetime")
    private Date createDatetime;
    private byte disabled;
    private Timestamp timestamp;

    public Review() {
        super();
    }

    public Review(BigInteger id, Date createDatetime, BigInteger createdBy, Date date, byte disabled, int gradeGiven, BigInteger voucherId,
                  int secuenceNumber, int scoreGiven, String text, Timestamp timestamp, BigInteger tradeId) {
        super();
        this.id = id;
        this.createDatetime = createDatetime;
        this.createdBy = createdBy;
        this.date = date;
        this.disabled = disabled;
        this.voucherId = voucherId;
        this.secuenceNumber = secuenceNumber;
        this.scoreGiven = scoreGiven;
        this.text = text;
        this.timestamp = timestamp;
        this.tradeId = tradeId;
    }


 

    public Date getCreateDatetime() {
        return this.createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public BigInteger getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(BigInteger createdBy) {
        this.createdBy = createdBy;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public byte getDisabled() {
        return this.disabled;
    }

    public void setDisabled(byte disabled) {
        this.disabled = disabled;
    }

    public BigInteger getVoucherId() {
        return this.voucherId;
    }

    public void setVoucherId(BigInteger voucherId) {
        this.voucherId = voucherId;
    }

    public int getSecuenceNumber() {
        return this.secuenceNumber;
    }

    public void setSecuenceNumber(int secuenceNumber) {
        this.secuenceNumber = secuenceNumber;
    }

    public int getScoreGiven() {
        return this.scoreGiven;
    }

    public void setScoreGiven(int scoreGiven) {
        this.scoreGiven = scoreGiven;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public BigInteger getTradeId() {
        return this.tradeId;
    }

    public void setTradeId(BigInteger tradeId) {
        this.tradeId = tradeId;
    }


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }
}