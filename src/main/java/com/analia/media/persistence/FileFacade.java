package com.analia.media.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.File;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.math.BigInteger;
import java.util.List;

/**
 * Session Bean implementation class MediaFacade
 */
@ApplicationScoped
public class FileFacade extends JPAPersistenceFacade<File> implements FileFacadeLocal {

    /**
     *
     */
    private static final String PARAM_DIRECTORY = "directoryId";
    /**
     *
     */
    private static final String PARAM_FILE_ID = "fileId";
    /**
     *
     */
    private static final String PARAM_SEC_NUMBER = "sequenceNumber";
    /**
     *
     */
    private static final String PARAM_USER_ID = "userId";
    /**
     *
     */
    private static final String QUERY_GET_FILE_WITH_ID_AND_INDEX = "getFileWithDirectoryIdAndIndex";
    /**
     *
     */
    private static final String QUERY_GET_FILE_BY_DIRECTORY_ID_AND_USER_ID = "getAllFileByDirectoryIdAndUserId";
    private static final String PARAM_FILE_NAME = "name";
    /**
     *
     */
    private static final String QUERY_GET_FILE_BY_OWNER = "getFileByOwnerId";
    private static final String QUERY_GET_FILE_BY_NAME = "getFileByName";

    @Inject
    private EntityManager entityManager;


    /**
     *
     */
    public FileFacade() {
        super(File.class);
    }

    /**
     *
     */



    /**
     *
     */

    public File getFileWithDirectoryIdAndIndex(BigInteger directoryId, int secuenceNumber) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_GET_FILE_WITH_ID_AND_INDEX);
        query.setParameter(PARAM_DIRECTORY,directoryId);
        query.setParameter(PARAM_SEC_NUMBER,secuenceNumber);
        List result =  query.getResultList();
        if(query.getResultList().isEmpty()){
            return null;
        }
        return (File) result.get(0);
    }

    /**
     *
     */

    public List<File> getAllFileByDirectoryIdAndUserId(BigInteger directoryId, BigInteger userId) throws AnaliaException {
        JpqlParameter jpqlParameterDirectoryId = new JpqlParameter(PARAM_DIRECTORY, directoryId);
        JpqlParameter jpqlParameterUserId = new JpqlParameter(PARAM_USER_ID, userId);
        List<File> listFileObjects = getListForNamedQuery(QUERY_GET_FILE_BY_DIRECTORY_ID_AND_USER_ID, jpqlParameterDirectoryId, jpqlParameterUserId);
        return listFileObjects;
    }

    /**
     *
     */

    public File getFileByOwner(BigInteger fileId, BigInteger userId) throws AnaliaException {
        JpqlParameter jpqlParameterFileId = new JpqlParameter(PARAM_FILE_ID, fileId);
        JpqlParameter jpqlParameterUserId = new JpqlParameter(PARAM_USER_ID, userId);
        File file = getPersistForNamedQuery(QUERY_GET_FILE_BY_OWNER, jpqlParameterFileId, jpqlParameterUserId);
        return file;
    }


    public File getFileByName(String name, BigInteger directoryId) throws AnaliaException {
        JpqlParameter jpqlParameterFileId = new JpqlParameter(PARAM_FILE_NAME, name);
        JpqlParameter jpqlParameterDirectoryId = new JpqlParameter(PARAM_DIRECTORY, directoryId);
        File file = getPersistForNamedQuery(QUERY_GET_FILE_BY_NAME, jpqlParameterFileId, jpqlParameterDirectoryId);
        return file;
    }
}
