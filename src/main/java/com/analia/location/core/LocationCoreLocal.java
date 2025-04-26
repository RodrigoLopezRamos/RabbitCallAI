package com.analia.location.core;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.City;
import com.analia.common.model.Location;
import com.analia.common.model.Zone;


import java.math.BigInteger;
import java.util.List;

public interface LocationCoreLocal {

    /**
     * @return
     */
    List<Zone> getZonesFromDataBase() throws AnaliaException;


    /**
     * @return
     * @throws AnaliaException
     */
    List<Zone> getZonesFromCache() throws AnaliaException;

    /**
     * @param locationId
     * @return
     * @throws AnaliaException
     */
    Location getLocation(BigInteger locationId) throws AnaliaException;

    /**
     * @param location
     * @return
     * @throws AnaliaException
     */
    Location saveLocation(Location location) throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    City getCityByName(String name) throws AnaliaException;

    /**
     * @param city
     * @return
     * @throws AnaliaException
     */
    City saveCity(City city) throws AnaliaException;

    /**
     * @param name
     * @return
     * @throws AnaliaException
     */
    Zone getZoneByName(String name) throws AnaliaException;

    /**
     * @param zone
     * @return
     * @throws AnaliaException
     */
    Zone save(Zone zone) throws AnaliaException;

    /**
     * @param id
     * @return
     * @throws AnaliaException
     */
    Zone getZoneById(BigInteger zoneId) throws AnaliaException;

    /**
     * @param querySearchParam
     * @return
     * @throws AnaliaException
     */
    List<City> getCities(String querySearchParam) throws AnaliaException;


}
