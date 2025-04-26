package com.analia.common.model;

import jakarta.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "STATUS")
public class Status extends AnaliaEntity {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    private String name;
    private String description;
    private Date timestamp;


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }
    public Status() {
        super();
    }

    public Status(BigInteger id, String name, String description, Date timestamp) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.timestamp = timestamp;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
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

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
