package com.analia.web.util;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.UserProfile;
import jakarta.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseUtils {

    public static Response buildErrorResponse(AnaliaException analiaException) {
        Log log = LogFactory.getLog(analiaException.getClass());
        log.error(analiaException.getMessage(), analiaException);

        String message = analiaException.getMessage();
        Error errorNotify = new Error(analiaException.getExceptionCode().getStatusCode(), message == null ? analiaException.getMessage() : message);

        int code = analiaException.getExceptionCode().getErrorCode().getStatusCode();
        return Response.status(code).entity(errorNotify).build();
    }

    public static Object createJsonForUserProfileKeys(List<UserProfile> userProfiles) {
        HashMap<String, Object> hashMap = new HashMap<>();
        for (UserProfile userProfile : userProfiles) {
            hashMap.put(userProfile.getKey(), userProfile.getValue());
        }
        return hashMap;
    }

    /**
     * @param m
     * @return
     */
    public static List<UserProfile> getUserProfile(Map<String, Object> m) throws AnaliaException {
        List<UserProfile> userProfiles = new ArrayList<>(m.size());

        List<String> keys = new ArrayList<String>(m.keySet());
        List<Object> values = new ArrayList<Object>(m.values());

        for (int i = 0; i < keys.size(); i++) {
            String key = (String) m.get("key");
            String value = (String) m.get("value");

            UserProfile userProfile = new UserProfile();
            userProfile.setValue(value);
            userProfile.setKey(key);
            userProfiles.add(userProfile);
        }
        return userProfiles;
    }


    public static HashMap<String, Object> createSucessResponse() {
        HashMap<String, Object> jsonResponseStatus = new HashMap<String, Object>();
        jsonResponseStatus.put("status", jsonResponseStatus);
        HashMap<String, Object> jsonResponse = new HashMap<String, Object>();
        jsonResponse.put("responseCode", "200");
        jsonResponse.put("messageKey", "com.analia.string.200");
        return jsonResponse;
    }

    public static Map<String, Object> createSucessResponseMatched(boolean matched) {
        HashMap<String, Object> jsonResponseStatus = new HashMap<String, Object>();
        jsonResponseStatus.put("matched", matched);
        return jsonResponseStatus;
    }

}
