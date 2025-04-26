package com.analia.cache.core;

import com.analia.cache.handler.CacheHandler;
import com.analia.cache.persistence.CacheConfigFacade;
import com.analia.cache.util.CacheConfigEnum;
import com.analia.common.cache.AnaliaCacheableEntity;
import com.analia.common.cache.CacheData;
import com.analia.common.cache.CacheKey;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.infrastructure.BeanFactory;
import com.analia.common.model.CacheConfig;
import com.analia.common.util.SHA256Util;
import com.analia.setttings.cache.SettingsCacheHandler;
import io.quarkus.arc.Lock;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Layer to support cache functionality
 *
 * @author Rodrigo Lopez
 */
@Singleton
public class CacheCore {

    @Inject
    private CacheConfigFacade cacheConfigFacade;

    /**
     * Cache Map
     */
    private final ConcurrentHashMap<String, CacheData<?>> cache = new ConcurrentHashMap<>();

    /**
     *
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Lock(Lock.Type.READ)
    public <T extends AnaliaCacheableEntity> List<T> getData(CacheConfigEnum cacheConfigEnum, CacheKey cacheKey) throws AnaliaException {
        String key = SHA256Util.SHA256(cacheKey.toString());
        CacheConfig cacheConfig = cacheConfigFacade.getCacheConfigByIdAndEnabled(BigInteger.valueOf(cacheConfigEnum.getId()));

        if (cacheConfig == null) {
            throw new AnaliaException(ExceptionCode.SERVER_ERROR, "Cache configuration is  messed up! Baboso fix it asap!");
        }
        System.out.println("CACHE HANDLER "+ cacheConfig.getHandler());
        CacheHandler<T>  cacheHandler;
        cacheHandler = (CacheHandler<T>) CDI.current().select(NamedLiteral.of(cacheConfig.getHandler())).get();
        int version = cacheConfig.getVersion();
        CacheData<T> cacheData = null;
        if ((cacheData = (CacheData<T>) cache.get(key)) != null) {
            if (version > cacheData.getVersion()) {
                return clone(prepareData(key, version, cacheHandler.execute(cacheKey)));
            }
            return cacheData.getData();
        }
        return clone(prepareData(key, version, cacheHandler.execute(cacheKey)));
    }

    /**
     * Wraps version and data in a common object for further processing .
     *
     * @param key
     * @param version
     * @param t
     * @return
     */
    @SuppressWarnings({"rawtypes"})
    private <T extends AnaliaCacheableEntity> CacheData<T> prepareData(String key, int version, List<T> t) {
        CacheData<T> cacheData = new CacheData<>();
        cacheData.setData(t);
        cacheData.setVersion(version);
        cache.put(key, cacheData);
        return cacheData;
    }

    /**
     * @param cacheData
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private <T extends AnaliaCacheableEntity> List<T> clone(CacheData<T> cacheData) {
        List<T> data = cacheData.getData();
        List<T> cloneList = new ArrayList<>(data.size());
        for (T t : data) {
            try {
                cloneList.add((T) t.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return cloneList;
    }
}
