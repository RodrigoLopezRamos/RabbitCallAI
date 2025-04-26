package com.analia.common.model;

import jakarta.persistence.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * The persistent class for the GROUPING database table.
 * +-------------+-------------+------+-----+-------------------+-----------------------------+
 * | Field       | Type        | Null | Key | Default           | Extra                       |
 * +-------------+-------------+------+-----+-------------------+-----------------------------+
 * | id          | int(11)     | NO   | PRI | NULL              | auto_increment              |
 * | name        | varchar(48) | NO   | MUL | NULL              |                             |
 * | description | varchar(64) | NO   |     | NULL              |                             |
 * | disabled    | tinyint(1)  | NO   |     | NULL              |                             |
 * | created_by  | int(11)     | NO   | MUL | NULL              |                             |
 * | timestamp   | timestamp   | NO   |     | CURRENT_TIMESTAMP | on update CURRENT_TIMESTAMP |
 * +-------------+-------------+------+-----+-------------------+-----------------------------+
 */
@Entity
@Table(name = "GROUPING")
public class Grouping extends AnaliaEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;


    private Timestamp timestamp;

    private String name;

    private String description;

    @Column(name = "vendor_id")
    private BigInteger vendorId;

    @Column(name = "created_by")
    private BigInteger createdBy;


    public Grouping() {
    }

    public BigInteger getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BigInteger createdBy) {
        this.createdBy = createdBy;
    }

 

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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