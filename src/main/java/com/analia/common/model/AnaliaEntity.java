package com.analia.common.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author Rodrigo Lopez
 */
public abstract class AnaliaEntity extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected BigInteger id;

    public Object clone() throws CloneNotSupportedException {
        throw new UnsupportedOperationException(
                "Baboso! you are not allowed to put this entity as part of the cache strategy!");
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }
}
