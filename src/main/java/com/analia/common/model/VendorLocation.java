package com.analia.common.model;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "VENDOR_LOCATION")
public class VendorLocation extends AnaliaEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    @Column(name = "vendor_id")
    private BigInteger vendorId;
    @Column(name = "location_id")
    private BigInteger locationId;
    @Column(name = "directory_id")
    private BigInteger directoryId;
    @Column(name = "businessschedule_id")
    private BigInteger businessScheduleId;
    private String name;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "location_email")
    private String locationEmail;
    @Column(name = "main_office")
    private boolean mainOffice;
    @Column(name = "created_by")
    private BigInteger createdBy;
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "created_datetime")
    private Date createdDatetime;
    @Column(name = "timestamp")
    private Date timestamp;

    public VendorLocation() {
        super();
    }

    public VendorLocation(BigInteger id, BigInteger vendorId, BigInteger locationId, BigInteger directoryId, String name, String phoneNumber, String locationEmail, boolean mainOffice, BigInteger createdBy, Date startTime, Date endTime, Date createdDatetime, Date timestamp) {
        super();
        this.id = id;
        this.vendorId = vendorId;
        this.locationId = locationId;
        this.directoryId = directoryId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.locationEmail = locationEmail;
        this.mainOffice = mainOffice;
        this.createdBy = createdBy;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createdDatetime = createdDatetime;
        this.businessScheduleId = BigInteger.ONE;
        this.timestamp = timestamp;
    }

   

 

    public BigInteger getVendorId() {
        return vendorId;
    }

    public void setVendorId(BigInteger vendorId) {
        this.vendorId = vendorId;
    }

    public BigInteger getLocationId() {
        return locationId;
    }

    public void setLocationId(BigInteger locationId) {
        this.locationId = locationId;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocationEmail() {
        return locationEmail;
    }

    public void setLocationEmail(String locationEmail) {
        this.locationEmail = locationEmail;
    }

    public boolean isMainOffice() {
        return mainOffice;
    }

    public void setMainOffice(boolean mainOffice) {
        this.mainOffice = mainOffice;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigInteger getBusinessScheduleId() {
        return businessScheduleId;
    }

    public void setBusinessScheduleId(BigInteger businessScheduleId) {
        this.businessScheduleId = businessScheduleId;
    }

}
