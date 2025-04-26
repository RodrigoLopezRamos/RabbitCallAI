package com.analia.common.model.resultset.view;

import com.analia.common.model.AnaliaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Table(name = "VENDORLOCATION_DETAIL_VIEW")
public class VendorLocationDetailView extends AnaliaEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "vendorlocation_id")
    private BigInteger vendorlocationId;
    private String address1;

    private String address2;

    private String country;

    @Column(name = "main_office")
    private boolean mainOffice;

    @Column(name = "businessschedule_displaytext")
    private String businessDisplay;

    private double latitude;

    @Column(name = "location_id")
    private BigInteger locationId;

    private double longitude;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "vendorlocation_businessschedule_id")
    private BigInteger vendorlocationBusinesshourId;

    @Column(name = "vendorlocation_directory_id")
    private BigInteger vendorLocationDirectoryId;

    @Column(name = "vendor_id")
    private BigInteger vendorId;

    @Column(name = "zone_id")
    private BigInteger zoneId;

    private String province;

    @Column(name = "city_id")
    private BigInteger cityId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "postal_or_zipcode")
    private String postalCode;

    @Column(name = "vendor_location_name")
    private String vendorLocationName;

    @Column(name = "website")
    private String website;

    @Column(name = "location_email")
    private String locationEmail;

    public BigInteger getVendorlocationId() {
        return vendorlocationId;
    }

    public void setVendorlocationId(BigInteger vendorlocationId) {
        this.vendorlocationId = vendorlocationId;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isMainOffice() {
        return mainOffice;
    }

    public void setMainOffice(boolean mainOffice) {
        this.mainOffice = mainOffice;
    }

    public String getBusinessDisplay() {
        return businessDisplay;
    }

    public void setBusinessDisplay(String businessDisplay) {
        this.businessDisplay = businessDisplay;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public BigInteger getLocationId() {
        return locationId;
    }

    public void setLocationId(BigInteger locationId) {
        this.locationId = locationId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public BigInteger getVendorlocationBusinesshourId() {
        return vendorlocationBusinesshourId;
    }

    public void setVendorlocationBusinesshourId(BigInteger vendorlocationBusinesshourId) {
        this.vendorlocationBusinesshourId = vendorlocationBusinesshourId;
    }

    public BigInteger getVendorLocationDirectoryId() {
        return vendorLocationDirectoryId;
    }

    public void setVendorLocationDirectoryId(BigInteger vendorLocationDirectoryId) {
        this.vendorLocationDirectoryId = vendorLocationDirectoryId;
    }

    public BigInteger getVendorId() {
        return vendorId;
    }

    public void setVendorId(BigInteger vendorId) {
        this.vendorId = vendorId;
    }

    public BigInteger getZoneId() {
        return zoneId;
    }

    public void setZoneId(BigInteger zoneId) {
        this.zoneId = zoneId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public BigInteger getCityId() {
        return cityId;
    }

    public void setCityId(BigInteger cityId) {
        this.cityId = cityId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getVendorLocationName() {
        return vendorLocationName;
    }

    public void setVendorLocationName(String vendorLocationName) {
        this.vendorLocationName = vendorLocationName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocationEmail() {
        return locationEmail;
    }

    public void setLocationEmail(String locationEmail) {
        this.locationEmail = locationEmail;
    }


    public BigInteger getId() {
        return vendorlocationId;
    }
}