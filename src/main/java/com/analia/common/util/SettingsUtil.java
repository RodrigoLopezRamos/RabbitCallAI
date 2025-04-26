package com.analia.common.util;

import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;

public class SettingsUtil {
    public static String getMediaFolder() throws AnaliaException {
        String mediaFolder = System.getProperty("fileSystemFolder");
        if (mediaFolder == null) {
            throw new AnaliaException(ExceptionCode.SERVER_CRITICAL_ERROR, "fileSystemFolder is missing from configuration!");
        }
        return mediaFolder;
    }

}
