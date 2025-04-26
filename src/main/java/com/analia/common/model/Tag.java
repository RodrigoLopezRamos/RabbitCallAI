package com.analia.common.model;

import com.analia.common.cache.AnaliaCacheableEntity;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "TAG")
public class Tag extends AnaliaEntity implements AnaliaCacheableEntity<Tag> {

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


    private String name;
    private String description;
    private boolean disabled;
    private Timestamp timestamp;

    public Tag() {
        super();
    }

    public Tag(BigInteger id, String name, String description, boolean disabled, Timestamp timestamp) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.disabled = disabled;
        this.timestamp = timestamp;
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

    public boolean getDisabled() {
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


    public Tag clone() {
        Tag tag = new Tag();
        tag.setId(this.id);
        tag.setName(this.name);
        tag.setDisabled(this.disabled);
        tag.setDescription(this.description);
        tag.setTimestamp(this.timestamp);
        return tag;
    }

}
