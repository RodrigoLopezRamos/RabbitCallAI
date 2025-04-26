package com.analia.media.core;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Directory;
import com.analia.common.model.File;
import com.analia.media.core.FileSystemCore.FileType;


import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

public interface FileSystemCoreLocal {

    /**
     * @param fileId
     * @param mimeType
     * @param directoryId
     * @param secuenceNumber
     * @param name
     * @param description
     * @param path
     * @param userId
     * @return
     * @throws AnaliaException
     */
    File saveFile(BigInteger fileId, FileType fileType, BigInteger directoryId, int secuenceNumber, String name, String description, String path, BigInteger userId, String mimeType) throws AnaliaException;

    /**
     * @param path
     * @return
     * @throws AnaliaException
     */
    String getMediaUrl(String path) throws AnaliaException;

    /**
     * @param directoryId
     * @param name
     * @param inputstream
     * @return
     * @throws AnaliaException
     */
    String saveFileInFileSystem(BigInteger directoryId, String name, InputStream inputstream) throws AnaliaException;

    /**
     * @param directoryId
     * @param userId
     * @param ownerid
     * @return
     * @throws AnaliaException
     */
    Directory saveDirectory(BigInteger directoryId, BigInteger userId, BigInteger ownerid, BigInteger vendorId) throws AnaliaException;

    /**
     * @param directoryId
     * @return
     * @throws AnaliaException
     */
    Directory getDirectory(BigInteger directoryId) throws AnaliaException;

    /**
     * @param fileId
     * @param ownerId
     * @return
     * @throws AnaliaException
     */
    Directory getDirectoryWithOwnerId(BigInteger fileId, BigInteger ownerId) throws AnaliaException;

    /**
     * @param directoryId
     * @param index
     * @return
     * @throws AnaliaException
     */
    File getFileWithDirectoryIdAndIndex(BigInteger directoryId, int index) throws AnaliaException;

    /**
     * @param directoryId
     * @param ownerId
     * @return
     * @throws AnaliaException
     */
    List<File> getAllFileByDirectoryIdAndOwnerId(BigInteger directoryId, BigInteger ownerId) throws AnaliaException;

    /**
     * @param fileId
     * @return
     * @throws AnaliaException
     */
    File getFile(BigInteger fileId) throws AnaliaException;

    /**
     * @param fileId
     * @param userId
     * @return
     * @throws AnaliaException
     */
    File getFileForOwner(BigInteger fileId, BigInteger userId) throws AnaliaException;


    File saveFile(File file) throws AnaliaException;


    File getFile(String name, BigInteger directoryId) throws AnaliaException;


}
