package com.analia.media.service.impl;

import com.analia.common.constants.Constants;
import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Directory;
import com.analia.common.model.File;
import com.analia.media.core.FileSystemCoreLocal;
import com.analia.media.core.FileSystemCore.FileType;
import com.analia.media.service.FileSystemServiceLocal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

/**
 * Session Bean implementation class MediaService
 */
@ApplicationScoped
public class FileSystemService implements FileSystemServiceLocal {
    @Inject
    private FileSystemCoreLocal fileSystemCoreLocal;


    @Transactional
    public File saveFile(BigInteger fileId, FileType fileType, BigInteger directoryId, int sequenceNumber, String name, String description, String path, String mimeType) throws AnaliaException {
        return fileSystemCoreLocal.saveFile(fileId, fileType, directoryId, sequenceNumber, name, description, path, new BigInteger(Integer.toString(Constants.SYSTEM_USER_ID)), mimeType);
    }


    public Directory saveDirectory(BigInteger directoryId, BigInteger userId, BigInteger ownerid, BigInteger vendorId) throws AnaliaException {

        return fileSystemCoreLocal.saveDirectory(directoryId, userId, ownerid, vendorId);
    }


    public Directory getDirectory(BigInteger directoryId) throws AnaliaException {
        return fileSystemCoreLocal.getDirectory(directoryId);
    }


    public String saveFileInSystem(BigInteger directoryId, String name, InputStream inputstream) throws AnaliaException {
        return fileSystemCoreLocal.saveFileInFileSystem(directoryId, name, inputstream);
    }


    public List<File> getAllFileByDirectoryIdAndOwnerId(BigInteger directoryId, BigInteger ownerId) throws AnaliaException {
        return fileSystemCoreLocal.getAllFileByDirectoryIdAndOwnerId(directoryId, ownerId);
    }


    public List<File> getAllFileByDirectoryId(BigInteger directoryId) throws AnaliaException {
        return fileSystemCoreLocal.getAllFileByDirectoryIdAndOwnerId(directoryId, null);
    }


    public File getFile(BigInteger fileId) throws AnaliaException {
        return fileSystemCoreLocal.getFile(fileId);
    }


    public File getFile(String name, BigInteger directoryId) throws AnaliaException {
        return fileSystemCoreLocal.getFile(name, directoryId);
    }


    public File getFileWithDirectoryIdAndIndex(BigInteger directoryId, int index) throws AnaliaException {
        return fileSystemCoreLocal.getFileWithDirectoryIdAndIndex(directoryId, index);
    }


    public String getMediaUrl(String path) throws AnaliaException {
        return fileSystemCoreLocal.getMediaUrl(path);
    }


    public String getMediaProccesedUrl(String path) throws AnaliaException {
        return fileSystemCoreLocal.getMediaUrl(path);
    }

    public File saveFile(File file) throws AnaliaException {
        return fileSystemCoreLocal.saveFile(file);
    }
}
