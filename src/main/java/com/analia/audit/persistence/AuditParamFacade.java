package com.analia.audit.persistence;

import com.analia.common.model.AuditParam;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.PersistenceFacade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;


@ApplicationScoped
public class AuditParamFacade extends JPAPersistenceFacade<AuditParam> implements PersistenceFacade<AuditParam> {


    @Inject
    EntityManager entityManager;

    public AuditParamFacade() {
        super(AuditParam.class);
    }




}
