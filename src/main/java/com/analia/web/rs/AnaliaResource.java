package com.analia.web.rs;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.File;
import com.analia.media.service.FileSystemServiceLocal;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnaliaResource {
    /**
     * @param mediaServiceLocal
     * @param directoryId
     * @return
     * @throws AnaliaException
     */
    public static List<HashMap<String, Object>> buildResponseForMedias(FileSystemServiceLocal mediaServiceLocal, BigInteger directoryId) throws AnaliaException {
        List<HashMap<String, Object>> listMedia = new ArrayList<>();
        List<File> medias = mediaServiceLocal.getAllFileByDirectoryId(directoryId);
        for (File file : medias) {
            HashMap<String, Object> mapMedia = new HashMap<>();
            mapMedia.put("mediaUrl", mediaServiceLocal.getMediaUrl(file.getPath()));
            mapMedia.put("secuence", file.getSecuenceNumber());
            listMedia.add(mapMedia);
        }
        return listMedia;
    }

    public static List<HashMap<String, Object>> buildResponseForMedia(FileSystemServiceLocal mediaServiceLocal, BigInteger directoryId, int index) throws AnaliaException {
        File file = mediaServiceLocal.getFileWithDirectoryIdAndIndex(directoryId, index);
        List<HashMap<String, Object>> listMedia = new ArrayList<>();
        if (file != null) {
            HashMap<String, Object> mapMedia = new HashMap<>();
            mapMedia.put("mediaUrl", mediaServiceLocal.getMediaUrl(file.getPath()));
            mapMedia.put("secuence", file.getSecuenceNumber());
            listMedia.add(mapMedia);
        }
        return listMedia;
    }
}
