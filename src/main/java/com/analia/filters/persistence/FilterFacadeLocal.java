package com.analia.filters.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Filter;



public interface FilterFacadeLocal extends com.analia.common.persistence.PersistenceFacade<Filter> {
    /**
     * @param name
     * @return
     * @throws AnaliaException
     */
    Filter getFilterByName(String name) throws AnaliaException;


}