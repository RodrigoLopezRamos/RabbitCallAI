package com.analia.common.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * The persistent class for the LANGUAGE database table.
 */
@Entity
@Table(name = "LANGUAGE")
@NamedQuery(name = "Language.findAll", query = "SELECT l FROM Language l")
public class Language extends AnaliaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    private String code;

    private byte enabled;

    @Column(name = "localized_name")
    private String localizedName;

    @Column(name = "name_key")
    private String nameKey;

    private Timestamp timestamp;

    public Language() {
        super();
    }

    public Language(String code, byte enabled, String localizedName, String nameKey, Timestamp timestamp) {
        super();
        this.code = code;
        this.enabled = enabled;
        this.localizedName = localizedName;
        this.nameKey = nameKey;
        this.timestamp = timestamp;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte getEnabled() {
        return this.enabled;
    }

    public void setEnabled(byte enabled) {
        this.enabled = enabled;
    }

    public String getLocalizedName() {
        return this.localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public String getNameKey() {
        return this.nameKey;
    }

    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

}