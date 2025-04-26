package com.analia.common.model;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "USER_PROFILE")
public class UserProfile extends AnaliaEntity {

    private static final long serialVersionUID = 1L;

    public static int STRING = 0;
    public static int DOUBLE = 1;
    public static int INTEGER = 2;
    public static int PROFILE_DATA_TYPE = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    @Column(name = "type")
    private BigInteger type;
    @Column(name = "user_id")
    private BigInteger userId;
    @Column(name = "key_profile")
    private String key;
    private String value;
    private Date timestamp;

   

 

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getType() {
        return type;
    }

    public void setType(BigInteger type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }


    public String mapKey() {
        return key.concat(userId.toString());
    }
}
