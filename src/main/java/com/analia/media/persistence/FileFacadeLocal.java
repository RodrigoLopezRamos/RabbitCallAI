package com.analia.media.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.File;
import com.analia.common.persistence.PersistenceFacade;

import java.math.BigInteger;
import java.util.List;


public interface FileFacadeLocal extends PersistenceFacade<File> {
    /**
     * @param directoryId
     * @param index
     * @return
     * @throws AnaliaException
     */
    File getFileWithDirectoryIdAndIndex(BigInteger directoryId, int index) throws AnaliaException;

    /**
     * @param directoryId
     * @param userId
     * @return
     * @throws AnaliaException
     */
    List<File> getAllFileByDirectoryIdAndUserId(BigInteger directoryId, BigInteger userId) throws AnaliaException;

    /**
     * @param fileId
     * @param userId
     * @return
     * @throws AnaliaException
     */
    File getFileByOwner(BigInteger fileId, BigInteger userId) throws AnaliaException;


    /**
     * @param name
     * @param directoryId
     * @return
     * @throws AnaliaException
     */
    File getFileByName(String name, BigInteger directoryId) throws AnaliaException;


}
