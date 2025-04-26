package com.analia.user.persistence;

import com.analia.common.model.Sharing;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.PersistenceFacade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;


@ApplicationScoped
public class ShareFacade extends JPAPersistenceFacade<Sharing> implements PersistenceFacade<Sharing> {

    @Inject
    private EntityManager entityManager;

    public ShareFacade() {
        super(Sharing.class);
    }



}
