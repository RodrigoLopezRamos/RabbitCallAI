package com.analia.common.model;

import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "CATEGORY_TYPE")
public class CategoryType extends AnaliaEntity {

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
    private boolean disabled;
    private Date timestamp;

    public CategoryType() {
        super();
    }

    public CategoryType(BigInteger id, String name, boolean disabled, Date timestamp) {
        super();

        this.id = id;
        this.name = name;
        this.disabled = disabled;
        this.timestamp = timestamp;
    }

    public static CategoryTypeEnum getCategoryTypeEnum(BigInteger categoryTypeId) throws AnaliaException {
        for (CategoryTypeEnum categoryTypeEnum : CategoryTypeEnum.values()) {
            if (categoryTypeId == categoryTypeEnum.getId()) {
                return categoryTypeEnum;
            }
        }
        throw new AnaliaException(ExceptionCode.SERVER_ERROR, "CategoryTypeEnum is not defined !");
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public enum CategoryTypeEnum {

        PLACE(new BigInteger("1")),
        PRODUCT(new BigInteger("2")),
        EVENT(new BigInteger("3"));

        private final BigInteger id;

        CategoryTypeEnum(BigInteger id) {
            this.id = id;
        }

        public BigInteger getId() {
            return id;
        }

  
    }
}
