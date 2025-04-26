package com.analia.common.model;

import com.analia.common.cache.AnaliaCacheableEntity;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "USER_TAG")
public class UserTag extends AnaliaEntity implements AnaliaCacheableEntity<UserTag> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    @Column(name = "user_id")
    private BigInteger userId;
    @Column(name = "tag_id")
    private BigInteger tagId;
    private boolean disabled;
    private Timestamp timestamp;

    public UserTag() {
        super();
    }

    public UserTag(BigInteger id, BigInteger userId, BigInteger tagId, boolean disabled, Timestamp timestamp) {
        super();
        this.id = id;
        this.setUserId(userId);
        this.tagId = tagId;
        this.disabled = disabled;
        this.timestamp = timestamp;
    }


    public BigInteger getTagId() {
        return tagId;
    }

    public void setTagId(BigInteger tagId) {
        this.tagId = tagId;
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


    public UserTag clone() {
        UserTag userTag = new UserTag();
        userTag.setId(this.id);
        userTag.setTagId(this.tagId);
        userTag.setUserId(this.userId);
        userTag.setTimestamp(timestamp);
        return userTag;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }
}
