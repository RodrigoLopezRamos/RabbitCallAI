package com.analia.web.rs.social;

import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.model.*;
import com.analia.common.util.Base26;
import com.analia.location.service.LocationServiceLocal;
import com.analia.media.service.FileSystemServiceLocal;
import com.analia.social.service.SocialServiceLocal;
import com.analia.user.service.UserService;
import com.analia.web.rs.AnaliaResource;
import com.analia.web.util.RequestUtils;
import com.analia.web.util.ResponseUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.json.simple.JSONArray;

import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/social")
@ApplicationScoped
public class SocialResource extends AnaliaResource {
    public static final String USER_CODE = "userCode";
    public static final String MEDIA_URL = "mediaUrl";
    public static final String SEQUENCE = "sequence";
    public static final String USER_ID = "userId";
    public static final String MEDIA = "media";
    private static final String USER_MATCH_ABOUT_ABOUTME = "user.match.about.aboutme";
    private static final String USER_MATCH_TAGS = "user.match.tags.";
    private static final String NAME = "name";
    private static final String FULL_NAME = "fullName";
    private static final String DESCRIPTION = "description";
    private static final String TAGS = "tags";
    private static final String PAGE = "page";
    private static final String PAGE_SIZE = "pageSize";
    private static final String LAST_NAME = "lastName";
    private static final String STORY_TYPE_CODE = "storyTypeCode";

    public static String MATCH_PREFIX_ABOUT = "user.match.about.";

    public static String MATCH_PREFIX_TAG = "user.match.tags.";

    @Inject
    private SocialServiceLocal socialServiceLocal;

    @Inject
    private UserService userServiceLocal;

    @Inject
    private FileSystemServiceLocal fileSystemServiceLocal;

    @Inject
    private LocationServiceLocal locationServiceLocal;

    @Inject
    private FileSystemServiceLocal mediaServiceLocal;

    /**
     * @param input
     * @return
     */
    @POST
    @Path("/story/types.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getStoryTypes(Map<String, Object> input) {
        Response response = null;
        try {
            List<HashMap<String, Object>> result = new ArrayList<>();
            List<StoryType> storyTypes = socialServiceLocal.getStoryTypes();
            for (StoryType storyType : storyTypes) {
                HashMap<String, Object> storyJson = new HashMap<>();
                storyJson.put(STORY_TYPE_CODE, Base26.encode(storyType.getId()));
                storyJson.put(NAME, storyType.getName());
                result.add(storyJson);
            }
            response = Response.ok(result).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);

        }
        return response;
    }


    /**
     * @param input
     * @return
     */
    @POST
    @Path("/story/save.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response saveStory(Map<String, Object> input) {
        Response response = null;
        try {

            BigInteger vendorId = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.VENDOR_ID, BigInteger.class);

            String storyTypeCode = RequestUtils.getStringForKey(STORY_TYPE_CODE, input);
            String storyCode = RequestUtils.getStringForKey("storyCode", input);
            String name = RequestUtils.getStringForKey(NAME, input);
            String description = RequestUtils.getStringForKey("description", input);
            String externalLink = RequestUtils.getStringForKey("externalLink", input);
            String latitude = RequestUtils.getStringForKey("latitude", input);
            String longitude = RequestUtils.getStringForKey("longitude", input);
            String title = RequestUtils.getStringForKey("title", input);
            boolean disabled = RequestUtils.getMandatoryBooleanForKey("disabled", input);
            String categoryCode = RequestUtils.getStringForKey("categoryCode", input);

            BigInteger storyId = storyCode == null ? null : Base26.decode(storyCode);
            BigInteger storyTypeId = storyTypeCode == null ? BigInteger.valueOf(0) : Base26.decode(storyTypeCode);
            BigInteger categoryId = categoryCode == null ? BigInteger.valueOf(0) : Base26.decode(categoryCode);

            Story story = socialServiceLocal.saveStory(storyId, storyTypeId, categoryId, name, description, externalLink, latitude != null ? Double.parseDouble(latitude) : 0, longitude != null ? Double.parseDouble(longitude) : 0, title, disabled, vendorId);
            HashMap<String, Object> result = new HashMap<>();
            result.put("storyCode", Base26.encode(story.getId()));
            result.put("directoryCode", Base26.encode(story.getDirectoryId()));
            response = Response.ok(result).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }


    /**
     * @param input
     * @return
     */
    @POST
    @Path("/story/detail.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getStoryDetail(Map<String, Object> input) {
        Response response = null;
        try {
            String storyCode = RequestUtils.getStringForKey("storyCode", input);
            BigInteger storyId = storyCode == null ? null : Base26.decode(storyCode);
            HashMap<String, Object> result = new HashMap<>();
            if (storyId == null) {
                return Response.ok(result).build();
            }
            Story story = socialServiceLocal.getStoryDetail(storyId);

            if (story == null) {
                return Response.ok(result).build();
            }
            if (story.isDisabled()) {
                return Response.ok(result).build();
            }

            result.put("storyCode", Base26.encode(story.getId()));
            result.put("directoryCode", Base26.encode(story.getDirectoryId()));
            result.put("media", buildResponseForMedias(fileSystemServiceLocal, story.getDirectoryId()));
            result.put("description", story.getDescription());
            result.put("favouriteCount", story.getFavouriteCount());
            result.put("likeCount", story.getLikeCount());
            result.put("latitude", story.getLatitude());
            result.put("longitude", story.getLongitude());
            result.put(NAME, story.getName());
            result.put("externalLink", story.getExternalLink());
            result.put("title", story.getTitle());
            result.put("author", story.getAuthor());
            result.put("categoryCode", Base26.encode(story.getCategoryId()));
            result.put("createdDatetime", story.getCreatedDatetime());
            result.put(STORY_TYPE_CODE, Base26.encode(story.getStoryTypeId()));
            Zone zone = locationServiceLocal.getZoneById(story.getZoneId());
            result.put("zoneName", zone.getName());
            response = Response.ok(result).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }


    /**
     * @param input
     * @return
     */
    @POST
    @Path("/timeline.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getTimeline(Map<String, Object> input) {
        Response response;
        try {
            List<HashMap<String, Object>> result = new ArrayList<>();
            int page = RequestUtils.getMandatoryIntForKey("page", input);
            int sortTypeId = RequestUtils.getMandatoryIntForKey("sortTypeId", input);
            Integer pageSize = RequestUtils.getIntegerForKey("pageSize", input);
            List<Story> stories = socialServiceLocal.getMyStories(page, sortTypeId, pageSize);
            response = Response.ok(stories).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }


    private void getTeamsFeed(){

    }

    /**
     * @param input
     * @return
     */
    @POST
    @Path("/friends/add.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addFriend(Map<String, Object> input) {
        Response response = null;
        try {
            String userCode = RequestUtils.getMandatoryStringForKey(USER_CODE, input);
            BigInteger userId = Base26.decode(userCode);
            socialServiceLocal.addFriend(userId);
            response = Response.ok(ResponseUtils.createSucessResponse()).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }

    /**
     * @param input
     * @return
     */
    @POST
    @Path("/friends/remove.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response removeFriend(Map<String, Object> input) {
        Response response = null;
        try {
            String userCode = RequestUtils.getMandatoryStringForKey(USER_CODE, input);
            BigInteger userId = Base26.decode(userCode);
            socialServiceLocal.removeFriend(userId);
            response = Response.ok(ResponseUtils.createSucessResponse()).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }


    /**
     * @param input
     * @return
     */
    @POST
    @Path("/friends/matching/add.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addMatch(Map<String, Object> input) {
        Response response = null;
        try {
            String userCode = RequestUtils.getMandatoryStringForKey(USER_CODE, input);
            BigInteger userId = Base26.decode(userCode);
            response = Response.ok(ResponseUtils.createSucessResponseMatched(socialServiceLocal.addMatch(userId))).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }

    /**
     * @param input
     * @return
     */
    @POST
    @Path("/friends/matching/remove.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response removeMatch(Map<String, Object> input) {
        Response response = null;
        try {
            String userCode = RequestUtils.getMandatoryStringForKey(USER_CODE, input);
            BigInteger userId = Base26.decode(userCode);
            socialServiceLocal.removeMatch(userId);
            response = Response.ok(ResponseUtils.createSucessResponse()).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }


    /**
     * @return
     */
    @POST
    @Path("/friends/matching/all.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getPossibleMatches(Map<String, Object> input) {
        Response response = null;
        try {
            int page = RequestUtils.getMandatoryIntForKey(PAGE, input);
            int pageSize = RequestUtils.getMandatoryIntForKey(PAGE_SIZE, input);

            Map<BigInteger, User> mapUser = socialServiceLocal.getPossibleMatches(page, pageSize)
                    .stream()
                    .collect(Collectors.toMap(User::getId, user -> user));

            Map<String, String> mapUserProfiles = userServiceLocal.getUserProfileForPossibleMatchingUsers(new ArrayList<>(mapUser.keySet()))
                    .stream()
                    .collect(Collectors.toMap(UserProfile::mapKey, UserProfile::getValue));

            List<Map<String, Object>> jsonResponse = new ArrayList<>();
            for (User user : mapUser.values()) {
                UserDirectory userDirectory = userServiceLocal.getUserDirectoryByUserId(user.getId());
                Map<String, Object> jsonUser = new HashMap<>();
                jsonUser.put(USER_CODE, Base26.encode(user.getId()));
                jsonUser.put(NAME, user.getPersona().getName());
                jsonUser.put(FULL_NAME, user.getPersona().getName());
                jsonUser.put(DESCRIPTION, mapUserProfiles.get(USER_MATCH_ABOUT_ABOUTME.concat(String.valueOf(user.getId()))));
                jsonUser.put(TAGS, mapUserProfiles.get(USER_MATCH_TAGS.concat(String.valueOf(user.getId()))));
                jsonUser.put(MEDIA, buildResponseForMedias(fileSystemServiceLocal, userDirectory.getDirectoryId()));
                jsonResponse.add(jsonUser);
            }
            response = Response.ok(jsonResponse).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }

    /**
     * @return
     */
    @POST
    @Path("/friends/request/all.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getFriendRequests() {
        Response response = null;
        try {
            List<HashMap<String, Object>> result = new ArrayList<>();
            List<User> userGroupings = socialServiceLocal.getMyFriends();
            for (User user : userGroupings) {
                HashMap<String, Object> userGroupingJson = new HashMap<>();
                userGroupingJson.put(USER_CODE, Base26.encode(user.getId()));
                userGroupingJson.put(NAME, user.getPersona().getName());
                userGroupingJson.put(LAST_NAME, user.getPersona().getLastName());
                result.add(userGroupingJson);
            }
            response = Response.ok(result).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }


    /**
     * @return
     */
    @POST
    @Path("/friends/all.s")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getFriendAll() {
        Response response = null;
        try {
            List<Map<String, Object>> result = new ArrayList<>();
            List<User> userGroupings = socialServiceLocal.getMyFriends();
            for (User user : userGroupings) {
                HashMap<String, Object> userGroupingJson = new HashMap<>();
                userGroupingJson.put(USER_CODE, Base26.encode(user.getId()));
                userGroupingJson.put(NAME, user.getPersona().getName());
                userGroupingJson.put(LAST_NAME, user.getPersona().getLastName());
                UserDirectory userDirectory = userServiceLocal.getUserDirectoryByUserId(user.getId());
                userGroupingJson.put(MEDIA, buildResponseForMedias(fileSystemServiceLocal, userDirectory.getDirectoryId()));
                result.add(userGroupingJson);
            }
            response = Response.ok(result).build();
        } catch (AnaliaException e) {
            response = ResponseUtils.buildErrorResponse(e);
        }
        return response;
    }
}
