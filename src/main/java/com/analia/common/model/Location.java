package com.analia.common.model;

import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Date;

@NamedQuery( name = "getCityByName", query = "select c from City c where (c.name =:name)")
@NamedQuery( name = "getZoneByName", query = "select z from Zone z where (z.name =:name)")
@NamedQuery( name = "getCitiesBySearchParam", query = "select c from City c where (c.name  like :search)")

@Entity
@Table(name = "LOCATION")
public class Location extends AnaliaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    private static final long serialVersionUID = 1L;

    @Column(name = "address1")
    private String address1;
    @Column(name = "address2")
    private String address2;
    @Column(name = "city_id")
    private BigInteger cityId;
    private String city;
    private String province;
    private String country;
    @Column(name = "postal_or_zipcode")
    private String postalOrZipcode;
    private double latitude;
    private double longitude;
    @Column(name = "created_by")
    private BigInteger createdBy;
    @Column(name = "created_datetime")
    private Date createdDatetime;
    private Date timestamp;

    public Location() {
    }

    public Location(BigInteger id, String address1, String address2, BigInteger cityId, String city, String province, String country,
                    String postalOrZipcode, double latitude, double longitude) {
        super();
        this.id = id;
        this.address1 = address1;
        this.address2 = address2;
        this.cityId = cityId;
        this.city = city;
        this.province = province;
        this.country = country;
        this.postalOrZipcode = postalOrZipcode;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public BigInteger getCityId() {
        return cityId;
    }

    public void setCityId(BigInteger cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalOrZipcode() {
        return postalOrZipcode;
    }

    public void setPostalOrZipcode(String postalOrZipcode) {
        this.postalOrZipcode = postalOrZipcode;
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

    public BigInteger getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BigInteger createdBy) {
        this.createdBy = createdBy;
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

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

}
