package com.analia.common.model;

import com.analia.common.util.SHA256Util;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "DEVICE")
public class Device extends AnaliaEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "vendor_id")
    private BigInteger vendorId;

    private String uuid;
    @Column(name = "user_agent")
    private String userAgent;

    private int nonce;
    @Column(name = "external_ip")
    private String externalIp;

    private Timestamp timestamp;

    public Device() {
        super();
    }

    public Device(BigInteger id, String uuid, String userAgent, int nonce, String externalIp, Timestamp timestamp) {
        super();
        this.id = id;
        this.uuid = uuid;
        this.userAgent = userAgent;
        this.nonce = nonce;
        this.externalIp = externalIp;
        this.timestamp = timestamp;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public String getExternalIp() {
        return externalIp;
    }

    public void setExternalIp(String externalIp) {
        this.externalIp = externalIp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isValidNonce(String nonce) {
        return this.generateNonceString().equals(nonce);
    }

    public String generateNonceString() {
        String newNonce = this.getId() + this.uuid + this.nonce;
        return SHA256Util.SHA256(newNonce);
    }

    public BigInteger getVendorId() {
        return vendorId;
    }

    public void setVendorId(BigInteger vendorId) {
        this.vendorId = vendorId;
    }


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

}
