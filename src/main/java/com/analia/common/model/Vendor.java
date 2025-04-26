package com.analia.common.model;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Date;

@NamedQuery( name = "getAllVendorLocationsViewForVendorId", query = "select vldv from VendorLocationDetailView vldv where vldv.vendorId = :vendorId")
@NamedQuery( name = "getAccountUserRoleByUserId", query = "select aur from AccountUser aur where aur.userId = :userId")
@NamedQuery( name = "getValidLocationsForAccountUserId", query = "select vl from ValidLocation vl where(vl.accountUserId = :accountUserId)")
@NamedQuery( name = "getValidLocationsWithDefaultLocationForAccountUserRoleId", query = "select vl from ValidLocation vl where (vl.accountUserId = :accountUserId) and (vl.defaultLocation = true)")
@NamedQuery( name = "getVendorLocationsForUserAndVendorId", query = "select vll from VendorLocationDetailView vll, ValidLocation vl, AccountUser au where (vll.vendorId = :vendorId) and (vll.vendorlocationId = vl.vendorLocationId) and (vl.accountUserId = au.id) and (au.userId = :userId)")
@NamedQuery( name = "getVendorLocationsForValidLocationId", query = "select vll from VendorLocationDetailView vll, ValidLocation vl where (vll.vendorlocationId = vl.vendorLocationId) and (vl.id = :validLocationId)")
@NamedQuery( name = "UnSetAllDefaultLocation", query = "update ValidLocation vl set vl.defaultLocation = false where vl.accountUserId = :accountUserId")
@NamedQuery( name = "setVendorDefaultLocation", query = "update ValidLocation vl set vl.defaultLocation = true where (vl.accountUserId = :accountUserId) and (vl.vendorLocationId = :vendorLocationId)")
@NamedQuery( name = "getVendorLocationForLocationId", query = "select vl from VendorLocation vl where vl.locationId = :locationId")
@NamedQuery( name = "getAccountUserRoleForUserAndVendorId", query = "select au from AccountUser au where (au.userId = :userId) and (au.vendorId = :vendorId)")
@NamedQuery( name = "getVendorByUserId", query = "select v from Vendor v, AccountUser au where (au.userId = :userId) and (au.vendorId = v.id)")
@NamedQuery( name = "getValidLocationForVendorLocationAndAccountUserId", query = "select vl from ValidLocation vl where (vl.vendorLocationId = :vendorLocationId) and (vl.accountUserId = :accountUserId)")
@NamedQuery( name = "getListAccountUsersViewForVendorId", query = "select auv from AccountUserView auv where (auv.vendorId = :vendorId) and (auv.accountDisabled = 0) and (auv.accountUserDisabled = 0)")
@NamedQuery( name = "disableAccountUserByVendor", query = "update AccountUser au set au.disabled = true where (au.userId = :userId) and (au.vendorId = :vendorId)")
@NamedQuery( name = "getAccountUserByVendorIdAndCreatedByUserId", query = "select au from AccountUser au where (au.createdBy = :createdByUserId) and (au.userId = :userId) and (au.vendorId = :vendorId)")
@NamedQuery( name = "removeAllValidLocationForUserInAccountUser", query = "Delete from ValidLocation vl where vl.accountUserId=:accountUserId")
@NamedQuery( name = "getValidVendorLocationsForUserId", query = "select vl from VendorLocation vl , AccountUserView aul where (aul.vendorLocationId = vl.id) and (aul.userId =:userId) and (aul.accountuserId =:accountUserId) and (aul.accountDisabled = 0) and (aul.accountUserDisabled = 0)")
@NamedQuery( name = "getValidVendorLocationDetailViewForUserAndVendor", query = "select ml from VendorLocationDetailView ml , AccountUserView aul where (aul.vendorLocationId = ml.vendorlocationId) and (aul.userId =:userId) and (aul.accountuserId =:accountUserId) and (aul.accountDisabled = 0) and (aul.accountUserDisabled = 0)")
@NamedQuery( name = "getVendorByExternalId", query = "select v from Vendor v where v.externalId = :externalId and v.disabled = false")

@Entity
@Table(name = "VENDOR")
public class Vendor extends AnaliaEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    @Column(name = "directory_id")
    private BigInteger directoryId;
    private String name;
    @Column(name = "corporate_name")
    private String corporateName;

    private String website;
    private String email;
    @Column(name = "voucher_email")
    private String voucherEmail;
    @Column(name = "external_id")
    private BigInteger externalId;

    private boolean disabled;
    @Column(name = "created_datetime")
    private Date createdDatetime;
    @Column(name = "corporate_phone_number")
    private String phoneNumber;
    @Column(name = "created_by")
    private BigInteger createdBy;


    private Date timestamp;

    public Vendor() {
        super();
    }


    public Vendor(BigInteger id, BigInteger directoryId, String name, String website, String email, String voucherEmail,
                  boolean disabled, String phoneNumber) {
        super();
        this.id = id;
        this.directoryId = directoryId;
        this.name = name;
        this.website = website;
        this.email = email;
        this.voucherEmail = voucherEmail;
        this.disabled = disabled;
        this.phoneNumber = phoneNumber;
    }



   

 

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigInteger getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BigInteger createdBy) {
        this.createdBy = createdBy;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getVoucherEmail() {
        return voucherEmail;
    }

    public void setVoucherEmail(String voucherEmail) {
        this.voucherEmail = voucherEmail;
    }

    public BigInteger getExternalId() {
        return externalId;
    }

    public void setExternalId(BigInteger externalId) {
        this.externalId = externalId;
    }
}
