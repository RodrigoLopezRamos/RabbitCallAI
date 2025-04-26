package com.analia.social.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Story;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.social.persistence.StoryFacadeLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class StoryFacade extends JPAPersistenceFacade<Story> implements StoryFacadeLocal {
    private static final String GET_STORIES_BY_GROUP_NAME = "getStoriesByGroupName";

    private static final String GET_PUBLIC_STORIES = "getPublicStories";

    private static final String GET_TOTAL_PUBLIC_STORIES_COUNT = "totalPublicStoriesCount";

    private static final String PARAM_STORY_ID = "storyId";

    private static final String PARAM_USER_ID = "userId";

    private static final String PARAM_GROUP_NAME = "groupName";

    private static final String PARAM_SEARCH_PUBLIC_STORIES = "searchPublicStories";

    private static final String PARAM_GET_STORY_VENDOR = "getStoryWithVendor";
    private static final String VENDOR_ID = "vendorId";


    @Inject
    private EntityManager entityManager;


    public StoryFacade() {
        super(Story.class);
    }





    public List<Story> getStoriesByGroupName(BigInteger userId, String groupName, int page, int pageSize) throws AnaliaException {
        Query query = entityManager.createNamedQuery(GET_STORIES_BY_GROUP_NAME);
        query.setMaxResults(pageSize);
        query.setFirstResult(page * pageSize);
        query.setParameter(PARAM_USER_ID, userId);
        query.setParameter(PARAM_GROUP_NAME, groupName);
        return (List<Story>) query.getResultList();
    }


    public List<Story> getPublicStories(int page, int pageSize, BigInteger vendorId) throws AnaliaException {
        Query query = entityManager.createNamedQuery(GET_PUBLIC_STORIES);
        query.setParameter(VENDOR_ID, vendorId);
        query.setMaxResults(pageSize);
        query.setFirstResult(page * pageSize);
        return (List<Story>) query.getResultList();
    }


    public Story getStoryById(BigInteger storyId) throws AnaliaException {
        if(storyId==null) {
            return null;
        }
        Story story = find(storyId);
        return story;
    }


    public int totalPublicStoriesCount() throws AnaliaException {
        Query query = entityManager.createNamedQuery(GET_TOTAL_PUBLIC_STORIES_COUNT);
        long count = (long) query.getSingleResult();
        return (int) count;
    }


    public List<Story> searchStory(String queryParam, int page, int pageSize) throws AnaliaException {
        Query query = entityManager.createNamedQuery(PARAM_SEARCH_PUBLIC_STORIES);
        query.setMaxResults(pageSize);
        query.setFirstResult(page * pageSize);
        return (List<Story>) query.getResultList();
    }


    public List<Story> getStoriesWithIds(List<BigInteger> values) throws AnaliaException {
   //     Query query = entityManager.createQuery("select s from Story s where s.externalId  IN :list");
      //  query.setParameter("list",values);
        return new ArrayList<>();//query.getResultList();
    }

    /**
     *
     * @param externalId
     * @param vendorId
     * @return
     * @throws AnaliaException
     */
   public Story getByExternalId(BigInteger externalId, BigInteger vendorId)throws  AnaliaException{
       Query query = entityManager.createNamedQuery(PARAM_GET_STORY_VENDOR);
       query.setParameter("externalId",externalId);
       query.setParameter("vendorId",vendorId);
       List result = query.getResultList();
       if(result.isEmpty()){
           return null;
       }
       return (Story) result.get(0);
   }
}
