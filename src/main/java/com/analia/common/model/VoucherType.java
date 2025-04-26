package com.analia.common.model;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "VOUCHER_TYPE")
public class VoucherType extends AnaliaEntity {

    /**
     * +----------------+-------------+------+-----+-------------------+-----------------------------+
     * | Field          | Type        | Null | Key | Default           | Extra                       |
     * +----------------+-------------+------+-----+-------------------+-----------------------------+
     * | id             | int(11)     | NO   | PRI | NULL              | auto_increment              |
     * | name           | varchar(48) | NO   |     | NULL              |                             |
     * | description    | varchar(64) | NO   |     | NULL              |                             |
     * | credit_enabled | tinyint(1)  | NO   |     | NULL              |                             |
     * | cash_enabled   | tinyint(1)  | NO   |     | NULL              |                             |
     * | timestamp      | timestamp   | NO   |     | CURRENT_TIMESTAMP | on update CURRENT_TIMESTAMP |
     * +----------------+-------------+------+-----+-------------------+-----------------------------+
     */

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    private String name;
    private String description;

    @Column(name = "credit_enabled")
    private boolean creditEnabled;

    @Column(name = "cash_enabled")
    private boolean cashEnabled;


    private Timestamp timestamp;

    public VoucherType() {
        super();
    }

    public VoucherType(BigInteger id, String name, String description, Timestamp timestamp) {
        super();

        this.id = id;
        this.name = name;
        this.description = description;
        this.timestamp = timestamp;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public boolean isCashEnabled() {
        return cashEnabled;
    }

    public void setCashEnabled(boolean cashEnabled) {
        this.cashEnabled = cashEnabled;
    }


    public void setId(BigInteger id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isCreditEnabled() {
        return creditEnabled;
    }

    public void setCreditEnabled(boolean creditEnabled) {
        this.creditEnabled = creditEnabled;
    }
}
