package com.analia.common.model;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "USER_DEVICE")
public class UserDevice extends AnaliaEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    @Column(name = "user_id")
    private BigInteger userId;
    @Column(name = "device_id")
    private BigInteger deviceId;
    private Timestamp timestamp;

    public UserDevice() {
        super();
    }

    public UserDevice(BigInteger id, BigInteger userId, BigInteger deviceId, Timestamp timestamp) {
        super();
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.timestamp = timestamp;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(BigInteger deviceId) {
        this.deviceId = deviceId;
    }


   

 

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
