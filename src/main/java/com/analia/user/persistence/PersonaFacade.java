package com.analia.user.persistence;

import com.analia.common.model.Persona;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.PersistenceFacade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;


@ApplicationScoped
public class PersonaFacade extends JPAPersistenceFacade<Persona> implements PersistenceFacade<Persona> {


    @Inject
    private EntityManager entityManager;


    public PersonaFacade() {
        super(Persona.class);
    }





}
