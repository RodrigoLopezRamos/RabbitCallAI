package com.analia.common.model;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * The persistent class for the CACHE_CONFIG database table.
 */
@Entity
@Table(name = "CACHE_CONFIG")
@NamedQuery(name = "CacheConfig.findAll", query = "SELECT c FROM CacheConfig c")
@NamedQuery(name = "getCacheConfigEnable",
        query = "select d from CacheConfig d where (d.id =:cacheId) and (d.disabled = 0) ",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
public class CacheConfig extends AnaliaEntity implements Serializable {

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

    private String name;
    private String description;
    @Column(name = "table_name")
    private String tableName;
    private int version;
    private String handler;
    private byte disabled;
    @Column(name = "created_datetime")
    private String createdDatetime;
    private Timestamp timestamp;

    public CacheConfig() {
        super();
    }

    public CacheConfig(BigInteger id, String createdDatetime, String description, byte disabled, String handler, String name,
                       String tableName, int version, Timestamp timestamp) {
        super();
        this.id = id;
        this.createdDatetime = createdDatetime;
        this.description = description;
        this.disabled = disabled;
        this.handler = handler;
        this.name = name;
        this.tableName = tableName;
        this.version = version;
        this.timestamp = timestamp;
    }



    public String getCreatedDatetime() {
        return this.createdDatetime;
    }

    public void setCreatedDatetime(String createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte getDisabled() {
        return this.disabled;
    }

    public void setDisabled(byte disabled) {
        this.disabled = disabled;
    }

    public String getHandler() {
        return this.handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}