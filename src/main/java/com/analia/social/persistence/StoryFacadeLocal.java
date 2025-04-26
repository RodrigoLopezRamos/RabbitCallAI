package com.analia.social.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Story;
import com.analia.common.persistence.PersistenceFacade;


import java.math.BigInteger;
import java.util.List;

public interface StoryFacadeLocal extends PersistenceFacade<Story> {
    /**
     * @param userId
     * @param groupName
     * @return
     * @throws AnaliaException
     */
    List<Story> getStoriesByGroupName(BigInteger userId, String groupName, int page, int pageSize) throws AnaliaException;


    /**
     * @return
     * @throws AnaliaException
     */
    List<Story> getPublicStories(int page, int pageSize, BigInteger vendorId) throws AnaliaException;


    /**
     * @param storyId
     * @return
     * @throws AnaliaException
     */
    Story getStoryById(BigInteger storyId) throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    int totalPublicStoriesCount() throws AnaliaException;

    /**
     * @param queryParam
     * @return
     * @throws AnaliaException
     */
    List<Story> searchStory(String queryParam, int page, int pageSize) throws AnaliaException;


    List<Story> getStoriesWithIds(List<BigInteger> values)throws AnaliaException;

    Story getByExternalId(BigInteger storyId, BigInteger vendorId)throws  AnaliaException;

}
