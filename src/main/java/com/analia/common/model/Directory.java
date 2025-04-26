package com.analia.common.model;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Date;

@NamedQuery(name = "getAllFileByDirectoryIdAndUserId",
        query = "select f from File f,Directory d where (f.directoryId = d.id)and (d.id = :directoryId)and ((d.ownerId IS NULL) or (d.ownerId = :userId))   order by f.secuenceNumber ASC")
@NamedQuery(name = "getFileByName", query="select f from File f, Directory d where (f.directoryId = :directoryId) and(f.name =:name) and (f.directoryId = d.id)")
@NamedQuery(name = "getFileWithDirectoryIdAndIndex", query="select f from File f where (f.directoryId = :directoryId) and (f.secuenceNumber = :sequenceNumber)")


@Entity
@Table(name = "DIRECTORY")
public class Directory extends AnaliaEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "owner_id")
    private BigInteger ownerId;
    @Column(name = "created_datetime")
    private Date createdDatetime;
    private boolean locked;
    @Column(name = "vendor_id")
    private BigInteger vendorId;

    @Column(name = "created_by")
    private BigInteger createdBy;
    private Date timestamp;

    public Directory() {
        super();
    }

    public Directory(BigInteger id, Date createdDatetime, BigInteger createdBy, BigInteger ownerId, boolean locked, Date timestamp) {
        super();
        this.id = id;
        this.createdDatetime = createdDatetime;
        this.createdBy = createdBy;
        this.ownerId = ownerId;
        this.locked = locked;
        this.timestamp = timestamp;
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

    public BigInteger getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BigInteger createdBy) {
        this.createdBy = createdBy;
    }

    public boolean getLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public BigInteger getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(BigInteger ownerId) {
        this.ownerId = ownerId;
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
