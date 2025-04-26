package com.analia.common.util;

import com.analia.common.model.AnaliaEntity;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUtil {

    /**
     * No instances allowed
     */
    private MapUtil() {
    }

    /**
     * Convert list to hashMap
     *
     * @param list
     * @return
     */
    public static Map<?, ?> convertListToBooleanMapKey(List<? extends AnaliaEntity> list) {
        Map<BigInteger, Boolean> result = new HashMap<>();
        for (AnaliaEntity analiaEntity : list) {
            result.put(analiaEntity.getId(), true);
        }
        return result;
    }


}
