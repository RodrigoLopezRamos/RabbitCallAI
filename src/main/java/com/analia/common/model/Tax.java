package com.analia.common.model;

import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * The persistent class for the TAX database table.
 */
@Entity
@Table(name = "TAX")
@NamedQuery(name = "Tax.findAll", query = "SELECT t FROM Tax t")
public class Tax extends AnaliaEntity implements Serializable {

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


    @Column(name = "taxgroup_id")
    private BigInteger taxgroupId;
    private String description;
    private Timestamp timestamp;

    public Tax() {
        super();
    }

    public Tax(BigInteger id, String description, BigInteger taxgroupId, Timestamp timestamp) {
        super();
        this.id = id;
        this.description = description;
        this.taxgroupId = taxgroupId;
        this.timestamp = timestamp;
    }


 

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigInteger getTaxgroupId() {
        return this.taxgroupId;
    }

    public void setTaxgroupId(BigInteger taxgroupId) {
        this.taxgroupId = taxgroupId;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}