package com.analia.common.model;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.sql.Timestamp;

/***
 * +-----------+-------------+------+-----+-------------------+-----------------------------+
 | Field     | Type        | Null | Key | Default           | Extra                       |
 +-----------+-------------+------+-----+-------------------+-----------------------------+
 | id        | int(11)     | NO   | PRI | NULL              | auto_increment              |
 | name      | varchar(48) | NO   |     | NULL              |                             |
 | disabled  | tinyint(1)  | NO   |     | NULL              |                             |
 | timestamp | timestamp   | NO   |     | CURRENT_TIMESTAMP | on update CURRENT_TIMESTAMP |
 +-----------+-------------+------+-----+-------------------+-----------------------------+
 * @author rlopez
 *
 */
@Entity
@Table(name = "STORY_TYPE")
public class StoryType extends AnaliaEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;


    private String name;

    private boolean disabled;

    private Timestamp timestamp;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }


    public BigInteger getId() {
        return id;
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
