package com.analia.common.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

/**
 * +------------------+------------+------+-----+-------------------+-----------------------------+
 * | Field | Type | Null | Key | Default | Extra |
 * +------------------+------------+------+-----+-------------------+-----------------------------+
 * | id | int(11) | NO | PRI | NULL | auto_increment | | story_id | int(11) | NO
 * | MUL | NULL | | | parent_id | int(11) | YES | MUL | NULL | | |
 * secuence_number | int(11) | NO | | NULL | | | created_datetime | datetime |
 * NO | | NULL | | | text | text | NO | | NULL | | | created_by | int(11) | NO |
 * MUL | NULL | | | disabled | tinyint(1) | NO | | NULL | | | timestamp |
 * timestamp | NO | | CURRENT_TIMESTAMP | on update CURRENT_TIMESTAMP |
 * +------------------+------------+------+-----+-------------------+-----------------------------+
 *
 * @author rlopez
 */
@Entity
@Table(name = "COMMENT")
public class Comment extends AnaliaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Column(name = "story_id")
    private BigInteger storyId;
    @Column(name = "parent_id")
    private BigInteger parentId;
    @Column(name = "secuence_number")
    private BigInteger secuenceNumber;
    @Column(name = "created_datetime")
    private Date createdDatetime;
    private String text;
    @Column(name = "created_by")
    private BigInteger createdBy;
    private boolean disabled;
    private Timestamp timestamp;


   

 

    public BigInteger getStoryId() {
        return storyId;
    }

    public void setStoryId(BigInteger storyId) {
        this.storyId = storyId;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public BigInteger getSecuenceNumber() {
        return secuenceNumber;
    }

    public void setSecuenceNumber(BigInteger secuenceNumber) {
        this.secuenceNumber = secuenceNumber;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BigInteger getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BigInteger createdBy) {
        this.createdBy = createdBy;
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
