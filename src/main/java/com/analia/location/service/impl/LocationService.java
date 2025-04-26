package com.analia.location.service.impl;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.City;
import com.analia.common.model.Location;
import com.analia.common.model.Zone;
import com.analia.location.core.LocationCoreLocal;
import com.analia.location.service.LocationServiceLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


import java.math.BigInteger;
import java.util.List;

@ApplicationScoped
public class LocationService implements LocationServiceLocal {
    @Inject
    private LocationCoreLocal locationCoreLocal;


    public Location getLocation(BigInteger locationId) throws AnaliaException {
        return locationCoreLocal.getLocation(locationId);
    }


    public List<Zone> getAllZones() throws AnaliaException {
        return locationCoreLocal.getZonesFromCache();
    }


    public Location saveLocation(Location location) throws AnaliaException {
        return locationCoreLocal.saveLocation(location);
    }


    public City getCityByName(String name) throws AnaliaException {
        return locationCoreLocal.getCityByName(name);
    }


    public City saveCity(City city) throws AnaliaException {
        return locationCoreLocal.saveCity(city);
    }


    public Zone getZoneByName(String name) throws AnaliaException {
        return locationCoreLocal.getZoneByName(name);
    }


    public Zone saveZone(Zone zone) throws AnaliaException {
        return locationCoreLocal.save(zone);
    }


    public Zone getZoneById(BigInteger zoneId) throws AnaliaException {
        return locationCoreLocal.getZoneById(zoneId);
    }


    public List<City> getCities(String querySearchParam) throws AnaliaException {
        return locationCoreLocal.getCities(querySearchParam);
    }
}
