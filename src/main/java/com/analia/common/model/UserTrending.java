package com.analia.common.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The persistent class for the BUNDLE database table.
 */
@Entity
@Table(name = "USER_TRENDING")
public class UserTrending extends AnaliaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    @Column(name = "user_id")
    private BigInteger userId;

    @Column(name = "trade_id")
    private BigInteger tradeId;

    @Column(name = "voucher_id")
    private BigInteger voucherId;

    @Column(name = "story_id")
    private BigInteger storyId;

    private boolean favourited;


    @Column(name = "created_datetime")
    private Date createdDateTime;

    private Timestamp timestamp;

   

 

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getTradeId() {
        return tradeId;
    }

    public void setTradeId(BigInteger tradeId) {
        this.tradeId = tradeId;
    }

    public BigInteger getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(BigInteger voucherId) {
        this.voucherId = voucherId;
    }


    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }


    public boolean isFavourited() {
        return favourited;
    }

    public void setFavourited(boolean favourited) {
        this.favourited = favourited;
    }


    public BigInteger getStoryId() {
        return storyId;
    }

    public void setStoryId(BigInteger storyId) {
        this.storyId = storyId;
    }
}