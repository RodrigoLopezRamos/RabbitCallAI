package com.analia.common.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.util.Date;


@NamedQuery(name = "categorySearchParam",
        query = "select c from Category c\n" +
                "where (c.name =:categoryTypeId)\n" +
                "and   (c.disabled = false)",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))


@NamedQuery(name = "getCategoriesByCategoryTypeId",
        query = "select c from Category c\n" +
                "where (c.categoryTypeId =:categoryTypeId)\n" +
                "and   (c.vendorId = :vendorId)" +
                "and   (c.disabled = false)",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))



@Entity
@Table(name = "CATEGORY")
public class Category extends AnaliaEntity {
    @Getter
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;


    @Column(name = "vendor_id")
    private BigInteger vendorId;
    @Column(name = "categorytype_id")
    private BigInteger categoryTypeId;
    @Column(name = "directory_id")
    private BigInteger directoryId;
    private String name;
    private boolean disabled;
    private Date timestamp;

    public Category() {
        super();
    }

    public Category(BigInteger id, BigInteger vendorId, BigInteger categoryTypeId, BigInteger directoryId, String name, boolean disabled, Date timestamp) {
        super();
        this.setId(id);
        this.setVendorId(vendorId);
        this.setCategoryTypeId(categoryTypeId);
        this.setDirectoryId(directoryId);
        this.setName(name);
        this.setDisabled(disabled);
        this.setTimestamp(timestamp);
    }


    @Override
    public BigInteger getId() {
        return id;
    }

    @Override
    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getVendorId() {
        return vendorId;
    }

    public void setVendorId(BigInteger vendorId) {
        this.vendorId = vendorId;
    }

    public BigInteger getCategoryTypeId() {
        return categoryTypeId;
    }

    public void setCategoryTypeId(BigInteger categoryTypeId) {
        this.categoryTypeId = categoryTypeId;
    }

    public BigInteger getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(BigInteger directoryId) {
        this.directoryId = directoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
