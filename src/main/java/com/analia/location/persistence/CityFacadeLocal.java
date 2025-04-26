package com.analia.location.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.City;
import com.analia.common.persistence.PersistenceFacade;


import java.util.List;

public interface CityFacadeLocal extends PersistenceFacade<City> {
    /**
     * @return
     * @throws AnaliaException
     */
    City getCityByName(String name) throws AnaliaException;

    /**
     * @param querySearchParam
     * @return
     * @throws AnaliaException
     */
    List<City> getCities(String querySearchParam) throws AnaliaException;


}
