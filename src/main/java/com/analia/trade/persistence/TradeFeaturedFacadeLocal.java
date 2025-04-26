package com.analia.trade.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.TradeFeatured;
import com.analia.common.persistence.PersistenceFacade;


import java.math.BigInteger;
import java.util.List;

public interface TradeFeaturedFacadeLocal extends PersistenceFacade<TradeFeatured> {

    /**
     * @param zoneId
     * @return
     * @throws AnaliaException
     */
    List<TradeFeatured> getTradeFeatured(BigInteger zoneId) throws AnaliaException;
}
