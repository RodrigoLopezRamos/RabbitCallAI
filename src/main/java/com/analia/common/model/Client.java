package com.analia.common.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * The persistent class for the CLIENT database table.
 */
@Entity
@Table(name = "CLIENT_APP")
@NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c")
@NamedQuery(name = "checkIfClientIsEligible",
        query = "select c from Client c where(c.clientCode=:code)")
public class Client extends AnaliaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    private static final long serialVersionUID = 1L;

    @Column(name = "vendor_id")
    private BigInteger vendorId;
    @Column(name = "platform_id")
    private BigInteger platformId;
    @Column(name = "upgrade_link")
    private String upgradeLink;
    @Column(name = "client_code")
    private String clientCode;

    private boolean disabled;
    private Timestamp timestamp;

    public Client() {
        super();
    }


 


    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }


    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getUpgradeLink() {
        return this.upgradeLink;
    }

    public void setUpgradeLink(String upgradeLink) {
        this.upgradeLink = upgradeLink;
    }

    public BigInteger getVendorId() {
        return vendorId;
    }

    public void setVendorId(BigInteger vendorId) {
        this.vendorId = vendorId;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public BigInteger getPlatformId() {
        return platformId;
    }

    public void setPlatformId(BigInteger platformId) {
        this.platformId = platformId;
    }
}