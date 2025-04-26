package com.analia.common.model;

import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "USER_GROUPING")
public class UserGrouping extends AnaliaEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    @Column(name = "user_id")
    private BigInteger userId;
    @Column(name = "grouping_id")
    private BigInteger groupingId;
    private boolean disabled;
    private Timestamp timestamp;

   

 

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getGroupingId() {
        return groupingId;
    }

    public void setGroupingId(BigInteger groupingId) {
        this.groupingId = groupingId;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }


}
