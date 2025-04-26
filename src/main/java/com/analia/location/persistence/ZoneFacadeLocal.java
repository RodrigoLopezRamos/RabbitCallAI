package com.analia.location.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Zone;
import com.analia.common.persistence.PersistenceFacade;

public interface ZoneFacadeLocal extends PersistenceFacade<Zone> {
    /**
     * @param name
     * @return
     * @throws AnaliaException
     */
    Zone getZoneByName(String name) throws AnaliaException;
}
