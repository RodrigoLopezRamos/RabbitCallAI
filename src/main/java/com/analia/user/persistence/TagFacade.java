package com.analia.user.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Tag;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.math.BigInteger;
import java.util.List;

@ApplicationScoped
public class TagFacade extends JPAPersistenceFacade<Tag>  {
    private static final String QUERY_NAME_GET_TAG_BY_NAME = "getTagByName";
    private static final String QUERY_NAME_GET_TAGS_BY_USER_ID = "getTagsByUserId";
    private static final String PARAMETER_USER_ID = "userId";
    private static final String PARAMETER_NAME = "name";

    @Inject
    private EntityManager entityManager;

    public TagFacade() {
        super(Tag.class);
    }






    public List<Tag> getUserTags(BigInteger userId) throws AnaliaException {
        return getListForNamedQuery(QUERY_NAME_GET_TAGS_BY_USER_ID, new JpqlParameter(PARAMETER_USER_ID, userId));
    }


    public Tag getTagByName(String name) throws AnaliaException {
        List<Tag> tags = getListForNamedQuery(QUERY_NAME_GET_TAG_BY_NAME, new JpqlParameter(PARAMETER_NAME, name));
        if (tags.isEmpty()) {
            return null;
        }
        return tags.get(0);
    }

}
