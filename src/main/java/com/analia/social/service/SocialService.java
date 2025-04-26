package com.analia.social.service;

import com.analia.common.constants.Constants;
import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.model.*;
import com.analia.common.util.DateUtils;
import com.analia.media.core.FileSystemCoreLocal;
import com.analia.setttings.core.impl.SettingsCore;
import com.analia.social.core.SocialCoreLocal;
import com.analia.user.core.UserActivityCore;
import com.analia.user.core.UserCore;
import com.analia.user.service.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import kotlin.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class SocialService implements SocialServiceLocal {
    @Inject
    private SocialCoreLocal socialCoreLocal;

    @Inject
    private UserActivityCore userActivityCoreLocal;

    @Inject
    private UserService userServiceLocal;

    @Inject
    private UserCore userCoreLocal;

    @Inject
    private FileSystemCoreLocal fileSystemCoreLocal;

    @Inject
    private SettingsCore settingsCore;

    public List<StoryType> getStoryTypes() throws AnaliaException {
        return socialCoreLocal.getStoryTypes();
    }

    public List<Story> getMyStories(int page, int sortTypeId, Integer pageSize) throws AnaliaException {
        JSONArray stories = new JSONArray();
        List<String> keys = new ArrayList<>();
        List<UserProfile> userProfiles = userServiceLocal.getUserProfile();
        for (UserProfile userProfile : userProfiles) {
            String key = userProfile.getKey();
            if (key.contains("team")) {
                keys.add(key);
            }
        }

        for (String key : keys) {
        }
        return stories;
    }


    public List<Story> getPublicStories(int page, int pageSize, BigInteger vendorId) throws AnaliaException {
        return socialCoreLocal.getPublicStories(page, pageSize, vendorId);
    }


    public List<User> getUserByIds(BigInteger[] ids) throws AnaliaException {
        return userCoreLocal.getUserByIds(ids);
    }

    /**
     *
     */

    public List<User> getMyFriends() throws AnaliaException {
        User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        return userActivityCoreLocal.getUserPendingsForApprovalInGroup(user.getId(), Constants.GROUP_FRIENDS);
    }


    public List<User> getUsers() throws AnaliaException {
        User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        return userActivityCoreLocal.getUserPendingsForApprovalInGroup(user.getId(), Constants.GROUP_FRIENDS);
    }


    public Story saveStory(BigInteger storyId, BigInteger storyTypeId, BigInteger categoryId, String name, String description, String externalLink, double latitude,
                           double longitude, String title, boolean disabled, BigInteger vendorId) throws AnaliaException {

        User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        Date date = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_DATE_TIME, Date.class);

        UserLocation userLocation = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_LOCATION, UserLocation.class);
        Story story = storyId == null ? new Story() : socialCoreLocal.getStoryById(storyId);

        story.setAuthor(user.getPersona().getName());
        story.setCreatedBy(user.getId());
        story.setName(name);
        story.setCategoryId(categoryId);
        story.setStoryTypeId(storyTypeId);
        story.setDescription(description);
        story.setDirectoryId(storyId == null ? (fileSystemCoreLocal.saveDirectory(BigInteger.valueOf(Constants.NEW_INSTANCE_ID), user.getId(), null, vendorId).getId()) : story.getDirectoryId());
        story.setDisabled(disabled);
        story.setDescription(description);
        story.setExternalLink(externalLink);
        story.setLatitude(latitude);
        story.setLongitude(longitude);
        story.setNotBefore(date);
        story.setFavouriteCount(BigInteger.ZERO);
        story.setZoneId(userLocation.getZoneId());
        story.setNotAfter(DateUtils.getNextNDay(date, Constants.NOT_AFTER_POST_DAYS));
        story.setTitle(title);
        story = socialCoreLocal.saveStory(story);
        return story;
    }


    @Transactional
    public Story saveStory(BigInteger storyId, BigInteger storyTypeId, BigInteger categoryId, String name, String description, String externalLink, double latitude,
                           double longitude, String title, boolean disabled, BigInteger userId, BigInteger zoneId, String author, Date date, BigInteger externalId, BigInteger vendorId) throws AnaliaException {
        Story story = socialCoreLocal.getStoryById(storyId);
        if (story == null) {
            story = new Story();
            story.setVendorId(vendorId);
        }
        story.setAuthor(author);
        story.setCreatedBy(userId);
        story.setName(name);
        story.setCategoryId(categoryId);
        story.setStoryTypeId(storyTypeId);
        story.setDescription(description);
        story.setDirectoryId(story.getId() == null ? (fileSystemCoreLocal.saveDirectory(BigInteger.valueOf(Constants.NEW_INSTANCE_ID), BigInteger.ONE, null, vendorId).getId()) : story.getDirectoryId());
        story.setDisabled(disabled);
        story.setDescription(description);
        story.setExternalLink(externalLink);
        story.setLatitude(latitude);
        story.setLongitude(longitude);
        story.setFavouriteCount(BigInteger.ZERO);
        story.setLikeCount(BigInteger.ZERO);
        story.setNotBefore(date);
        story.setExternalId(externalId);
        story.setZoneId(zoneId);
        story.setNotAfter(DateUtils.getNextNDay(date, Constants.NOT_AFTER_POST_DAYS));
        story.setTitle(title);
        story = socialCoreLocal.saveStorySystem(story);
        return story;
    }


    public Story getStoryDetail(BigInteger storyId) throws AnaliaException {
        return socialCoreLocal.getStoryById(storyId);
    }


    public Story saveStory(Story story) throws AnaliaException {
        return socialCoreLocal.saveStory(story);
    }

    /**
     *
     */

    public List<User> getPossibleMatches(int page, int pageSize) throws AnaliaException {
        User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        String gender = user.getPersona().getGender().equals("female") ? "male" : "female";
        return userCoreLocal.getUserProfileMatchingTags(user.getId(), gender);
    }


    public List<Story> searchStory(String queryParam, int page, int pageSize) throws AnaliaException {
        return socialCoreLocal.searchStory(queryParam, page, pageSize);
    }

    /**
     *
     */

    public void removeFriend(BigInteger userId) throws AnaliaException {
        Grouping grouping = userActivityCoreLocal.getGroupingByName(Constants.GROUP_FRIENDS);
        userActivityCoreLocal.removeUserFromGrouping(grouping.getId(), userId);
    }

    /**
     *
     */

    public void addFriend(BigInteger userId) throws AnaliaException {
        Grouping grouping = userActivityCoreLocal.getGroupingByName(Constants.GROUP_FRIENDS);
        UserGrouping userGrouping = userActivityCoreLocal.getUserGropingByGroupIdAndUserId(grouping.getId(), userId);
        userGrouping.setDisabled(true);
        userActivityCoreLocal.saveUserGrouping(userGrouping);
    }


    public boolean addMatch(BigInteger userId) throws AnaliaException {
        User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);

        //My group of matches
        Grouping myGroupOfMatching = userActivityCoreLocal.getGroupingByNameWithUserId(Constants.GROUP_MATCHING, user.getId());

        if (myGroupOfMatching == null) {
            return false;
        }

        UserGrouping myUSerGroupingOfMatching = userActivityCoreLocal.getUserGropingByGroupIdAndUserId(myGroupOfMatching.getId(), userId);

        //User's group of matches
        Grouping userGroupOfMatching = userActivityCoreLocal.getGroupingByNameWithUserId(Constants.GROUP_MATCHING, userId);
        UserGrouping userUserGroupingOfMatching = userActivityCoreLocal.getUserGropingByGroupIdAndUserIdNull(userGroupOfMatching.getId(), user.getId());

        //MATCHED YOU HAVE FOUND LOVE :)
        if (myUSerGroupingOfMatching != null && userUserGroupingOfMatching != null) {

            //Remove from matching
            userActivityCoreLocal.removeUserFromGrouping(myUSerGroupingOfMatching.getId(), userId);
            userActivityCoreLocal.removeUserFromGrouping(userUserGroupingOfMatching.getId(), user.getId());

            Grouping myUserGroupOfFriends = userActivityCoreLocal.getGroupingByNameWithUserId(Constants.GROUP_FRIENDS, user.getId());

            Grouping userGroupOfFriends = userActivityCoreLocal.getGroupingByNameWithUserId(Constants.GROUP_FRIENDS, userId);

            userActivityCoreLocal.getUserGropingByGroupIdAndUserId(myUserGroupOfFriends.getId(), userId);

            userActivityCoreLocal.getUserGropingByGroupIdAndUserId(userGroupOfFriends.getId(), user.getId());

            return true;

        }
        userActivityCoreLocal.getUserGropingByGroupIdAndUserId(userGroupOfMatching.getId(), userId);
        return false;
    }


    public void removeMatch(BigInteger userId) throws AnaliaException {
        User user = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        //My group of matches
        Grouping myGroupOfMatching = userActivityCoreLocal.getGroupingByNameWithUserId(Constants.GROUP_MATCHING, user.getId());
        if (myGroupOfMatching == null) {
            return;
        }

        UserGrouping myUSerGroupingOfMatching = userActivityCoreLocal.getUserGropingByGroupIdAndUserIdNull(myGroupOfMatching.getId(), userId);
        if (myUSerGroupingOfMatching != null) {
            userActivityCoreLocal.removeUserFromGrouping(myUSerGroupingOfMatching.getId(), userId);
        }
    }


    public List<Story> getStoriesWithIds(List<BigInteger> values) throws AnaliaException {
        return socialCoreLocal.getStoriesWithIds(values);
    }


    public int totalPublicStoriesCount() throws AnaliaException {
        return socialCoreLocal.totalPublicStoriesCount();
    }

    public Story getByExternalId(BigInteger storyId, BigInteger vendorId) throws AnaliaException {
        return socialCoreLocal.getByExternalId(storyId, vendorId);
    }


    public void disableStory(Story story) throws AnaliaException {
        story .setDisabled(true);
        socialCoreLocal.saveStory(story);
    }

}
