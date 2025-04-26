package com.analia.metadata.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Category;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import com.analia.metadata.persistence.CategoryFacadeLocal;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;

import java.math.BigInteger;
import java.util.List;



@ApplicationScoped
public class CategoryFacade extends JPAPersistenceFacade<Category> implements CategoryFacadeLocal {
    private static final String GET_CATEGORIES_BY_CATEGORY_TYPE_ID = "getCategoriesByCategoryTypeId";
    private static final String PARAM_CATEGORY_TYPE_ID = "categoryTypeId";

    private static final String PARAM_CATEGORY_VENDOR_ID = "vendorId";


    @Inject
    private EntityManager entityManager;

    public CategoryFacade() {
        super(Category.class);
    }


    public List<Category> getCategoriesByCategoryTypeId(BigInteger vendorId, BigInteger categoryTypeId) throws AnaliaException {
        return getListForNamedQuery(GET_CATEGORIES_BY_CATEGORY_TYPE_ID,
                new JpqlParameter(PARAM_CATEGORY_TYPE_ID, categoryTypeId),
                new JpqlParameter(PARAM_CATEGORY_VENDOR_ID, vendorId)
        );
    }


    @Transactional
    public Category getCategoryByName(String name) throws AnaliaException {
        Query query = entityManager.createQuery("select c from Category c where c.name =?1");
        query.setParameter(1, name);
        List result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return (Category) result.get(0);
    }

}
