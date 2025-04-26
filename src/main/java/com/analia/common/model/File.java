package com.analia.common.model;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "FILE")
public class File extends AnaliaEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "mime_type")
    private String mimeType;
    @Column(name = "mimetype_id")
    private int mimeTypeId;
    @Column(name = "directory_id")
    private BigInteger directoryId;
    @Column(name = "secuence_number")
    private int secuenceNumber;
    private String name;
    private String description;
    private String path;
    @Column(name = "created_datetime")
    private Date createdDatetime;
    @Column(name = "created_by")
    private BigInteger createdBy;
    private Date timestamp;

    public File() {
        super();
    }

    public File(BigInteger id, int mimeTypeId, BigInteger directoryId, int secuenceNumber, String name, String description, String path, Date createdDatetime, BigInteger createdBy, Date timestamp) {
        super();
        this.id = id;
        this.mimeTypeId = mimeTypeId;
        this.directoryId = directoryId;
        this.secuenceNumber = secuenceNumber;
        this.name = name;
        this.description = description;
        this.path = path;
        this.createdDatetime = createdDatetime;
        this.createdBy = createdBy;
        this.timestamp = timestamp;
    }

    public BigInteger getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BigInteger createdBy) {
        this.createdBy = createdBy;
    }

   

 

    public BigInteger getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(BigInteger directoryId) {
        this.directoryId = directoryId;
    }

    public int getSecuenceNumber() {
        return secuenceNumber;
    }

    public void setSecuenceNumber(int secuenceNumber) {
        this.secuenceNumber = secuenceNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getMimeTypeId() {
        return mimeTypeId;
    }

    public void setMimeTypeId(int mimeTypeId) {
        this.mimeTypeId = mimeTypeId;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

}
