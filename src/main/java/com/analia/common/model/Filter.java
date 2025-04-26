package com.analia.common.model;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

//@NamedQuery(name = "getFilterByName", query = "select f from Filter f where (f.name = :name)")
@NamedQuery(name = "getFilterByName",
        query = "select f from Filter f where (f.name = :name)",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
@Entity
@Table(name = "FILTER")
public class Filter extends AnaliaEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;


    @Column(name = "filtertype_id")
    private int filterTypeId;
    private String name;
    @Column(name = "filter_handler")
    private String filterHandler;
    private boolean disabled;
    private Date timestamp;
    //bi-directional many-to-one association to FilterParameter
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "filter")
    private List<FilterAttribute> filterAttributes;

    public Filter() {
        super();
    }

    public Filter(BigInteger id, String name, String filterHandler, boolean disabled, Date timestamp) {
        super();
        this.id = id;
        this.name = name;
        this.filterHandler = filterHandler;
        this.disabled = disabled;
        this.timestamp = timestamp;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public int getFilterTypeId() {
        return filterTypeId;
    }

    public void setFilterTypeId(int filterTypeId) {
        this.filterTypeId = filterTypeId;
    }

   

 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilterHandler() {
        return filterHandler;
    }

    public void setFilterHandler(String filterHandler) {
        this.filterHandler = filterHandler;
    }

    public List<FilterAttribute> getFilterAttributes() {
        return this.filterAttributes;
    }

    public void setFilterAttributes(List<FilterAttribute> filterAttributes) {
        this.filterAttributes = filterAttributes;
    }

    public FilterAttribute addFilterAttribute(FilterAttribute filterAttribute) {
        getFilterAttributes().add(filterAttribute);
        filterAttribute.setFilter(this);
        return filterAttribute;
    }

    public FilterAttribute removeFilterAttribute(FilterAttribute filterAttribute) {
        getFilterAttributes().remove(filterAttribute);
        filterAttribute.setFilter(null);
        return filterAttribute;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
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
