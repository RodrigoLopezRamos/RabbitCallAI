package com.analia.media.service;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Directory;
import com.analia.common.model.File;
import com.analia.media.core.FileSystemCore.FileType;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

public interface FileSystemServiceLocal {

    /**
     * @param fileId
     * @param fileTypeId
     * @param directoryId
     * @param sequenceNumber
     * @param name
     * @param description
     * @param path
     * @return
     * @throws AnaliaException
     */
    File saveFile(BigInteger fileId, FileType fileTypeId, BigInteger directoryId, int sequenceNumber, String name, String description, String path, String mimeType) throws AnaliaException;

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
     * @param directoryId
     * @param name
     * @param inputstream
     * @return
     * @throws AnaliaException
     */
    String saveFileInSystem(BigInteger directoryId, String name, InputStream inputstream) throws AnaliaException;


    /**
     * @param directoryId
     * @return
     * @throws AnaliaException
     */
    List<File> getAllFileByDirectoryIdAndOwnerId(BigInteger directoryId, BigInteger ownerId) throws AnaliaException;

    /**
     * @param directoryId
     * @return
     * @throws AnaliaException
     */
    List<File> getAllFileByDirectoryId(BigInteger directoryId) throws AnaliaException;

    /**
     * @param fileId
     * @return
     * @throws AnaliaException
     */
    File getFile(BigInteger fileId) throws AnaliaException;

    /**
     * @param name
     * @param directoryId
     * @return
     * @throws AnaliaException
     */
    File getFile(String name, BigInteger directoryId) throws AnaliaException;


    /**
     * @param directoryId
     * @param index
     * @return
     * @throws AnaliaException
     */
    File getFileWithDirectoryIdAndIndex(BigInteger directoryId, int index) throws AnaliaException;

    /**
     * @param path
     * @return
     * @throws AnaliaException
     */
    String getMediaUrl(String path) throws AnaliaException;

    String getMediaProccesedUrl(String path) throws AnaliaException;


    /**
     * @param file
     * @return
     * @throws AnaliaException
     */
    File saveFile(File file) throws AnaliaException;


}
