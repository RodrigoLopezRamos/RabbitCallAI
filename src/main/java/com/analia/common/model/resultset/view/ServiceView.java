package com.analia.common.model.resultset.view;

import com.analia.common.model.AnaliaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Table(name = "SERVICE_VIEW")
public class ServiceView extends AnaliaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "service_id")
    private int serviceId;

    @Column(name = "zone_id")
    private int zoneId;

    @Column(name = "service_directory_id")
    private Integer serviceDirectoryId;

    @Column(name = "service_provider_name")
    private String serviceProviderName;

    @Column(name = "service_vendor_id")
    private int serviceMerchantId;

    @Column(name = "service_contact_info")
    private String serviceContactInfo;

    @Column(name = "service_active")
    private int serviceActive;

    @Column(name = "service_type_id")
    private int serviceTypeId;

    @Column(name = "service_type_name")
    private String serviceTypeName;

    @Column(name = "service_type_active")
    private int serviceTypeActive;

    @Column(name = "service_type_directory_id")
    private Integer serviceTypeDirectoryId;

    @Column(name = "city_name")
    private String cityName;

    public ServiceView() {

    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getServiceDirectoryId() {
        return serviceDirectoryId;
    }

    public void setServiceDirectoryId(Integer serviceDirectoryId) {
        this.serviceDirectoryId = serviceDirectoryId;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    public int getServiceMerchantId() {
        return serviceMerchantId;
    }

    public void setServiceMerchantId(int serviceMerchantId) {
        this.serviceMerchantId = serviceMerchantId;
    }

    public String getServiceContactInfo() {
        return serviceContactInfo;
    }

    public void setServiceContactInfo(String serviceContactInfo) {
        this.serviceContactInfo = serviceContactInfo;
    }

    public int getServiceActive() {
        return serviceActive;
    }

    public void setServiceActive(int serviceActive) {
        this.serviceActive = serviceActive;
    }

    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public int getServiceTypeActive() {
        return serviceTypeActive;
    }

    public void setServiceTypeActive(int serviceTypeActive) {
        this.serviceTypeActive = serviceTypeActive;
    }

    public Integer getServiceTypeDirectoryId() {
        return serviceTypeDirectoryId;
    }

    public void setServiceTypeDirectoryId(Integer serviceTypeDirectoryId) {
        this.serviceTypeDirectoryId = serviceTypeDirectoryId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


    public BigInteger getId() {
        return new BigInteger(Integer.toString(serviceId));
    }

}
