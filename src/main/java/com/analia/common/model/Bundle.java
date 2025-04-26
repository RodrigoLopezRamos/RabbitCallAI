package com.analia.common.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * The persistent class for the BUNDLE database table.
 */
@Entity
@Table(name = "BUNDLE")
@NamedQuery(name = "Bundle.findAll", query = "SELECT b FROM Bundle b")
public class Bundle extends AnaliaEntity implements Serializable {

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

    @Column(name = "parent_id")
    private int parentId;
    private String name;
    private Timestamp timestamp;

    public Bundle() {
        super();
    }

    public Bundle(BigInteger id, String name, int parentId, Timestamp timestamp) {
        super();
        this.setId(id);
        this.name = name;
        this.parentId = parentId;
        this.timestamp = timestamp;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return this.parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }


}