package com.analia.common.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "STORY")
public class Story extends AnaliaEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "storytype_id")
    private BigInteger storyTypeId;

    private String title;

    private String name;

    private String author;

    private String description;

    @Column(name = "zone_id")
    private BigInteger zoneId;

    @Column(name = "directory_id")
    private BigInteger directoryId;

    @Column(name = "category_id")
    private BigInteger categoryId;

    @Column(name = "vendor_id")
    private BigInteger vendorId;

    @Column(name = "favourite_count")
    private BigInteger favouriteCount;

    @Column(name = "like_count")
    private BigInteger likeCount;

    @Column(name = "not_before")
    private Date notBefore;

    @Column(name = "not_after")
    private Date notAfter;

    @Column(name = "created_datetime")
    private Date createdDatetime;

    @Column(name = "external_link")
    private String externalLink;

    @Column(name = "disabled")
    private boolean disabled;

    @Column(name = "created_by")
    private BigInteger createdBy;

    @Column(name = "external_id")
    private BigInteger externalId;


    private double latitude;

    private double longitude;
    private Timestamp timestamp;

    @Column(name = "image_url")
    private String imageUrl;

    @Transient
    private String source;

    public static long getSerialversionuid() {
        return getSerialVersionUID();
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public BigInteger getId() {
        return id;
    }

    @Override
    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getStoryTypeId() {
        return storyTypeId;
    }

    public void setStoryTypeId(BigInteger storyTypeId) {
        this.storyTypeId = storyTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigInteger getZoneId() {
        return zoneId;
    }

    public void setZoneId(BigInteger zoneId) {
        this.zoneId = zoneId;
    }

    public BigInteger getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(BigInteger directoryId) {
        this.directoryId = directoryId;
    }

    public BigInteger getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(BigInteger categoryId) {
        this.categoryId = categoryId;
    }

    public BigInteger getVendorId() {
        return vendorId;
    }

    public void setVendorId(BigInteger vendorId) {
        this.vendorId = vendorId;
    }

    public BigInteger getFavouriteCount() {
        return favouriteCount;
    }

    public void setFavouriteCount(BigInteger favouriteCount) {
        this.favouriteCount = favouriteCount;
    }

    public BigInteger getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(BigInteger likeCount) {
        this.likeCount = likeCount;
    }

    public Date getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(Date notBefore) {
        this.notBefore = notBefore;
    }

    public Date getNotAfter() {
        return notAfter;
    }

    public void setNotAfter(Date notAfter) {
        this.notAfter = notAfter;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public String getExternalLink() {
        return externalLink;
    }

    public void setExternalLink(String externalLink) {
        this.externalLink = externalLink;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public BigInteger getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BigInteger createdBy) {
        this.createdBy = createdBy;
    }

    public BigInteger getExternalId() {
        return externalId;
    }

    public void setExternalId(BigInteger externalId) {
        this.externalId = externalId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
