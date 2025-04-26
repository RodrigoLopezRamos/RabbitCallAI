package com.analia.common.model;

import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "AUDIT")
public class Audit extends AnaliaEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "role_id")
    private int roleId;

    @Column(name = "device_id")
    private int deviceId;

    private String uri;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "http_method")
    private String httpMethod;

    @Column(name = "http_method_code")
    private String httpMethodCode;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "gps_latitude")
    private double gpsLatitude;

    @Column(name = "gps_longitude")
    private double gpsLongitude;

    @Column(name = "response_time")
    private int responseTime;

    @Column(name = "created_time")
    private Date createdDatetime;

    private Timestamp timestamp;

    //bi-directional many-to-one association to AuditParam
    @Transient
    private List<AuditParam> auditParams;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getHttpMethodCode() {
        return httpMethodCode;
    }

    public void setHttpMethodCode(String httpMethodCode) {
        this.httpMethodCode = httpMethodCode;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        if (this.userAgent != null && this.userAgent.length() > 254) {
            this.userAgent = this.userAgent.substring(0, 242) + "[truncated]";
        }
    }

    public double getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(double gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public double getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(double gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }


   

 

    public List<AuditParam> getAuditParams() {
        if (auditParams == null) {
            return auditParams = new ArrayList<>();
        }
        return this.auditParams;
    }

    public void setAuditParams(List<AuditParam> auditParams) {
        this.auditParams = auditParams;
    }

    public AuditParam addAuditParam(AuditParam auditParam) {
        getAuditParams().add(auditParam);
        return auditParam;
    }

    public AuditParam removeAuditParam(AuditParam auditParam) {
        getAuditParams().remove(auditParam);
        return auditParam;
    }


}
