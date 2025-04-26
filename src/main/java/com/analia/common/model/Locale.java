package com.analia.common.model;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "LOCALE")
public class Locale extends AnaliaEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;


    @Column(name = "bundle_id")
    private BigInteger bundleId;
    @Column(name = "country_code")
    private String countryCode;
    @Column(name = "language_code")
    private String languageCode;
    @Column(name = "system_default")
    private boolean systemDefault;
    private boolean enabled;
    private Date timestamp;

    public Locale() {
        super();
    }

    public Locale(int BigInteger, BigInteger bundleId, String countryCode, String languageCode, boolean systemDefault, boolean enabled,
                  Date timestamp) {
        super();
        this.id = id;
        this.bundleId = bundleId;
        this.countryCode = countryCode;
        this.languageCode = languageCode;
        this.systemDefault = systemDefault;
        this.enabled = enabled;
        this.timestamp = timestamp;
    }

   

 

    public BigInteger getBundleId() {
        return bundleId;
    }

    public void setBundleId(BigInteger bundleId) {
        this.bundleId = bundleId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public boolean isSystemDefault() {
        return systemDefault;
    }

    public void setSystemDefault(boolean systemDefault) {
        this.systemDefault = systemDefault;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

}
