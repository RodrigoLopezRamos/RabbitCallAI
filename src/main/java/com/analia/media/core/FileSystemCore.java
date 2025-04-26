package com.analia.media.core;

import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.BaseException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.Directory;
import com.analia.common.model.File;
import com.analia.media.persistence.DirectoryFacadeLocal;
import com.analia.media.persistence.FileFacadeLocal;
import com.analia.media.util.FileSystemUtils;
import com.analia.setttings.core.SettingsCoreLocal;
import com.analia.setttings.core.impl.SettingsCore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

import java.io.InputStream;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Session Bean implementation class MediaCore
 */
@ApplicationScoped
public class FileSystemCore implements FileSystemCoreLocal {

    private static final Log LOG = LogFactory.getLog(FileSystemCore.class);

    @Inject
    private FileFacadeLocal fileFacadeLocal;

    @Inject
    private DirectoryFacadeLocal directoryFacadeLocal;

    @Inject
    private SettingsCore settingsCoreLocal;

    /**
     *
     */

    public File saveFile(BigInteger fileId, FileType fileType, BigInteger directoryId, int secuenceNumber, String name, String description, String path, BigInteger userId, String mimeType) throws AnaliaException {
        File file = null;
        try {
            if (fileId == null || (file = fileFacadeLocal.find(fileId)) == null) {
                file = new File();
                file.setCreatedBy(userId);
                file.setCreatedDatetime(new Date());
            }
            file.setMimeTypeId(fileType.getId());
            file.setDirectoryId(directoryId);
            file.setMimeType(mimeType);
            file.setSecuenceNumber(secuenceNumber);
            file.setName(name);
            file.setDescription(description);
            file.setPath(path);
            fileFacadeLocal.save(file);
        } catch (BaseException baseException) {
            LOG.debug("File exist on database!");
        }
        return file;
    }

    /**
     *
     */

    public String getMediaUrl(String path) throws AnaliaException {
        String media = "";
        if (path != null) {
            String mediaUrl = settingsCoreLocal.getStringValueForSettingKey(SettingsCore.SYSTEM_MEDIA_SERVER_URL);
            media = mediaUrl.concat(path);
        }
        return media;
    }

    /**
     *
     */

    public String saveFileInFileSystem(BigInteger directoryId, String name, InputStream inputstream) throws AnaliaException {
        String mediaUrl = settingsCoreLocal.getStringValueForSettingKey(SettingsCore.SYSTEM_MEDIA_SERVER_URL);
        return FileSystemUtils.saveFileOnFileSystem(inputstream, directoryId, name, mediaUrl);
    }

    /**
     *
     */

    @Transactional
    public Directory saveDirectory(BigInteger directoryId, BigInteger userId, BigInteger ownerId, BigInteger vendorId) throws AnaliaException {
        Directory directory = null;
        try {
            if ((directory = directoryFacadeLocal.find(directoryId)) == null) {
                directory = new Directory();
                directory.setVendorId(vendorId);
                directory.setCreatedBy(userId);
                directory.setCreatedDatetime(new Date());
            }
            directory.setOwnerId(ownerId);
            directory.setTimestamp(new Timestamp(System.currentTimeMillis()));
            directoryFacadeLocal.save(directory);
            directoryFacadeLocal.flush();
        } catch (BaseException baseException) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, baseException.getMessage());
        }
        return directory;
    }

    /**
     *
     */

    public Directory getDirectoryWithOwnerId(BigInteger directoryId, BigInteger ownerId) throws AnaliaException {
        Directory directory = directoryFacadeLocal.find(directoryId);
        if ((directory == null) || ((directory.getOwnerId() != null) && (!Objects.equals(directory.getOwnerId(), ownerId)))) {
            throw new AnaliaException(ExceptionCode.AUTHORIZATION_FAILED, "directory not found or invalid owner for this directory");
        }
        return directory;
    }

    /**
     *
     */

    public File getFileWithDirectoryIdAndIndex(BigInteger directoryId, int index) throws AnaliaException {
        return fileFacadeLocal.getFileWithDirectoryIdAndIndex(directoryId, index);
    }

    /**
     *
     */

    public List<File> getAllFileByDirectoryIdAndOwnerId(BigInteger directoryId, BigInteger ownerId) throws AnaliaException {
        return fileFacadeLocal.getAllFileByDirectoryIdAndUserId(directoryId, ownerId);
    }

    /**
     *
     */

    public File getFileForOwner(BigInteger fileId, BigInteger userId) throws AnaliaException {
        return fileFacadeLocal.getFileByOwner(fileId, userId);
    }

    /**
     *
     */

    public Directory getDirectory(BigInteger directoryId) throws AnaliaException {
        return directoryFacadeLocal.find(directoryId);
    }

    /**
     *
     */

    public File getFile(BigInteger fileId) throws AnaliaException {
        return fileFacadeLocal.find(fileId);
    }


    public File saveFile(File file) throws AnaliaException {
        fileFacadeLocal.save(file);
        fileFacadeLocal.flush();
        return file;
    }


    public File getFile(String name, BigInteger directoryId) throws AnaliaException {
        return fileFacadeLocal.getFileByName(name, directoryId);
    }

    public enum FileType {

        VIDEO(1),
        IMAGE(2),
        PDF(3),
        WORD(4),
        HTML(5);

        private int id;

        FileType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }


    }


}
