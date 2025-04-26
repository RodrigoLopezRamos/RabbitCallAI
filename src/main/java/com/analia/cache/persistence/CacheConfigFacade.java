package com.analia.cache.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.CacheConfig;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.math.BigInteger;


@ApplicationScoped
public class CacheConfigFacade extends JPAPersistenceFacade<CacheConfig> {

    private static final String QUERY_GET_CACHE_CONFIG_ENABLE = "getCacheConfigEnable";

    private static final String PARAM_CACHE_CONFIG_ID = "cacheId";


    @Inject
    private EntityManager entityManager;

    public CacheConfigFacade() {
        super(CacheConfig.class);
    }





    public CacheConfig getCacheConfigByIdAndEnabled(BigInteger cacheId) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_GET_CACHE_CONFIG_ENABLE, new JpqlParameter(PARAM_CACHE_CONFIG_ID, cacheId));
    }

}
