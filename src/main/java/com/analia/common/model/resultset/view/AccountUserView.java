package com.analia.common.model.resultset.view;

import com.analia.common.model.AnaliaEntity;

import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Table(name = "ACCOUNT_USER_LIST_VIEW")
public class AccountUserView extends AnaliaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "jpql_key_id")
    private String jpqlKeyId;

    @Column(name = "vendor_id")
    private BigInteger vendorId;

    @Column(name = "account_disabled")
    private byte accountDisabled;

    @Column(name = "account_user_disabled")
    private byte accountUserDisabled;

    @Column(name = "account_user_id")
    private BigInteger accountuserId;

    @Column(name = "user_id")
    private BigInteger userId;

    @Column(name = "default_location")
    private byte defaultLocation;

    @Column(name = "vendor_location_id")
    private BigInteger vendorLocationId;

    @Column(name = "location_id")
    private BigInteger locationId;

    @Column(name = "vendor_location_name")
    private String vendorLocationName;

    @Column(name = "role_id")
    private BigInteger roleId;

    @Column(name = "title")
    private String title;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String emailAddress;

    public String getJpqlKeyId() {
        return jpqlKeyId;
    }

    public void setJpqlKeyId(String jpqlKeyId) {
        this.jpqlKeyId = jpqlKeyId;
    }

    public BigInteger getVendorId() {
        return vendorId;
    }

    public void setVendorId(BigInteger vendorId) {
        this.vendorId = vendorId;
    }

    public byte getAccountDisabled() {
        return accountDisabled;
    }

    public void setAccountDisabled(byte accountDisabled) {
        this.accountDisabled = accountDisabled;
    }

    public byte getAccountUserDisabled() {
        return accountUserDisabled;
    }

    public void setAccountUserDisabled(byte accountUserDisabled) {
        this.accountUserDisabled = accountUserDisabled;
    }

    public BigInteger getAccountuserId() {
        return accountuserId;
    }

    public void setAccountuserId(BigInteger accountuserId) {
        this.accountuserId = accountuserId;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public byte getDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultLocation(byte defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    public BigInteger getVendorLocationId() {
        return vendorLocationId;
    }

    public void setVendorLocationId(BigInteger vendorLocationId) {
        this.vendorLocationId = vendorLocationId;
    }

    public BigInteger getLocationId() {
        return locationId;
    }

    public void setLocationId(BigInteger locationId) {
        this.locationId = locationId;
    }

    public String getVendorLocationName() {
        return vendorLocationName;
    }

    public void setVendorLocationName(String vendorLocationName) {
        this.vendorLocationName = vendorLocationName;
    }

    public BigInteger getRoleId() {
        return roleId;
    }

    public void setRoleId(BigInteger roleId) {
        this.roleId = roleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * This is a read-only object. This field is not used for VIEWS.
     */

    public BigInteger getId() {
        return new BigInteger("0");
    }

}
