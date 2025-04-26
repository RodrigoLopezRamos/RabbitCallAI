package com.analia.common.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "SOURCE_TYPE")
@NamedQuery(name = "SourceType.findAll", query = "SELECT st FROM SourceType st")
public class SourceType extends AnaliaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    private static final long serialVersionUID = 1L;



    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    private String name;
    private boolean active;
    private Timestamp timestamp;

    public SourceType() {
        super();
    }

    public SourceType(BigInteger id, boolean active, String name, Timestamp timestamp) {
        super();
        this.id = id;
        this.active = active;
        this.name = name;
        this.timestamp = timestamp;
    }


 

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}