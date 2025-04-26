package com.analia.social.core;

import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.Story;
import com.analia.common.model.StoryType;
import com.analia.common.model.User;
import com.analia.social.core.SocialCoreLocal;
import com.analia.social.persistence.StoryFacadeLocal;
import com.analia.social.persistence.StoryTypeFacadeLocal;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class SocialCore implements SocialCoreLocal {
    /**
     *
     */
    @Inject
    private StoryTypeFacadeLocal storyTypeFacadeLocal;

    /**
     *
     */
    @Inject
    private StoryFacadeLocal storyFacadeLocal;

    /**
     * @return
     * @throws AnaliaException
     */

    public List<StoryType> getStoryTypes() throws AnaliaException {
        return storyTypeFacadeLocal.getStoryTypes();
    }

    /**
     * @param storyId
     * @return
     * @throws AnaliaException
     */

    public Story getStoryById(BigInteger storyId) throws AnaliaException {
        return storyFacadeLocal.getStoryById(storyId);
    }

    /**
     * @param story
     * @return
     * @throws AnaliaException
     */

    @Transactional
    public Story saveStory(Story story) throws AnaliaException {
        if (story == null) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, "Story object must be not null . Baboso!");
        }
        Story storyFromDatase = storyFacadeLocal.find(story.getId());
        if (storyFromDatase == null) {
            story.setCreatedDatetime(new Date());
            story.setCreatedBy(AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class).getId());
        }
        storyFacadeLocal.save(story);
        storyFacadeLocal.flush();
        return story;
    }


    public Story saveStorySystem(Story story) throws AnaliaException {
        Story storyFromDatase = null;

        if (story == null) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, "Story object must be not null . Baboso!");
        }
        if (story.getId() != null) {
            storyFromDatase = storyFacadeLocal.find(story.getId());

        }
        if (storyFromDatase == null) {
            story.setCreatedDatetime(new Date());
            story.setCreatedBy(BigInteger.valueOf(1));
        }
        storyFacadeLocal.save(story);
        storyFacadeLocal.flush();
        return story;
    }

    /**
     * @param userId
     * @param groupName
     * @return
     * @throws AnaliaException
     */

    public List<Story> getStoriesByGroupName(BigInteger userId, String groupName, int page, int pageSize) throws AnaliaException {
        return storyFacadeLocal.getStoriesByGroupName(userId, groupName, page, pageSize);
    }

    /**
     * @param page
     * @param pageSize
     * @return
     * @throws AnaliaException
     */

    public List<Story> getPublicStories(int page, int pageSize, BigInteger vendorId) throws AnaliaException {
        return storyFacadeLocal.getPublicStories(page, pageSize, vendorId);
    }

    /**
     * @param queryParam
     * @param page
     * @param pageSize
     * @return
     * @throws AnaliaException
     */

    public List<Story> searchStory(String queryParam, int page, int pageSize) throws AnaliaException {
        return storyFacadeLocal.searchStory(queryParam, page, pageSize);
    }


    /**
     * @return
     * @throws AnaliaException
     */

    public int totalPublicStoriesCount() throws AnaliaException {
        return storyFacadeLocal.totalPublicStoriesCount();
    }


    public List<Story> getStoriesWithIds(List<BigInteger> values) throws AnaliaException {
        return storyFacadeLocal.getStoriesWithIds(values);
    }

    @Transactional
    public Story getByExternalId(BigInteger storyId, BigInteger vendorId) throws AnaliaException {
        return storyFacadeLocal.getByExternalId(storyId, vendorId);
    }
}
