package com.analia.common.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * The persistent class for the ROLE database table.
 */
@Entity
@Table(name = "ROLE")
@NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
public class Role extends AnaliaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public static final int ROLE_GUEST = 1;
    public static final int ROLE_NOT_VALIDATED_USER = 100;
    public static final int ROLE_VALIDATED_USER = 101;
    public static final int VENDOR_STAFF = 200;
    public static final int VENDOR_ADMIN = 300;
    public static final int SUPER_USER = 1000;
    public static final int SUPER_GOD = 1001;
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private boolean disabled;
    private Timestamp timestamp;

    public Role() {
        super();
    }

    public Role(BigInteger id, String description, boolean disabled, String name, Timestamp timestamp) {
        super();
        this.id = id;
        this.description = description;
        this.disabled = disabled;
        this.name = name;
        this.timestamp = timestamp;
    }


    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
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


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }
}