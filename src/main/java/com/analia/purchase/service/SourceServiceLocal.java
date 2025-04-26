package com.analia.purchase.service;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Source;
import com.analia.common.model.SourceType;


import java.math.BigInteger;
import java.util.List;


public interface SourceServiceLocal {


    /***
     * @return
     * @throws AnaliaException
     */
    Source addSource(String sourceHolderName, String sourceNickName, String sourceProcessorToken, String phoneNumber) throws AnaliaException;

    /**
     * @return
     */
    List<Source> getSources() throws AnaliaException;

    /**
     * @param defaultSourceId
     * @return
     * @throws AnaliaException
     */
    boolean setDefaultSource(BigInteger defaultSourceId) throws AnaliaException;

    /**
     * @param sourceId
     * @return
     * @throws AnaliaException
     */
    boolean deleteSource(BigInteger sourceId) throws AnaliaException;


    /**
     * @param sourceTypeId
     * @return
     * @throws AnaliaException
     */
    SourceType getSourceTypeById(BigInteger sourceTypeId) throws AnaliaException;

    /**
     * @throws AnaliaException
     */
    void disableUserSources() throws AnaliaException;

}
