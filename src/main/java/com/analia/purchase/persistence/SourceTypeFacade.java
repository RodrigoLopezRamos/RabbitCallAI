package com.analia.purchase.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.SourceType;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import com.analia.purchase.persistence.SourceTypeFacadeLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@ApplicationScoped
public class SourceTypeFacade extends JPAPersistenceFacade<SourceType> implements SourceTypeFacadeLocal {

    private final String QUERY_GET_ALL_CARD_TYPES = "getAllCardTypes";
    private final String QUERT_GET_CARD_TYPE_BY_NAME = "getCardTypeByName";
    private final String PARAM_CARD_TYPE_NAME = "name";

    @Inject
    private EntityManager entityManager;

    public SourceTypeFacade() {
        super(SourceType.class);
    }





    public SourceType getCardTypeByName(String name) throws AnaliaException {
        JpqlParameter jpqlParameterName = new JpqlParameter(PARAM_CARD_TYPE_NAME, name);
        return getPersistForNamedQuery(QUERT_GET_CARD_TYPE_BY_NAME, jpqlParameterName);
    }



    public List<SourceType> getAllCardTypes() throws AnaliaException {
        return getListForNamedQuery(QUERY_GET_ALL_CARD_TYPES);
    }

}
