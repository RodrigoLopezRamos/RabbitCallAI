package com.analia.common.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * The persistent class for the TAX_RANGE database table.
 */
@Entity
@Table(name = "TAX_RANGE")
@NamedQuery(name = "TaxRange.findAll", query = "SELECT t FROM TaxRange t")
public class TaxRange extends AnaliaEntity implements Serializable {

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

    @Column(name = "tax_id")
    private BigInteger taxId;
    private String description;
    @Column(name = "equal_or_higher_than")
    private BigDecimal equalOrHigherThan;
    @Column(name = "lower_than")
    private BigDecimal lowerThan;
    private BigDecimal percentage;
    private Timestamp timestamp;

    public TaxRange() {
        super();
    }

    public TaxRange(BigInteger id, String description, BigDecimal equalOrHigherThan, BigDecimal lowerThan,
                    BigDecimal percentage, BigInteger taxId, Timestamp timestamp) {
        super();
        this.id = id;
        this.description = description;
        this.equalOrHigherThan = equalOrHigherThan;
        this.lowerThan = lowerThan;
        this.percentage = percentage;
        this.taxId = taxId;
        this.timestamp = timestamp;
    }


 

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getEqualOrHigherThan() {
        return this.equalOrHigherThan;
    }

    public void setEqualOrHigherThan(BigDecimal equalOrHigherThan) {
        this.equalOrHigherThan = equalOrHigherThan;
    }

    public BigDecimal getLowerThan() {
        return this.lowerThan;
    }

    public void setLowerThan(BigDecimal lowerThan) {
        this.lowerThan = lowerThan;
    }

    public BigDecimal getPercentage() {
        return this.percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public BigInteger getTaxId() {
        return this.taxId;
    }

    public void setTaxId(BigInteger taxId) {
        this.taxId = taxId;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}