package com.analia.purchase.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Source;


import java.math.BigInteger;
import java.util.List;

public interface SourceFacadeLocal extends com.analia.common.persistence.PersistenceFacade<Source> {
    /**
     * @param userId
     * @return
     * @throws AnaliaException
     */
    Source getDefaultSourceByUserId(BigInteger userId) throws AnaliaException;

    /**
     * @param userId
     * @return
     * @throws AnaliaException
     */
    List<Source> getSourcesByUserId(BigInteger userId) throws AnaliaException;

    /**
     * @param userId
     * @param cardId
     * @return
     * @throws AnaliaException
     */
    boolean setDefaultSource(BigInteger userId, BigInteger cardId) throws AnaliaException;

    /**
     * @param userId
     * @return
     * @throws AnaliaException
     */
    boolean setAllUserSourceToBeUnDefault(BigInteger userId) throws AnaliaException;

    /**
     * @param sourceId
     * @param userId
     * @return
     * @throws AnaliaException
     */
    Source getSourceByIdAndUserId(BigInteger sourceId, BigInteger userId) throws AnaliaException;

}
