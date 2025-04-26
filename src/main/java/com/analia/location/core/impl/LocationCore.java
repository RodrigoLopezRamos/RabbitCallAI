package com.analia.location.core.impl;

import com.analia.cache.core.CacheCore;
import com.analia.cache.util.CacheConfigEnum;
import com.analia.common.cache.CacheKey;
import com.analia.common.constants.Constants;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.City;
import com.analia.common.model.Location;
import com.analia.common.model.Zone;
import com.analia.location.core.LocationCoreLocal;
import com.analia.location.persistence.CityFacadeLocal;
import com.analia.location.persistence.LocationFacadeLocal;
import com.analia.location.persistence.ZoneFacadeLocal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class LocationCore implements LocationCoreLocal {

    @Inject
    private LocationFacadeLocal locationFacadeLocal;

    @Inject
    private ZoneFacadeLocal zoneFacadeLocal;

    @Inject
    private CacheCore cacheCoreLocal;

    @Inject
    private CityFacadeLocal cityFacadeLocal;


    public Location getLocation(BigInteger locationId) throws AnaliaException {
        return locationFacadeLocal.find(locationId);
    }


    public List<Zone> getZonesFromDataBase() throws AnaliaException {
        return zoneFacadeLocal.findAllJPa();
    }


    public List<Zone> getZonesFromCache() throws AnaliaException {
        CacheKey cacheKey = new CacheKey();
        cacheKey.put("ZONES", "ZONES");
        return cacheCoreLocal.getData(CacheConfigEnum.ZONES, cacheKey);
    }


    public Location saveLocation(Location location) throws AnaliaException {
        Location locationDatabase = null;
        if (location == null) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, "Object Location must be not null!");
        }
        if (location.getId() != null) {
            locationDatabase = locationFacadeLocal.find(location.getId());
        }
        if (locationDatabase == null) {
            location.setCreatedDatetime(new Date());
            location.setCreatedBy(new BigInteger(Integer.toString(Constants.SYSTEM_USER_ID)));
        } else {
            location.setCreatedDatetime(locationDatabase.getCreatedDatetime());
        }
        locationFacadeLocal.save(location);
        locationFacadeLocal.flush();
        return location;
    }


    public City getCityByName(String name) throws AnaliaException {
        return cityFacadeLocal.getCityByName(name);
    }

    /**
     *
     */

    public City saveCity(City city) throws AnaliaException {
        cityFacadeLocal.save(city);
        cityFacadeLocal.flush();
        return city;
    }


    public Zone getZoneByName(String name) throws AnaliaException {
        return zoneFacadeLocal.getZoneByName(name);
    }


    public Zone save(Zone zone) throws AnaliaException {
        if (zone == null) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, "Zone object must be not null!");
        }
        zoneFacadeLocal.save(zone);
        zoneFacadeLocal.flush();
        return zone;
    }


    public Zone getZoneById(BigInteger zoneId) throws AnaliaException {
        return zoneFacadeLocal.find(zoneId);
    }


    public List<City> getCities(String querySearchParam) throws AnaliaException {
        return cityFacadeLocal.getCities(querySearchParam);
    }

}
