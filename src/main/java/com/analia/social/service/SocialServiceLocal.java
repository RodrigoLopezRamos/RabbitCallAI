package com.analia.social.service;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Story;
import com.analia.common.model.StoryType;
import com.analia.common.model.User;
import org.json.simple.JSONArray;


import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public interface SocialServiceLocal {

    /**
     * @return
     * @throws AnaliaException
     */
    List<StoryType> getStoryTypes() throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    List<Story> getMyStories(int page, int sortTypeId, Integer pageSize) throws AnaliaException;

    /**
     * @return
     */
    List<Story> getPublicStories(int page, int pageSize, BigInteger vendorId) throws AnaliaException;


    List<User> getUserByIds(BigInteger[] ids) throws AnaliaException;

    /**
     * @return
     */
    List<User> getMyFriends() throws AnaliaException;


    /**
     * @return
     * @throws AnaliaException
     */
    List<User> getUsers() throws AnaliaException;


    /**
     * @param page
     * @param pageSize
     * @return
     * @throws AnaliaException
     */
    List<User> getPossibleMatches(int page, int pageSize) throws AnaliaException;


    /**
     * @param queryParam
     * @param page
     * @param pageSize
     * @return
     * @throws AnaliaException
     */
    List<Story> searchStory(String queryParam, int page, int pageSize) throws AnaliaException;

    /**
     * @param storyId
     * @param storyTypeId
     * @param name
     * @param description
     * @param externalLink
     * @param latitude
     * @param longitude
     * @param title
     * @return
     * @throws AnaliaException
     */
    Story saveStory(BigInteger storyId, BigInteger storyTypeId, BigInteger categoryId, String name, String description, String externalLink, double latitude, double longitude, String title, boolean disabled,BigInteger vendorId) throws AnaliaException;



    Story saveStory(BigInteger storyId, BigInteger storyTypeId, BigInteger categoryId, String name, String description, String externalLink, double latitude,
                    double longitude, String title, boolean disabled, BigInteger userId , BigInteger zoneId , String author , Date date , BigInteger externalId, BigInteger vendorId) throws AnaliaException;
    /**
     * @param storyId
     * @return
     * @throws AnaliaException
     */
    Story getStoryDetail(BigInteger storyId) throws AnaliaException;



    Story saveStory(Story story)throws AnaliaException;
    /**
     *
     * @param storyId
     * @return
     * @throws AnaliaException
     */
    Story getByExternalId(BigInteger storyId , BigInteger vendorId) throws AnaliaException;


    void  disableStory(Story  story)throws AnaliaException;


    /**
     * @param userId
     * @throws AnaliaException
     */
    void removeFriend(BigInteger userId) throws AnaliaException;

    /**
     * @param userId
     * @throws AnaliaException
     */
    void addFriend(BigInteger userId) throws AnaliaException;


    boolean addMatch(BigInteger userId) throws AnaliaException;


    void removeMatch(BigInteger userId) throws AnaliaException;



    List<Story> getStoriesWithIds(List<BigInteger> values) throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    int totalPublicStoriesCount() throws AnaliaException;


}
