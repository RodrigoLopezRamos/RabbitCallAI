package com.analia.common.model;

import jakarta.persistence.*;

import java.math.BigInteger;

@Entity
@Table(name = "USER_ROLE")
public class UserRole extends AnaliaEntity{

    public static final BigInteger ROLE_GUEST = BigInteger.ONE;
    public static final BigInteger ROLE_NOT_EMAIL_VALIDATED = BigInteger.valueOf(100);
    public static final BigInteger ROLE_EMAIL_VALIDATED = BigInteger.valueOf(101);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    @Column(name = "user_id")
    private BigInteger userId;
    @Column(name = "vendor_id")
    private BigInteger vendorId;
    @Column(name = "role_id")
    private BigInteger roleId;
    private boolean disabled;
 

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getVendorId() {
        return vendorId;
    }

    public void setVendorId(BigInteger vendorId) {
        this.vendorId = vendorId;
    }

    public BigInteger getRoleId() {
        return roleId;
    }

    public void setRoleId(BigInteger roleId) {
        this.roleId = roleId;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
