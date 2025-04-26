package com.analia.common.model;


import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "FILTER_ATTRIBUTE")
public class FilterAttribute extends AnaliaEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;


    @Column(name = "parameter_name")
    private String parameterName;
    @Column(name = "parameter_type")
    private int parameterType;
    @Column(name = "single_value")
    private boolean singleValue;
    private Date timestamp;

    @Transient
    private Object value;


    //bi-directional many-to-one association to Filter
    @ManyToOne
    private Filter filter;


    public FilterAttribute() {
        super();
    }

    public FilterAttribute(BigInteger id, String parameterName, int parameterType, boolean singleValue, Date timestamp) {
        super();
        this.id = id;
        this.parameterName = parameterName;
        this.parameterType = parameterType;
        this.singleValue = singleValue;
        this.timestamp = timestamp;
    }


    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public int getParameterType() {
        return parameterType;
    }

    public void setParameterType(int parameterType) {
        this.parameterType = parameterType;
    }

    public boolean isSingleValue() {
        return singleValue;
    }

    public void setSingleValue(boolean singleValue) {
        this.singleValue = singleValue;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

}
