package com.analia.web.util;

import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.Filter;
import com.analia.common.model.FilterAttribute;
import com.analia.common.util.Base26;
import com.analia.common.util.DataTypeUtil;
import com.analia.filters.service.FilterServiceLocal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestUtils {
    @SuppressWarnings("unchecked")
    public static <T> T getValueForKey(String key, Map<?, ?> map, Class<T> clazz) {
        return getValueForKey(key, map) == null ? null : (T) getValueForKey(key, map);
    }

    public static Object getValueForKey(String key, Map<?, ?> map) {
        if (key == null) {
            throw new NullPointerException("Key is null.");
        }
        if (map != null) {
            return map.get(key);
        }
        return null;
    }

    public static String getStringForKey(String key, Map<?, ?> map) {
        Object result = RequestUtils.getValueForKey(key, map);
        return (result != null ? result.toString() : null);
    }

    public static Integer getIntegerForKey(String key, Map<?, ?> map) {
        Object result = RequestUtils.getValueForKey(key, map);
        return (result != null ? Integer.valueOf(result.toString()) : null);
    }

    public static Byte getByteForKey(String key, Map<?, ?> map) {
        Object result = RequestUtils.getValueForKey(key, map);
        return (result != null ? Byte.valueOf(result.toString()) : null);
    }

    public static Boolean getBooleanForKey(String key, Map<?, ?> map) {
        Object result = RequestUtils.getValueForKey(key, map);
        return (result != null ? Boolean.valueOf(result.toString()) : null);
    }

    public static Double getDoubleForKey(String key, Map<?, ?> map) {
        Object result = RequestUtils.getValueForKey(key, map);
        return (result != null ?  new Double(result.toString()) : null);
    }

    public static Long getLongForKey(String key, Map<?, ?> map) {
        Object result = RequestUtils.getValueForKey(key, map);
        return (result != null ? Long.valueOf(result.toString()) : null);
    }

    public static String getMandatoryStringForKey(String key, Map<?, ?> map) throws AnaliaException {
        String result = RequestUtils.getStringForKey(key, map);
        RequestUtils.validateNotNull(key, result);
        return result;
    }

    public static int getMandatoryIntForKey(String key, Map<?, ?> map) throws AnaliaException {
        Integer result = RequestUtils.getIntegerForKey(key, map);
        RequestUtils.validateNotNull(key, result);
        return result.intValue();
    }

    public static byte getMandatoryByteForKey(String key, Map<?, ?> map) throws AnaliaException {
        Byte result = RequestUtils.getByteForKey(key, map);
        RequestUtils.validateNotNull(key, result);
        return result.byteValue();
    }

    public static boolean getMandatoryBooleanForKey(String key, Map<?, ?> map) throws AnaliaException {
        Boolean result = RequestUtils.getBooleanForKey(key, map);
        RequestUtils.validateNotNull(key, result);
        return result.booleanValue();
    }

    public static double getMandatoryDoubleForKey(String key, Map<?, ?> map) throws AnaliaException {
        Double result = RequestUtils.getDoubleForKey(key, map);
        RequestUtils.validateNotNull(key, result);
        return result.doubleValue();
    }

    public static void validateNotNull(String key, Object value) throws AnaliaException {
        if (value == null) {
            throw new AnaliaException(ExceptionCode.PARAMETER_IS_MANDATORY, " value for key: " + key + " is null");
        }
    }

    public static List<Filter> getFilters(List<HashMap<String, Object>> listParameters, FilterServiceLocal filterServiceLocal) throws AnaliaException {
        List<Filter> filters = new ArrayList<Filter>();
        if (listParameters == null || listParameters.isEmpty()) {
            return filters;
        }

        for (HashMap<String, Object> jsonFilter : listParameters) {
            String filterCode = (String) jsonFilter.get("filterCode");
            int filterId = Base26.decode(filterCode).intValue();
            Filter filter = filterServiceLocal.getFilterById(filterId);
            if (filter != null && !filter.isDisabled()) {
                List<FilterAttribute> filterParameters = filter.getFilterAttributes();
                @SuppressWarnings("unchecked")
                List<HashMap<String, Object>> parameters = (List<HashMap<String, Object>>) jsonFilter.get("attributes");
                int index = 0;
                if (parameters != null) {
                    for (HashMap<String, Object> hashMap : parameters) {
                        FilterAttribute filterParameter = filterParameters.get(index);
                        filterParameter.setValue(DataTypeUtil.convertData(filterParameter.getParameterType(), filterParameter.isSingleValue(), hashMap.get("value")));
                        index++;
                    }
                }
                filters.add(filter);
            }
        }
        return filters;
    }

}
