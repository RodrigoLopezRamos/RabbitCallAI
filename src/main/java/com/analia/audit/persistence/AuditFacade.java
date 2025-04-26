package com.analia.audit.persistence;

import com.analia.common.model.Audit;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.PersistenceFacade;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;


@ApplicationScoped
public class AuditFacade extends JPAPersistenceFacade<Audit> implements PersistenceFacade<Audit> {

    @Inject
    EntityManager entityManager;

    public AuditFacade() {
        super(Audit.class);
    }




}
