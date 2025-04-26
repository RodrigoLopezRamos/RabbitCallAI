package com.analia.common.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * The persistent class for the LOCALIZED_TEXT database table.
 */
@Entity
@Table(name = "LOCALIZED_TEXT")
@NamedQuery(name = "LocalizedText.findAll", query = "SELECT l FROM LocalizedText l")
public class LocalizedText extends AnaliaEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "bundle_id")
    private BigInteger bundleId;
    @Column(name = "text_key")
    private String textKey;
    @Lob
    @Column(name = "text_message")
    private String textMessage;
    private Timestamp timestamp;

    public LocalizedText() {
        super();
    }

    public LocalizedText(BigInteger id, BigInteger bundleId, String textKey, String textMessage, Timestamp timestamp) {
        super();
        this.id = id;
        this.bundleId = bundleId;
        this.textKey = textKey;
        this.textMessage = textMessage;
        this.timestamp = timestamp;
    }


    public BigInteger getBundleId() {
        return this.bundleId;
    }

    public void setBundleId(BigInteger bundleId) {
        this.bundleId = bundleId;
    }

    public String getTextKey() {
        return this.textKey;
    }

    public void setTextKey(String textKey) {
        this.textKey = textKey;
    }

    public String getTextMessage() {
        return this.textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

}