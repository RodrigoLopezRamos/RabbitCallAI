package com.analia.social.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.StoryType;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.social.persistence.StoryTypeFacadeLocal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

import java.util.List;

@ApplicationScoped
public class StoryTypeFacade extends JPAPersistenceFacade<StoryType> implements StoryTypeFacadeLocal {
    /**
     *
     */
    private static final String GET_STORY_TYPES_QUERY = "getStoryTypes";

    @Inject
    private EntityManager entityManager;


    public StoryTypeFacade() {
        super(StoryType.class);
    }


    public List<StoryType> getStoryTypes() throws AnaliaException {
        return getListForNamedQuery(GET_STORY_TYPES_QUERY);
    }




}
