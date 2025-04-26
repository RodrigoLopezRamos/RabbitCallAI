package com.analia.common.model;


import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Date;

@NamedQuery( name = "getUserDevice", query = "select ud from UserDevice ud where (ud.userId =:userId ) and (ud.deviceId = :deviceId) ")
@NamedQuery( name = "getPermissionByEndpoint", query = "select p  from Permission p , UserRole ur where (p.method =:method) and (p.resource =:resource) and (p.vendorId =:vendorId) and (ur.userId =:userId) and (ur.vendorId =:vendorId) and (ur.roleId >= p.roleCleareance)")
@NamedQuery( name = "getDeviceByUuid", query = "select d from Device d where (d.uuid =:uuid) and (d.vendorId =:vendorId)")
@NamedQuery( name = "getAuthorizeVendor", query = "select  u  from  User u,  Permission p, Role r, AccountUser aur where (u.id = :userId) and  (p.method = :method) and  (p.resource = :resource) and  ((r.id = 1025) or (r.id = 1035))")
@NamedQuery( name = "getListOfActiveRoles", query = "select r from Role r where  (r.disabled = false) and (r.id between :minRoleId and :maxRoleId)")

@Entity
@Table(name = "PERMISSION")
public class Permission extends AnaliaEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    private String resource;
    private String method;
    @Column(name = "vendor_id")
    private BigInteger vendorId;
    @Column(name = "role_cleareance")
    private BigInteger roleCleareance;
    @Column(name = "created_datetime")
    private Date createdDatetime;
    private Date timestamp;

    public Permission() {
        super();
    }

    public Permission(BigInteger id, String resource, String method, BigInteger roleCleareance, Date createdDatetime,
                      Date timestamp) {
        super();
        this.setId(id);
        this.resource = resource;
        this.method = method;
        this.roleCleareance = roleCleareance;
        this.createdDatetime = createdDatetime;
        this.timestamp = timestamp;
    }


    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public BigInteger getRoleCleareance() {
        return roleCleareance;
    }

    public void setRoleCleareance(BigInteger roleCleareance) {
        this.roleCleareance = roleCleareance;
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
