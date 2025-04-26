package com.analia.common.model;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "SHOPPING_CART")
public class ShoppingCart extends AnaliaEntity {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "user_id")
    private BigInteger userId;
    @Column(name = "voucher_id")
    private BigInteger voucherId;

    @Column(name = "quantity")
    private BigInteger quantity;
    @Column(name = "timestamp")
    private Timestamp timestamp;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }



    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }
 

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public BigInteger getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(BigInteger voucherId) {
        this.voucherId = voucherId;
    }

}
