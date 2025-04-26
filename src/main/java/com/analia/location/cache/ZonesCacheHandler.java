package com.analia.location.cache;

import com.analia.cache.handler.CacheHandler;
import com.analia.common.cache.CacheKey;
import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Zone;
import com.analia.location.persistence.ZoneFacadeLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;


@ApplicationScoped
@Named("ZonesCacheHandler")
public class ZonesCacheHandler implements CacheHandler<Zone> {

    @Inject
    private ZoneFacadeLocal zoneFacadeLocal;


    public List<Zone> execute(CacheKey cacheKey) throws AnaliaException {
        return zoneFacadeLocal.findAllJPa();
    }
}
