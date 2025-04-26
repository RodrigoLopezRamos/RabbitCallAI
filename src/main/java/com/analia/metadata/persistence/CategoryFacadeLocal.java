package com.analia.metadata.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Category;
import com.analia.common.persistence.PersistenceFacade;

import java.math.BigInteger;
import java.util.List;

public interface CategoryFacadeLocal extends PersistenceFacade<Category> {

    List<Category> getCategoriesByCategoryTypeId(BigInteger vendorId,BigInteger categoryTypeId) throws AnaliaException;

    Category getCategoryByName(String name)throws AnaliaException;
}
